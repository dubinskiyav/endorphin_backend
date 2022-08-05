package biz.gelicon.core.components.erp.todo.appendix;

import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;
import biz.gelicon.core.components.core.sqlaction.SqlAction;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.security.GrantedAccessRole;
import biz.gelicon.core.security.Permission;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppendixService extends BaseService<Appendix> {
    private static final Logger logger = LoggerFactory.getLogger(AppendixService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private AppendixRepository appendixRepository;
    @Autowired
    private AppendixValidator appendixValidator;
    @Autowired
    private AuthenticationBean authenticationBean;
    @Autowired
    private DocumentRealRepository docrealRepository;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/appendix/mainSQL.sql")
    Resource mainSQL;
    // главный запрос для получения одной записи. используется в get
    @Value("classpath:/query/appendix/mainSQLForOne.sql")
    Resource mainSQLForOne;


    @PostConstruct
    public void init() {
        init(appendixRepository, appendixValidator);
    }

    public List<AppendixView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<AppendixView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("query/appendix",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(AppendixView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(AppendixView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<AppendixView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("query/appendix",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(AppendixView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(AppendixView.class)
                .count();

    }

    public AppendixView getOne(Integer id) {
        return new Query.QueryBuilder<AppendixView>(mainSQLForOne)
                .setPredicate(ALIAS_MAIN+".appendix_id=:appendixId")
                .build(AppendixView.class)
                .executeOne("appendixId", id);
    }

    public String getHash(Integer appendixId){
        String sqlText = "" +
                "SELECT a.appendix_repositoryid " +
                "FROM appendix a " +
                "WHERE a.appendix_id = :appendixId";
        return appendixRepository.findQueryForObject(String.class, sqlText, "appendixId", appendixId);
    }

    /**
     * Проверка прав на операции с ЭМ, который может быть связан с документом с типом documentId.
     * Реальная связь ЭМ с документами не проверяется
     *
     * @param appendix
     * @param actionId
     * @param documentId
     * @return
     */
    public boolean checkAccess(Appendix appendix, int actionId, int documentId){
        UserDetailsImpl ud = (UserDetailsImpl) authenticationBean.getCurrentUserDetails();
        boolean isOwner = authenticationBean.getCurrentUser().getProguserId().equals(appendix.getProguserId());
        // владелец и системный пользователь могут все
        if(isOwner || ud.containsSystemRole()) {
            return true;
        }

        Map<String, Object> params = Map.of(
                "appendixtypeId",appendix.getCapclassId(),
                "sqlActionId",actionId,
                "documentId",documentId,
                "rolesIds",ud.getAuthorities().stream()
                        .map(ga->((GrantedAccessRole)ga).accessRoleId())
                        .collect(Collectors.toList())
        );
        String sqlText = "" +
                "SELECT COUNT(*) " +
                "FROM documentappendixrole dar " +
                "WHERE 1=1 " +
                    "AND dar.appendixtype_id = :appendixtypeId " +
                    "AND dar.sqlaction_id = :sqlActionId " +
                    "AND dar.document_id = :documentId " +
                    "AND dar.accessrole_id in (:rolesIds)";
        int count = appendixRepository.countQuery(sqlText, params);

        return count > 0;

    }

    /**
     * Проверка прав на операции с ЭМ по всем связанным с ним документам
     * @param appendix
     * @param actionId
     * @return
     */
    public boolean checkAccess(Appendix appendix, int actionId){
        List<Integer> docrealIds = getDocRealIdsForAppendix(appendix.getAppendixId());
        DocumentReal causeDocReal = validateAccessAppendixForDocumentIds(appendix, actionId, docrealIds);
        return causeDocReal==null?true:false;
    }

    /**
     * Проверка права на операцию с ЭМ, которые связаны с документами
     * @param appendix
     * @param actionId
     * @param documentReals
     * @return - если доступ есть возвращается -1, иначе, возвращается номер документа
     * в списке documentReals, который стал причиной ограничения доступа
     */
    public int validateAccessAppendixForDocuments(Appendix appendix, int actionId, List<DocumentReal> documentReals) {
        DocumentReal[] cause = new DocumentReal[1];
        boolean noAccess = documentReals.stream().anyMatch(documentReal -> {
            boolean ban = !checkAccess(appendix, actionId, documentReal.getDocumentId());
            // Так как ЭМ являются частью документа
            // дополнительно нужно проверить право на изменение документа.
            if(!ban) {
                docrealRepository.validateUpdate(documentReal, Permission.UPDATE);
            }
            // сохраним причину бана
            if(ban) {
                cause[0]=documentReal;
            }
            return ban;
        });
        return noAccess?documentReals.indexOf(cause[0]):-1;
    }

    /**
     * Проверка права на операцию с ЭМ, которые связаны с документами
     * @param appendix
     * @param actionId
     * @param documentRealsIds
     * @return - если доступ есть возвращается null, иначе, возвращается документ
     * , который стал причиной ограничения доступа
     */
    public DocumentReal validateAccessAppendixForDocumentIds(Appendix appendix, int actionId, List<Integer> documentRealsIds) {
        if(!documentRealsIds.isEmpty()) {
            List<DocumentReal> documentReals = docrealRepository.findById(documentRealsIds.toArray(new Integer[]{}));
            int validIdx = validateAccessAppendixForDocuments(appendix,actionId,documentReals);
            if(validIdx>=0) {
                return documentReals.get(validIdx);
            };
        }
        return null;
    }

    public void saveHash(Integer appendixId, String hash){
        String sqlText = "" +
                "UPDATE appendix " +
                "SET appendix_repositoryid = :hash " +
                "WHERE appendix_id = :appendixId";
        Map<String, Object> params = new HashMap<>();
        params.put("hash", hash);
        params.put("appendixId", appendixId);
        appendixRepository.executeSQL(sqlText, params);
    }

    public List<Integer> getDocRealIdsForAppendix(Integer appendixId){
        String sqlText = "SELECT documentreal_id " +
                "FROM docrealappendix dra " +
                "WHERE appendix_id = :appendixId";
        List<Integer> ids = new ArrayList<>();
        appendixRepository.findQueryForMap(sqlText, "appendixId", appendixId).forEach(stringObjectMap -> {
            ids.add((Integer)stringObjectMap.get("documentreal_id"));
        });
        return ids;
    }

    /**
     * Устанавливаются/Разрушаются связи между ЭМ и документами. Проверяются все права
     * @param appendixId
     * @param insertDocRealIds
     * @param deleteDocRealIds
     */
    public void setDocumentRealIdsForAppendix(Integer appendixId, List<Integer> insertDocRealIds, List<Integer> deleteDocRealIds){
        List<DocumentReal> documentReals;
        UserDetailsImpl ud = (UserDetailsImpl) authenticationBean.getCurrentUserDetails();
        Appendix appendix = appendixRepository.findById(appendixId);
        DocumentReal cause = null;
        // проверка прав на вставку
        if(!insertDocRealIds.isEmpty()) {
            documentReals = docrealRepository.findById(insertDocRealIds.toArray(new Integer[]{}));
        } else {
            documentReals = new ArrayList<>();
        }
        int result = validateAccessAppendixForDocuments(appendix, SqlAction.INSERT_ACTION,documentReals);
        if(result>=0) {
            throw  new RuntimeException(
                    String.format("Не прав на связывание ЭМ (Тип = %s) с документом %s",
                            appendix.getCapclassId().toString(),documentReals.get(result).getDocumentRealName())
            );
        }
        appendixRepository.bindWithDocumntReal(appendixId,insertDocRealIds);

        // проверка прав на удаление
        if(!deleteDocRealIds.isEmpty()) {
            documentReals = docrealRepository.findById(deleteDocRealIds.toArray(new Integer[]{}));
        } else {
            documentReals = new ArrayList<>();
        }

        result = validateAccessAppendixForDocuments(appendix, SqlAction.DELETE_ACTION,documentReals);
        if(result>=0) {
            throw  new RuntimeException(
                    String.format("Не прав на удаление связи ЭМ (Тип = %s) с документом %s",
                            appendix.getCapclassId().toString(),documentReals.get(result).getDocumentRealName())
            );
        }
        appendixRepository.unBindWithDocumntReal(appendixId,deleteDocRealIds);
    }


}

