package biz.gelicon.core.components.core.capresource;

import biz.gelicon.core.artifacts.ArtifactCapconfigKind;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactTranKinds;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.OrmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Репозиторий артефактов
 */
@Repository
public class ArtifactRepository implements TableRepository<Artifact> {


    private static final Logger logger = LoggerFactory.getLogger(ArtifactRepository.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getDocumentBindingCounts(){
        return findQueryForMap(
                "SELECT DOCUMENT_ID, " +
                        "CAPRESOURCE_ID, " +
                        "COUNT(*) AS count_pos " +
                        "FROM CAPRESOURCENUMBERDOC " +
                        "GROUP BY DOCUMENT_ID, CAPRESOURCE_ID "
        );
    }

    public List<Integer> getArtifactIdByCode(String artifactCode){
        String sqlText = "" +
                "SELECT CAPRESOURCE_ID " +
                "FROM CAPRESOURCE " +
                "WHERE CAPRESOURCE_CODE = :code ";
        List<Integer> artifactId = new ArrayList<>();
        findQueryForMap(sqlText, Map.of("code", artifactCode))
                .forEach((row) -> {
                    artifactId.add((Integer)row.get("capresource_id"));
                });
        return artifactId;
    }

    public void updateArtifactByCode(Artifact artifact){
        Map<String, Object> params = new HashMap<>();
        params.put("code", artifact.getArtifactCode());
        params.put("name", artifact.getArtifactName());
        params.put("lastModify", artifact.getArtifactLastmodify());

        executeSQL(
                "UPDATE CAPRESOURCE SET " +
                        "CAPRESOURCE_NAME = :name, " +
                        "CAPRESOURCE_LASTMODIFY = :lastModify " +
                        "WHERE CAPRESOURCE_CODE = :code ",
                params
        );
    }
    /**
     * Вовзращает список capresource_id у application_id
     * @param applicationId
     * @return
     */
    public List<Integer> getArtifactIdList(Integer applicationId) {
        if (applicationId == null) {
            return new ArrayList<Integer>(); // Чтобы не проверять после вызова
        }
        List<Map<String, Object>> data = findQueryForMap(""
                        + " SELECT CRA.capresource_id "
                        + " FROM   capresourceapp CRA "
                        + " WHERE  CRA.application_id = :application_id ",
                "application_id", applicationId
        );
        return data.stream()
                .map(e -> (Integer) e.get("capresource_id"))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список document_id - нумераторов для артефакта (capresource_id)
     *
     * @param artifactId - capresource_id
     * @return Список document_id
     */
    public List<Integer> getLinkWithDocuments(Integer artifactId) {
        List<Map<String, Object>> data = findQueryForMap(""
                        + " SELECT CRND.document_id "
                        + " FROM   capresourcenumberdoc CRND "
                        + " WHERE  CRND.capresource_id = :capresource_id ",
                "capresource_id", artifactId
        );
        return data.stream()
                .map(e -> (Integer) e.get("document_id"))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список application_id у артефакта (capresource_id)
     *
     * @param artifactId - capresource_id
     * @return Список application_id
     */
    public List<Integer> getLinkWithApplications(Integer artifactId) {
        List<Map<String, Object>> data = findQueryForMap(""
                        + " SELECT CRA.application_id "
                        + " FROM   capresourceapp CRA "
                        + " WHERE  CRA.capresource_id = :capresource_id ",
                "capresource_id", artifactId
        );
        return data.stream()
                .map(e -> (Integer) e.get("application_id"))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список артефактов - внешних ресурсов
     *
     * @param tranKinds     - если null - все
     * @param kind
     * @param capconfigKind
     * @return
     */
    public List<Artifact> getExternalArtifacts(
            ArtifactTranKinds tranKinds,
            ArtifactKinds kind,
            ArtifactCapconfigKind capconfigKind
    ) {
        Map<String, Object> params = new HashMap<>();
        // Тип языка ресурса
        params.put("resourcetrantype_id", tranKinds != null ? tranKinds.getResourceTranType() : 0);
        // Тип ресурса
        params.put("resourcetype_id", kind != null ? kind.getResourceType() : 0);
        // Конфигурация
        params.put("capconfig_id",
                capconfigKind != null ? capconfigKind.getResourceCapconfigType() : 0);
        // вернуть выбор R.*
        return findQuery(""
                        + " SELECT R.*  "
                        + " FROM   capresource R "
                        + " WHERE  (R.resourcetype_id = :resourcetype_id OR :resourcetype_id = 0) "
                        + "   AND  (R.resourcetrantype_id = :resourcetrantype_id OR :resourcetrantype_id = 0) "
                        + "   AND  (R.capconfig_id = :capconfig_id OR :capconfig_id = 0) ",
                params);
    }

    public List<Artifact> getRestrictArtifacts(int progUserId, ArtifactKinds kind) {
        Map<String, Object> params = new HashMap<>();
        params.put("proguser_id", progUserId);
        params.put("kind", kind.getResourceType());
        return findQuery("select a.* " +
                "from capresource a " +
                "inner join capresourcerole r on r.capresource_id = a.capresource_id AND r.capresourcerole_restrictflag<>0 "
                +
                "inner join proguserrole pur on pur.accessrole_id = r.accessrole_id  " +
                "where pur.proguser_id =:proguser_id and  " +
                "a.resourcetype_id=:kind", params);
    }

    public List<Artifact> getAllowArtifacts(int progUserId, ArtifactKinds kind) {
        Map<String, Object> params = new HashMap<>();
        params.put("proguser_id", progUserId);
        params.put("kind", kind.getResourceType());
        return findQuery("select a.* " +
                "from capresource a " +
                "inner join capresourcerole r on r.capresource_id =a.capresource_id AND r.capresourcerole_restrictflag=0 "
                +
                "inner join proguserrole pur on pur.accessrole_id = r.accessrole_id  " +
                "where pur.proguser_id =:proguser_id and  " +
                "a.resourcetype_id=:kind", params);
    }

    public boolean isRestricted(String code, ArtifactKinds kind, int progUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("proguser_id", progUserId);
        params.put("kind", kind.getResourceType());
        params.put("code", code);
        return countQuery("select count(*) " +
                "from capresource a " +
                "inner join capresourcerole r on r.capresource_id =a.capresource_id AND r.capresourcerole_restrictflag<>0 "
                +
                "inner join proguserrole pur on pur.accessrole_id = r.accessrole_id " +
                "where " +
                "a.capresource_code = :code and " +
                "pur.proguser_id = :proguser_id and " +
                "a.resourcetype_id=:kind", params) > 0;
    }

    private boolean isLinkedWithRole(Integer accessRoleId, Integer appId) {
        String sqlText = "SELECT COUNT(*) " +
                "FROM capresourcerole " +
                "WHERE accessrole_id=:accessrole_id AND " +
                "capresource_id=:artifact_id";
        Map<String, Object> params = new HashMap<>();
        params.put("artifact_id", appId);
        params.put("accessrole_id", accessRoleId);
        Integer count = findQueryForObject(Integer.class, sqlText, params);
        return count.intValue() > 0;
    }


    public int restrict(Integer accessRoleId, Integer artiId) {
        if (isLinkedWithRole(accessRoleId, artiId)) {
            return 0;
        }
        String sqlText = buildInsertClause("capresourcerole",
                new String[]{"capresourcerole_id", "capresource_id", "accessrole_id",
                        "capresourcerole_restrictflag"},
                new String[]{"capresourcerole_id", "capresource_id", "accessrole_id",
                        "capresourcerole_restrictflag"});
        int id = DatabaseUtils.sequenceNextValue("capresourcerole_id_gen");
        Map<String, Object> params = new HashMap<>();
        params.put("capresourcerole_id", id);
        params.put("capresource_id", artiId);
        params.put("accessrole_id", accessRoleId);
        params.put("capresourcerole_restrictflag", 1); // это ограничение
        executeSQL(sqlText, params);
        return id;
    }

    public int allow(Integer accessRoleId, Integer artiId) {
        if (isLinkedWithRole(accessRoleId, artiId)) {
            return 0;
        }
        String sqlText = buildInsertClause("capresourcerole",
                new String[]{"capresourcerole_id", "capresource_id", "accessrole_id",
                        "capresourcerole_restrictflag"},
                new String[]{"capresourcerole_id", "capresource_id", "accessrole_id",
                        "capresourcerole_restrictflag"});
        int id = DatabaseUtils.sequenceNextValue("capresourcerole_id_gen");
        Map<String, Object> params = new HashMap<>();
        params.put("capresourcerole_id", id);
        params.put("capresource_id", artiId);
        params.put("accessrole_id", accessRoleId);
        params.put("capresourcerole_restrictflag", 0); // это разрешение
        executeSQL(sqlText, params);
        return id;
    }

    public void dropLink(Integer accessRoleId, Integer artiId) {
        String sqlText = "DELETE FROM capresourcerole " +
                "WHERE accessrole_id=:accessrole_id AND capresource_id=:capresource_id";
        Map<String, Object> params = new HashMap<>();
        params.put("capresource_id", artiId);
        params.put("accessrole_id", accessRoleId);
        executeSQL(sqlText, params);

    }

    public void dropApplicationBinding(Integer artifactId) {
        String sqlText = "DELETE FROM capresourceapp " +
                "WHERE capresource_id=:capresource_id";
        Map<String, Object> params = new HashMap<>();
        params.put("capresource_id", artifactId);
        executeSQL(sqlText, params);
    }

    public void dropDocumentBinding(Integer artifactId) {
        String sqlText = "DELETE FROM capresourcenumberdoc " +
                "WHERE capresource_id=:capresource_id";
        Map<String, Object> params = new HashMap<>();
        params.put("capresource_id", artifactId);
        executeSQL(sqlText, params);
    }

    public void insertApplicationBinding(Integer artifactId, List<Integer> appIds) {
        String sqlText =
                "INSERT INTO capresourceapp(capresourceapp_id,capresource_id,application_id) " +
                        " VALUES (:capresourceapp_id,:capresource_id,:application_id)";
        appIds.forEach(appId -> {
            Map<String, Object> params = new HashMap<>();
            Integer genId = DatabaseUtils.sequenceNextValue("capresourceapp_id_gen");
            params.put("capresourceapp_id", genId);
            params.put("capresource_id", artifactId);
            params.put("application_id", appId);
            executeSQL(sqlText, params);
        });
    }

    public void insertDocumentBinding(Integer artifactId, List<Integer> docIds) {
        String sqlText =
                "INSERT INTO capresourcenumberdoc(capresourcenumberdoc_id,capresource_id,document_id) "
                        +
                        " VALUES (:capresourcenumberdoc_id,:capresource_id,:document_id)";
        docIds.forEach(id -> {
            Map<String, Object> params = new HashMap<>();
            Integer genId = DatabaseUtils.sequenceNextValue("capresourcenumberdoc_id_gen");
            params.put("capresourcenumberdoc_id", genId);
            params.put("capresource_id", artifactId);
            params.put("document_id", id);
            executeSQL(sqlText, params);
        });
    }

    /**
     * Вставляет в capresourcenumberdoc, если его не было
     *
     * @param artifactId артефакт
     * @param documentId документ
     */
    public void insertIfNotExistsDocumentBinding(
            Integer artifactId,
            Integer documentId
    ) {
        // kav 20210611 "нах не надо. Есьт удаление и вставка. Этого достаточно"
        // ЗАНЧИТ ТАК:
        // 1) При вставке нужно сделать insert
        // 2) При изменении, если нет связи, сдеалать insert
        Map<String, Object> params = new HashMap<>();
        params.put("capresource_id", artifactId);
        params.put("document_id", documentId);
        // Проверим, есть ли
        String sqlText = ""
                + " SELECT COUNT(*) "
                + " FROM   capresourcenumberdoc "
                + " WHERE  capresource_id = :capresource_id "
                + "   AND  document_id = :document_id ";
        OrmUtils.logSQL(sqlText);
        Integer count = jdbcTemplate.queryForObject(sqlText, params, Integer.class);
        if (count == 0) { // Связи нет
            // Вставляем
            List<Integer> docIds = Arrays.asList(documentId);
            insertDocumentBinding(artifactId, docIds);
        }
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400406-capresource.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getJdbcTemplate().getDataSource());
        logger.info("capresource created");
    }

    @Override
    public int load() {
        Artifact[] data = new Artifact[]{
                new Artifact(1, "0001", "Список пользователей",
                        ArtifactKinds.REPORT.getResourceType()),
                new Artifact(2, "TST_01", "Нумератор тестовый",
                        ArtifactKinds.NUMERATOR.getResourceType()),
                new Artifact(3, "CONST_01", "Константа тестовая",
                        ArtifactKinds.CONST.getResourceType()),
                new Artifact(4, "ATTR_01", "Атрибут тестовый",
                        ArtifactKinds.ATTRIBUTE.getResourceType()),
                new Artifact(5, "UDF_SUM", "Функция пользователя тестовая",
                        ArtifactKinds.USER_FUNC.getResourceType()),
                new Artifact(6, "UDF_TEST_ERR", "Функция пользователя тестовая с ошибкой",
                        ArtifactKinds.USER_FUNC.getResourceType()),
                new Artifact(211, "21", "Группы констант",
                        ArtifactKinds.CONST.getResourceType()),
                new Artifact(39, "20", "Группы признаков",
                        ArtifactKinds.ATTRIBUTE.getResourceType()),
                new Artifact(68, "76", "Типы электронных материалов",
                        15),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d artifact loaded", data.length));
        DatabaseUtils.setSequence("capresource_id_gen", 1000000);
        return data.length;
    }

}

