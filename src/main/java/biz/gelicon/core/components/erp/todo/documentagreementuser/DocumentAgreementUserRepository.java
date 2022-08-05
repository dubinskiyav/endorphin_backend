package biz.gelicon.core.components.erp.todo.documentagreementuser;

import biz.gelicon.core.repository.TableRepository;
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

import java.util.Arrays;
import java.util.Date;

@Repository
public class DocumentAgreementUserRepository implements TableRepository<DocumentAgreementUser> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentAgreementUserRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700020-documentagreementuser.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documentagreementuser created");
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
        DocumentAgreementUser[] data =  new DocumentAgreementUser[] {
                new DocumentAgreementUser(1,1,2, DateUtils.getMinDate(),DateUtils.getMaxDate(),new Date()),
                new DocumentAgreementUser(2,2,3, DateUtils.getMinDate(),DateUtils.getMaxDate(),new Date())
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d documentagreementuser loaded", data.length));
        DatabaseUtils.setSequence("documentagreementuser_id_gen", data.length+1);
        return data.length;
    }

}

