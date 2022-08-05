package biz.gelicon.core.components.erp.todo.documentappendixrole;

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
public class DocumentAppendixRoleRepository implements TableRepository<DocumentAppendixRole> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentAppendixRoleRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700220-documentappendixrole.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documentappendixrole created");
    }

    @Override
    public void drop() {
        String[] dropTableBefore = new String[]{
        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.drop();

        String[] dropTableAfter = new String[]{
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {
        DocumentAppendixRole[] data =  new DocumentAppendixRole[] {
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d documentappendixrole loaded", data.length));
        DatabaseUtils.setSequence("documentappendixrole_id_gen", data.length+1);
        return data.length;
    }

}

