package biz.gelicon.core.components.erp.todo.documentrealagree;

import biz.gelicon.core.components.erp.todo.documentagreement.DocumentAgreementRepository;
import biz.gelicon.core.components.erp.todo.documentagreementuser.DocumentAgreementUserRepository;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;
import biz.gelicon.core.components.erp.todo.documentrealagreeuser.DocumentRealAgreeUser;
import biz.gelicon.core.components.erp.todo.documentrealagreeuser.DocumentRealAgreeUserRepository;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;

@Repository
public class DocumentRealAgreeRepository implements TableRepository<DocumentRealAgree> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentRealAgreeRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DocumentRealRepository documentRealRepository;
    @Autowired
    private DocumentAgreementRepository documentAgreementRepository;
    @Autowired
    private DocumentAgreementUserRepository documentAgreementUserRepository;
    @Autowired
    private AuthenticationBean authentication;
    @Autowired
    private DocumentRealAgreeUserRepository documentRealAgreeUserRepository;


    public void prepare(Integer documentRealId) {
        prepare(documentRealRepository.getOne(documentRealId));
    }

    /**
     * Формируем подписи для документа на основании шаблонов
     * @param documentReal
     */
    public void prepare(DocumentReal documentReal) {
        Integer currUserId = authentication.getCurrentUser().getProguserId();
        Date now = new Date();
        documentAgreementRepository
                .findWhere("document_id=:documentId","documentId",documentReal.getDocumentId())
                .forEach(agree->{
                    DocumentRealAgree docargee = agree.toDocumentRealAgree();
                    docargee.setDocumentRealId(documentReal.getDocumentRealId());
                    docargee.setProguserCreateId(currUserId);
                    insert(docargee);
                    // вставлям подписантов
                    documentAgreementUserRepository.findWhere("documentagreement_id = :documentagreementId",
                            "documentagreementId",agree.getDocumentAgreementId()).forEach(useragree->{
                        DocumentRealAgreeUser docagreeuser = new DocumentRealAgreeUser(
                                null,
                                docargee.getDocumentRealAgreeId(),
                                currUserId,
                                useragree.getAgreementUserDatebeg(),
                                useragree.getAgreementUserDateend(),
                                now
                                );
                        documentRealAgreeUserRepository.insert(docagreeuser);
                    });
                });
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600900-documentrealagree.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documentrealagree created");
    }

    @Override
    public void dropForTest() {
        String[] dropTableBefore = new String[]{
        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.dropForTest();

        String[] dropTableAfter = new String[]{
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {
        DocumentRealAgree[] data =  new DocumentRealAgree[] {
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d documentrealagree loaded", data.length));
        DatabaseUtils.setSequence("documentrealagree_id_gen", data.length+1);
        return data.length;
    }

}

