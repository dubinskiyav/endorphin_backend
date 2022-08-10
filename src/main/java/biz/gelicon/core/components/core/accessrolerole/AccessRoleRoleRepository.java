package biz.gelicon.core.components.core.accessrolerole;

import biz.gelicon.core.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

@Repository
public class AccessRoleRoleRepository implements TableRepository<AccessRoleRole> {

    private static final Logger logger = LoggerFactory.getLogger(AccessRoleRoleRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/accessrolerole.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("AccessRoleRole created");
    }
    @Override
    public void createFullTextView() {
        String fileName = "sql/accessrolerole_ft.sql";
        Resource resource = new ClassPathResource(fileName);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info(fileName + " was executed");

    }

}

