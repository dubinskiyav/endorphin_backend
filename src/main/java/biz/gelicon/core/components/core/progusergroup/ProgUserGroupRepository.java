package biz.gelicon.core.components.core.progusergroup;


import biz.gelicon.core.components.erp.todo.edizm.EdizmRepository;
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
public class ProgUserGroupRepository implements TableRepository<Progusergroup> {

    private static final Logger logger = LoggerFactory.getLogger(EdizmRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400200-progusergroup.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("progusergroup created");
    }

    @Override
    public int load() {
        return 0;
    }

}
