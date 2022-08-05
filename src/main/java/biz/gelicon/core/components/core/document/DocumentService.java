package biz.gelicon.core.components.core.document;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentService extends BaseService<Document> {
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentValidator documentValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/document/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(documentRepository, documentValidator);
    }

    public List<DocumentView> getMainList(GridDataOption gridDataOption) {
        Map<String, String> subsColumns = new HashMap<>();
        subsColumns.put("uniquetype_name", "u.uniquetype_name");

        return new Query.QueryBuilder<DocumentView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("document",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(DocumentView.class,ALIAS_MAIN,subsColumns))
                .setParams(gridDataOption.buildQueryParams())
                .build(DocumentView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        Map<String, String> subsColumns = new HashMap<>();
        subsColumns.put("uniquetype_name", "u.uniquetype_name");

        return new Query.QueryBuilder<DocumentView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("document",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(DocumentView.class,ALIAS_MAIN,subsColumns))
                .setParams(gridDataOption.buildQueryParams())
                .build(DocumentView.class)
                .count();

    }


    public DocumentView getOne(Integer id) {
        return new Query.QueryBuilder<DocumentView>(mainSQL)
                .setPredicate(ALIAS_MAIN+".document_id=:documentId")
                .build(DocumentView.class)
                .executeOne("documentId", id);
    }

    public Integer getUniqueTypeForDocument(Integer id) {
        return documentRepository.findUniqueType(id);
    }

    public void saveUniqueTypeForDocument(Integer documentId, Integer uniqueTypeId) {
        Map<String, Object> args = new HashMap<>();
        Integer origUnique = documentRepository.findUniqueType(documentId);
        if(origUnique==null) {
            if(uniqueTypeId!=null) {
                String sql = "INSERT INTO uniquetypedocument(document_id, uniquetype_id) " +
                        "VALUES (:document_id, :uniquetype_id)";
                args.put("document_id",documentId);
                args.put("uniquetype_id",uniqueTypeId);
                documentRepository.executeSQL(sql,args);
            }
        } else {
            if(uniqueTypeId!=null) {
                String sql = "UPDATE uniquetypedocument " +
                        "SET document_id = :document_id , uniquetype_id = :uniquetype_id " +
                        "WHERE document_id = :document_id";
                args.put("document_id",documentId);
                args.put("uniquetype_id",uniqueTypeId);
                documentRepository.executeSQL(sql,args);
            } else {
                String sql = "DELETE FROM uniquetypedocument " +
                        "WHERE document_id = :document_id";
                args.put("document_id",documentId);
                documentRepository.executeSQL(sql,args);
            }
        }
    }
}

