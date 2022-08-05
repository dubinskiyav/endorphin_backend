package biz.gelicon.core.components.erp.todo.documentreal;

import biz.gelicon.core.components.erp.todo.documentrealagree.DocumentRealAgreeRepository;
import biz.gelicon.core.components.core.document.DocumentRepository;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.security.Permission;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.TwoTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentRealService extends BaseService<DocumentReal> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentRealService.class);

    @Autowired
    private DocumentRealRepository documentRealRepository;
    @Autowired
    private DocumentRealValidator documentRealValidator;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private AuthenticationBean authentication;
    @Autowired
    private DocumentRealAgreeRepository documentRealAgreeRepository;


    @PostConstruct
    public void init() {
        init(documentRealRepository, documentRealValidator);
    }

    /**
     * Стандартный набор подстановок для всеех документов
     *
     * @return
     */
    public static Map<String, String> getStandartColumnSubstitution(String aliasDocumentReal,
            String aliasDocumentTransit) {
        Map<String, String> subsColumns = new HashMap<>();
        subsColumns.put("documentreal_number", aliasDocumentReal + ".documentreal_number");
        subsColumns.put("documentreal_date", aliasDocumentReal + ".documentreal_date");
        subsColumns.put("documenttransit_name", aliasDocumentTransit + ".documenttransit_name");
        return subsColumns;
    }


    @Override
    protected void beforeValidate(DocumentReal entity) {
        boolean modeInsert = entity.getDocumentRealId() == null;

        if (entity.getDocumentRealLevel() != null &&
                entity.getDocumentRealLevel() == DocumentReal.DocumentLevel.FOLDER_DOCUMENT
                        .ordinal() &&
                entity.getDocumentRealNumber() == null) {
            entity.setDocumentRealNumber(entity.getDocumentRealCode());
        }
        entity.setDocumentRealName(documentRealRepository.createDocumentRealName(
                documentRepository.findById(entity.getDocumentId()),
                entity.getDocumentRealNumber(),
                entity.getDocumentRealDate()));
        if (modeInsert) {
            // вставка документа, поля становятся default так как может придти
            // копия документа но с id = null
            entity.setDocumentRealDatecreate(new Date());
            entity.setDocumentRealDatemodify(null);
            entity.setDocumentRealStatus(0);
            entity.setDocumentRealRepkey(null);
            entity.setDocumentRealArchive(0);
            entity.setProguserId(authentication.getCurrentUser().getProguserId());
            if (entity.getParentId() == null || entity.getClusterDocumentRealId() == null) {
                // в корень для документа
                TwoTuple<Integer, Integer> defaultCluster = documentRepository
                        .getDefaultCluster(entity.getDocumentId());
                entity.setClusterDocumentRealId(defaultCluster.a);
                entity.setParentId(defaultCluster.b);
            }
        } else {
            // обновление документа, переписывается только некоторые поля
            entity.setDocumentRealDatemodify(new Date());
            entity.setLastProguserId(authentication.getCurrentUser().getProguserId());
        }
    }

    @Override
    protected void afterSave(DocumentReal entity, boolean modeInsert) {
        // дополняем для уровней
        Integer level = entity.getDocumentRealLevel();
        if (level == DocumentReal.DocumentLevel.COMPOSITE_DOCUMENT.ordinal() ||
                level == DocumentReal.DocumentLevel.FOLDER_DOCUMENT.ordinal()) {
            documentRealRepository.setDocumentRealCluster(entity.getDocumentRealId());
        } else {
            documentRealRepository.deleteDocumentRealCluster(entity.getDocumentRealId());
        }
        //проверяем статусы документов
        documentRealRepository.checkStatusUpdate(entity.getDocumentRealId(),
                modeInsert ? Permission.INSERT : Permission.UPDATE);
        //проверяем права и ограничения
        documentRealRepository.checkUpdate(entity.getDocumentRealId(),
                modeInsert ? Permission.INSERT : Permission.UPDATE);

        // Функциональность documentrealhistory реализована в системе Аудита
        //documentrealhistoryins не переносим

        //Функциональность doclastproguser не нужна так как повторяет функциональность documentrealhistory
        // По утверждению sav doclastproguser еще используется в реализации пессимистических блокировок
        // документов. В новой платформе такая реализация не предполагается

        if (modeInsert) {
            documentRealAgreeRepository.prepare(entity);
        }
    }

    @Override
    protected void beforeDelete(int id) {
        DocumentReal doc = documentRealRepository.getOne(id);
        //проверяем статусы документов
        documentRealRepository.checkStatusUpdate(doc, Permission.DELETE);
        //проверяем права и ограничения
        documentRealRepository.checkUpdate(doc, Permission.DELETE);
    }

    /**
     * Изменяет в базе поле статус (documentreal_status) без проверок прямым UPDATE
     *
     * @param documentRealId
     * @param documentTransitId
     */
    public void updateStatus(
            Integer documentRealId,
            Integer documentTransitId
    ) {
        documentRealRepository.executeSQL(""
                        + " UPDATE documentreal SET "
                        + "   documentreal_status = :documenttransit_id "
                        + " WHERE documentreal_id = :documentreal_id ",
                new HashMap<String, Object>() {{
                    put("documenttransit_id", documentTransitId);
                    put("documentreal_id", documentRealId);
                }}
        );

    }

    /**
     * Возвращает статус документа
     *
     * @param documentrealId
     * @return
     */
    public Integer getStatus(Integer documentrealId) {
        return documentRealRepository
                .findQueryForObject(Integer.class, ""
                                + " SELECT documentreal_status "
                                + " FROM   documentreal "
                                + " WHERE  DRT.documentreal_id = " + documentrealId,
                        null
                );
    }

}

