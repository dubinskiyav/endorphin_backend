package biz.gelicon.core.components.erp.todo.pricesgoodsubject;

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
public class PriceSgoodSubjectRepository implements TableRepository<PriceSgoodSubject> {

    private static final Logger logger = LoggerFactory.getLogger(PriceSgoodSubjectRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700500-pricesgoodsubject.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("pricesgoodsubject created");
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
        PriceSgoodSubject[] data =  new PriceSgoodSubject[] {
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d pricesgoodsubject loaded", data.length));
        DatabaseUtils.setSequence("pricesgoodsubject_id_gen", data.length+1);
        return data.length;
    }

}

