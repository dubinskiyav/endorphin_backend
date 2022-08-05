package biz.gelicon.core.components.erp.todo.documentagreement;

import biz.gelicon.core.repository.TableRepository;
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

@Repository
public class DocumentAgreementRepository implements TableRepository<DocumentAgreement> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentAgreementRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700000-documentagreement.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documentagreement created");
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
        DocumentAgreement[] data =  new DocumentAgreement[] {
                new DocumentAgreement(1,2,1,"Зам.директора",1,1,null,null),
                new DocumentAgreement(2,2,2,"Гл.бух",1,1,null,null)
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d documentagreement loaded", data.length));
        DatabaseUtils.setSequence("documentagreement_id_gen", data.length+1);
        return data.length;
    }

}

