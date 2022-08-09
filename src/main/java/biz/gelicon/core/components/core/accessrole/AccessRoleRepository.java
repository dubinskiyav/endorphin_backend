package biz.gelicon.core.components.core.accessrole;

import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.security.Permission;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.components.core.controlobjectrole.ObjectRoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AccessRoleRepository implements TableRepository<AccessRole> {

    private static final Logger logger = LoggerFactory.getLogger(AccessRoleRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Список ролей для пользователя
     *
     * @param progUserId - пользователь
     * @return - список
     */
    public List<AccessRole> findByUser(Integer progUserId) {
        List<AccessRole> accessRoleList;
        if (progUserId == Proguser.SYSDBA_PROGUSER_ID) {
            // dav SYSDBA имеет доступ на все роли
            accessRoleList = findQuery(""
                    + " SELECT DISTINCT"
                    + "        AR.* "
                    + " FROM   accessrole AR "
                    + " WHERE  AR.accessrole_visible != 0 ");
        } else {
            accessRoleList = findQuery(""
                            + " SELECT DISTINCT"
                            + "        AR.* "
                            + " FROM   accessrole AR "
                            + "        INNER JOIN proguserrole PGR "
                            + "          ON PGR.accessrole_id = AR.accessrole_id "
                            + " WHERE  AR.accessrole_visible != 0 "
                            + "   AND  (PGR.proguser_id = :proguser_id)",
                    "proguser_id", progUserId);
        }
        return accessRoleList;
    }

    /**
     * Список ролей для пользователя с рекурсией
     *
     * @param progUserId - пользователь
     * @return - список
     * @author dav 09.08.2022
     */
    public List<AccessRole> findByUserRecursive(Integer progUserId) {
        List<AccessRole> accessRoleList = findByUser(progUserId);
        if (progUserId == Proguser.SYSDBA_PROGUSER_ID) {
            return accessRoleList;
        }
        // Дополнительные роли, полученный из ролей ролей
        List<AccessRole> accessRoleListTree = new ArrayList<>();
        for (AccessRole accessRole : accessRoleList) {
            List<AccessRole> tree = findQuery(""
                            + " WITH RECURSIVE tree AS ( "
                            + "   SELECT accessrolerole_id, "
                            + "          accessrole_id_parent, "
                            + "          accessrole_id_child "
                            + "   FROM   accessrolerole "
                            + "   WHERE  accessrole_id_parent = :accessrole_id_parent "
                            + "   UNION "
                            + "   SELECT accessrolerole.accessrolerole_id, "
                            + "          accessrolerole.accessrole_id_parent, "
                            + "          accessrolerole.accessrole_id_child "
                            + "   FROM   accessrolerole "
                            + "          JOIN tree "
                            + "            ON accessrolerole.accessrole_id_parent = tree.accessrole_id_child "
                            + " ) "
                            + " SELECT AR.*                      "
                            + " FROM   tree "
                            + "        INNER JOIN accessrole AR ON AR.accessrole_id = accessrole_id_child "
                    , "accessrole_id_parent", accessRole.getAccessRoleId());
            accessRoleListTree.addAll(tree);
        }
        // todo тут бы убрать дубликаты но лень
        accessRoleList.addAll(accessRoleListTree); // Добавим рекурсию
        return accessRoleList;
    }

    public List<ObjectRoleView> findAllObjectRoles() {
        String sqlText = "" +
                "SELECT DISTINCT " +
                "cor.accessrole_id, " +
                "cor.controlobject_id, " +
                "co.controlobject_url, " +
                "cor.sqlaction_id " +
                "FROM   controlobjectrole cor " +
                "INNER JOIN accessrole ar ON ar.accessrole_id=cor.accessrole_id " +
                "INNER JOIN controlobject co ON co.controlobject_id=cor.controlobject_id " +
                "WHERE ar.accessrole_visible!=0";
        return findQuery(ObjectRoleView.class, sqlText);
    }

    /**
     * Связывает пользователя с ролью
     *
     * @param accessRoleId
     * @param progUserId
     */
    public void bindWithProgUser(Integer accessRoleId, Integer progUserId) {
        String sqlText =
                "INSERT INTO proguserrole (proguserrole_id, proguser_id,accessrole_id) " +
                        "VALUES (:id,:proguser_id,:accessrole_id)";
        Map<String, Object> map = new HashMap<>();
        map.put("id", DatabaseUtils.sequenceNextValue("proguserrole_id_gen"));
        map.put("proguser_id", progUserId);
        map.put("accessrole_id", accessRoleId);
        executeSQL(sqlText, map);
    }

    /**
     * Разрушает связь пользователя с ролью
     *
     * @param accessRoleId
     * @param progUserId
     */
    public void unbindProgUser(Integer accessRoleId, Integer progUserId) {
        String sqlText =
                "DELETE FROM proguserrole " +
                        "WHERE accessrole_id=:accessrole_id AND proguser_id=:proguser_id ";
        Map<String, Object> map = new HashMap<>();
        map.put("proguser_id", progUserId);
        map.put("accessrole_id", accessRoleId);
        executeSQL(sqlText, map);
    }

    public void unbindProgUser(Integer progUserId) {
        String sqlText =
                "DELETE FROM proguserrole " +
                        "WHERE proguser_id=:proguser_id ";
        Map<String, Object> map = new HashMap<>();
        map.put("proguser_id", progUserId);
        executeSQL(sqlText, map);
    }

    /**
     * Связывает контролируемый объект с ролью
     *
     * @param accessRoleId
     * @param controlObjectId
     * @param permission
     */
    public void bindWithControlObject(Integer accessRoleId, Integer controlObjectId,
            Permission permission) {
        String sqlText =
                "INSERT INTO controlobjectrole (controlobjectrole_id,controlobject_id,accessrole_id,sqlaction_id) "
                        +
                        "VALUES (:id,:controlobject_id,:accessrole_id,:sqlaction_id)";
        Map<String, Object> map = new HashMap<>();
        map.put("id", DatabaseUtils.sequenceNextValue("controlobjectrole_id_gen"));
        map.put("controlobject_id", controlObjectId);
        map.put("accessrole_id", accessRoleId);
        map.put("sqlaction_id", permission.ordinal());
        executeSQL(sqlText, map);
    }

    /**
     * Разрушает связь контролируемого объекта с ролью
     *
     * @param accessRoleId
     * @param controlObjectId
     */
    public void unbindControlObject(Integer accessRoleId, Integer controlObjectId) {
        String sqlText =
                "DELETE FROM controlobjectrole " +
                        "WHERE accessrole_id=:accessrole_id AND controlobject_id=:controlobject_id ";
        Map<String, Object> map = new HashMap<>();
        map.put("controlobject_id", controlObjectId);
        map.put("accessrole_id", accessRoleId);
        executeSQL(sqlText, map);
    }


    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400230-accessrole.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("accessrole created");
    }

    @Override
    public int load() {
        AccessRole[] data = new AccessRole[]{
                new AccessRole(0, "SYSDBA", "Системный администратор", 1),
                new AccessRole(1, "CEO", "Генеральный директор", 1),
                new AccessRole(2, "DIRECTOR", "Директор", 0),
                new AccessRole(3, "PM", "Менеджер проекта", 1),
                new AccessRole(4, "AM", "Клиент-менеджер", 1),
                new AccessRole(5, "WORKER", "Работник", 1),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d accessrole loaded", data.length));
        DatabaseUtils.setSequence("accessrole_id_gen", data.length + 1);

        return data.length;
    }

    @Override
    public void createFullTextView() {
        String fileName = "sql/400231-ft_accessrole.sql";
        Resource resource = new ClassPathResource(fileName);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info(fileName + " was executed");
    }

}

