package biz.gelicon.core.components.erp.todo.documentreal;

import biz.gelicon.core.components.core.document.Document;
import biz.gelicon.core.components.core.document.DocumentRepository;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransit;
import biz.gelicon.core.components.erp.todo.uniquetype.UniqueType;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitRepository;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.security.Permission;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class DocumentRealRepository implements TableRepository<DocumentReal> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentRealRepository.class);
    public static final String DOCUMENT_DATE_FORMAT = "dd.MM.yyyy";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentTransitRepository documentTransitRepository;

    @Autowired
    private AuthenticationBean authentication;


    public Integer getDocumentRealCluster(Integer docrealId) {
        return findQueryForObject(Integer.class,
                "SELECT max(clusterdocumentreal_id) FROM documentrealcluster WHERE documentreal_id=:documentrealId",
                "documentrealId",
                docrealId);
    }

    public Integer setDocumentRealCluster(Integer docrealId) {
        Integer clusterId = getDocumentRealCluster(docrealId);
        if (clusterId == null) {
            String sql;
            clusterId = DatabaseUtils.sequenceNextValue("clusterdocumentreal_id_gen");
            sql = buildInsertClause("clusterdocumentreal",
                    new String[]{"clusterdocumentreal_id"}, new String[]{"clusterdocumentrealId"});
            executeSQL(sql, "clusterdocumentrealId", clusterId);

            sql = buildInsertClause("documentrealcluster",
                    new String[]{"documentreal_id", "clusterdocumentreal_id"},
                    new String[]{"documentrealId", "clusterdocumentrealId"});
            HashMap<String, Object> params = new HashMap<>();
            params.put("documentrealId", docrealId);
            params.put("clusterdocumentrealId", clusterId);
            executeSQL(sql, params);
        }
        return clusterId;
    }

    public void deleteDocumentRealCluster(Integer docrealId) {
        String sql = buildDeleteClause("documentrealcluster", "documentreal_id=:documentrealId");
        executeSQL(sql, "documentrealId", docrealId);
    }

    public String createDocumentRealName(Document document, String number, Date date) {
        if(document==null) {
            throw new RuntimeException("Неизвестный тип документа (document = null)");
        }
        SimpleDateFormat df = new SimpleDateFormat(DOCUMENT_DATE_FORMAT);
        String name;
        if (document.getDocumentShortname() != null) {
            name = document.getDocumentShortname();
        } else {
            if (document.getDocumentName() != null) {
                name = document.getDocumentName();
            } else {
                name = "Документ";
            }
        }
        return name + (number != null ? " № " + number
                : "") +(date != null ? " от " + df.format(date) : "");
    }

    /**
     * Проверка возможности модификации документа. Документ должен существовать
     *
     * @param documentrealId - документ
     * @param sqlActionId    - акция
     * @return - можно модифицировать
     */
    public void checkUpdate(
            Integer documentrealId,
            Permission sqlActionId) {
        DocumentReal docreal = findById(documentrealId);
        if (docreal == null) {
            throw new RuntimeException(String.format("Документ с идентификатором %d не существует",
                    documentrealId.intValue()));
        }
        checkUpdate(docreal, sqlActionId);
    }

    /**
     * Проверка возможности модификации документа.
     *
     * @param docreal     - документ
     * @param sqlActionId - акция, например Permission.UPDATE
     */
    public void checkUpdate(DocumentReal docreal, Permission sqlActionId) {
        if(!validateUpdate(docreal,sqlActionId)) {
            throw new RuntimeException(
                    String.format("Отсутствует доступ на операцию %s для документа %s",
                            sqlActionId.getNameRu(),
                            docreal.getDocumentRealName()));
        }
    }

    public boolean validateUpdate(DocumentReal docreal, Permission sqlActionId) {
        // текущий пользователь
        UserDetailsImpl ud = (UserDetailsImpl) authentication.getCurrentUserDetails();
        // системный может все, иначе др.проверки
        return ud.containsSystemRole() || validateDocConstraintDays(
                ud.getProgUser().getProguserId(),
                sqlActionId,
                docreal.getDocumentId(),
                docreal.getDocumentRealDate());
    }


    private boolean validateDocConstraintDays(Integer currentUserId, Permission sqlActionId,
            Integer documentId, Date documentRealDate) {
        // откзались от использования в новой
        // платформе из-за низкой (=0) востребованности
        return true;
    }

    /**
     * Проверка ограничений модификации статусов документа
     *
     * @param documentRealId
     * @param permission
     */
    public void checkStatusUpdate(Integer documentRealId, Permission permission) {
        checkStatusUpdate(getOne(documentRealId), permission);
    }

    public void checkStatusUpdate(DocumentReal entity, Permission permission) {
        if (entity.getDocumentRealStatus() != 0) { // Статус установлен
            DocumentTransit dt =
                    documentTransitRepository.findById(entity.getDocumentRealStatus());
            switch (permission) {
                case DELETE: {
                    if (dt.getDocumentTransitCandelete() == 0) {
                        throw new RuntimeException(String.format(
                                "Удаление документа '%s' запрещено настройкой статуса '%s'",
                                entity.getDocumentRealName(), dt.getDocumentTransitName()));
                    }
                }
                break;
                case UPDATE: {
                    if (dt.getDocumentTransitCanedit() == 0) {
                        throw new RuntimeException(String.format(
                                "Изменение документа '%s' запрещено настройкой статуса '%s'",
                                entity.getDocumentRealName(), dt.getDocumentTransitName()));
                    }
                }
                break;
            }
        }
    }

    /**
     * Проверка уникальности номера документа в соответствии со стратегией указанной в Document
     *
     * @param entity - проверяемый документ
     */
    public boolean validateUniqueNumber(DocumentReal entity) {

        Integer uniqueType = documentRepository.findUniqueType(entity.getDocumentId());
        if (uniqueType == null || uniqueType == UniqueType.UNQ_NOUNIQUE) {
            return true;
        }
        if (entity.getDocumentRealNumber() == null || entity.getDocumentRealDate() == null) {
            return true;
        }
        Date docdate = entity.getDocumentRealDate();
        Date dateBeg;
        Date dateEnd;

        switch (uniqueType) {
            case UniqueType.UNQ_THROUGH:
                dateBeg = DateUtils.getMinDate();
                dateEnd = DateUtils.getMinDate();
                break;

            case UniqueType.UNQ_YEAR:
                dateBeg = DateUtils.atStartOfYear(docdate);
                dateEnd = DateUtils.atEndOfYear(docdate);
                break;

            case UniqueType.UNQ_QUARTER:
                dateBeg = DateUtils.atStartOfQuarter(docdate);
                dateEnd = DateUtils.atEndOfQuarter(docdate);
                break;

            case UniqueType.UNQ_MONTH:
                dateBeg = DateUtils.atStartOfMonth(docdate);
                dateEnd = DateUtils.atEndOfMonth(docdate);
                break;
            case UniqueType.UNQ_DAY:
                dateBeg = DateUtils.atStartOfDay(docdate);
                dateEnd = DateUtils.atEndOfDay(docdate);
                break;
            default:
                throw new RuntimeException("Неизвестный тип уникальности");
        }
        String sql = "" +
                "SELECT count(*)" +
                "FROM documentreal " +
                "WHERE documentreal_date BETWEEN :dateBeg AND :dateEnd " +
                "   AND  documentreal_number = :documentrealNumber " +
                "   AND  document_id = :documentId " +
                "   AND  (:documentRealId=0 OR documentreal_id != :documentRealId) ";
        Map<String, Object> params = new HashMap<>();
        params.put("dateBeg", dateBeg);
        params.put("dateEnd", dateEnd);
        params.put("documentrealNumber", entity.getDocumentRealNumber());
        params.put("documentId", entity.getDocumentId());
        params.put("documentRealId", entity.getDocumentRealId()!=null?entity.getDocumentRealId():0);

        return findQueryForObject(Integer.class, sql, params) == 0;
    }


    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600600-documentreal.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documentreal created");
    }

    @Override
    public void dropForTest() {
        String[] dropTableBefore = new String[]{
                "documentcluster",
                "documentrealcluster"
        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.dropForTest();

        String[] dropTableAfter = new String[]{
                "clusterdocumentreal"
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {

        Date dateBeg = DateUtils.getMinDate();
        Date dateEnd = DateUtils.getMaxDate();

        DocumentReal[] data = new DocumentReal[]{
                new DocumentReal(1, 50, 1, 1, "Корневой уровень документов", "000", "000",
                        new Date(), null,
                        DocumentReal.DocumentLevel.FOLDER_DOCUMENT.ordinal(), null, null, null, 0,
                        null,
                        new Date(), null, 0, 1),
                new DocumentReal(101, 2, 2, 1, "Заявка №1", null, "1", new Date(), null,
                        DocumentReal.DocumentLevel.SIMPLE_DOCUMENT.ordinal(), dateBeg, dateEnd,
                        null, 4, 1, new Date(), new Date(), 0, 1),
                new DocumentReal(102, 2, 3, 2, "Заявка №2", null, "2", new Date(), null,
                        DocumentReal.DocumentLevel.SIMPLE_DOCUMENT.ordinal(), dateBeg, dateEnd,
                        null, 0, 1, new Date(), new Date(), 0, 1),
                new DocumentReal(103, 1, 4, 3, "Счет на оплату №1", null, "1", new Date(), null,
                        DocumentReal.DocumentLevel.SIMPLE_DOCUMENT.ordinal(), dateBeg, dateEnd,
                        null, 0, 1, new Date(), new Date(), 0, 1),
        };
        insert(Arrays.asList(data));
        // сделаем уровнем
        setDocumentRealCluster(1);

        logger.info(String.format("%d documentreal loaded", data.length));
        DatabaseUtils.setSequence("documentreal_id_gen", data.length + 1);
        return data.length;
    }

}

