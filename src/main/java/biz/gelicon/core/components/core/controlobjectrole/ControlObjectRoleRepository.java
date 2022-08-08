package biz.gelicon.core.components.core.controlobjectrole;

import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.security.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

@Repository
public class ControlObjectRoleRepository implements TableRepository<ControlObjectRole> {

    private static final Logger logger = LoggerFactory.getLogger(ControlObjectRoleRepository.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    AccessRoleRepository accessRoleRepository;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400234-controlobjectrole.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getJdbcTemplate().getDataSource());
        logger.info("controlobjectrole created");
    }

    @Override
    public int load() {
        return 0;
        /*
        //Роль TEST1 связана с всеми функциями edizm, кроме удаления
        accessRoleRepository.bindWithControlObject(1, 1, Permission.EXECUTE);
        accessRoleRepository.bindWithControlObject(1, 2, Permission.EXECUTE);
        accessRoleRepository.bindWithControlObject(1, 3, Permission.EXECUTE);
        // это расширения get (#add и #edit)
        accessRoleRepository.bindWithControlObject(1, 5, Permission.EXECUTE);
        accessRoleRepository.bindWithControlObject(1, 6, Permission.EXECUTE);
        // это delete - свяжем с другими ролями
        accessRoleRepository.bindWithControlObject(2, 4, Permission.EXECUTE);
        accessRoleRepository.bindWithControlObject(4, 4, Permission.EXECUTE);

        return 7;

         */
    }
    

}

