package biz.gelicon.core.components.core.proguserrole;


import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
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
public class ProgUserRoleRepository implements TableRepository<ProguserRole> {

    private static final Logger logger = LoggerFactory.getLogger(ProgUserRoleRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    AccessRoleRepository accessRoleRepository;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400232-proguserrole.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("proguserrole created");
    }

    @Override
    public int load() {
        // Пользователь root всегда связан с ролью SYSDBA
        accessRoleRepository.bindWithProgUser(0, 1);

        return 1;
    }

}
