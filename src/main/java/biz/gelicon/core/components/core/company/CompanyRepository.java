package biz.gelicon.core.components.core.company;

import biz.gelicon.core.components.core.companycode.CompanyCodeDTO;
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

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository implements TableRepository<Company> {

    private static final Logger logger = LoggerFactory.getLogger(CompanyRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertCompanyCode(CompanyCodeDTO dto) {
        String sql = "" +
                "INSERT INTO companycode VALUES (:companyCodeId, :companyId, :capClassId, :code)";
        Map<String, Object> params = new HashMap<>(Map.of("companyId", dto.getCompanyId(),
                "capClassId", dto.getCapClassId(),
                "code", dto.getCode()));
        if (dto.getCompanyCodeId() == null) {
            params.put("companyCodeId", DatabaseUtils.sequenceNextValue("companycode_id_gen"));
        } else {
            params.put("companyCodeId", dto.getCompanyCodeId());
        }
        executeSQL(sql, params);
    }

    public void updateCompanyCode(CompanyCodeDTO dto) {
        String sql = "" +
                "UPDATE companycode " +
                "SET companycode_code = :code " +
                "WHERE company_id = :companyId " +
                "AND capclass_id = :capClassId ";
        Map<String, Object> params = new HashMap<>(Map.of("companyId", dto.getCompanyId(),
                "capClassId", dto.getCapClassId()));
        params.put("code", dto.getCode());
        if (dto.getCompanyCodeId() == null) {
            params.put("companyCodeId", DatabaseUtils.sequenceNextValue("companycode_id_gen"));
        } else {
            params.put("companyCodeId", dto.getCompanyCodeId());
        }
        executeSQL(sql, params);
    }

    public boolean existCompanyCode(CompanyCodeDTO dto) {
        String sql = "" +
                "SELECT * " +
                "FROM companycode cc " +
                "WHERE cc.company_id = :companyId " +
                "AND cc.capclass_id = :capClassId ";
        Map<String, Object> params = new HashMap<>(Map.of("companyId", dto.getCompanyId(),
                "capClassId", dto.getCapClassId()));
        return findQueryExists(sql, params);
    }

    /*Метод для удаления записей из таблицы companycode
     * Если capClassId == null то удаляются все записи(Используется при удалении контрагента),
     * иначе только определенная*/
    public void deleteCompanyCode(Integer companyId, Integer capClassId) {
        if (capClassId == null) {
            String sql = "" +
                    "DELETE FROM companycode WHERE company_id = :companyId";
            Map<String, Object> params = new HashMap<>(Map.of("companyId", companyId));
            executeSQL(sql, params);
        } else {
            String sql = "" +
                    "DELETE FROM companycode WHERE company_id = :companyId AND capclass_id = :capClassId";
            Map<String, Object> params = new HashMap<>(Map.of("companyId", companyId,
                    "capClassId", capClassId));
            executeSQL(sql, params);
        }
    }

    public Company getProductOwner() {
        List<Company> list = findQuery("" +
                "SELECT c.* " +
                "FROM mycompany mc " +
                "INNER JOIN company c ON c.company_id=mc.company_id ");
        return list.isEmpty() ? null : list.get(0);
    }

    public void setProductOwner(Integer companyId) {
        executeSQL("DELETE FROM mycompany");
        String sqlText =
                "INSERT INTO mycompany(company_id) " +
                        "VALUES (:companyId)";
        executeSQL(sqlText, "companyId", companyId);
    }

    public List<Integer> getBranches(Integer companyMainId) {
        return findQueryForMap("" +
                "SELECT cb.company_id " +
                "FROM companybranch cb " +
                "WHERE cb.companymain_id=:companyId", "companyId", companyMainId)
                .stream()
                .map(m -> (Integer) m.get("company_id"))
                .collect(Collectors.toList());
    }

    public List<Company> getBranchesAsCompany(Integer companyMainId) {
        return findQuery("" +
                "SELECT c.* " +
                "FROM companybranch cb " +
                "INNER JOIN company c ON c.company_id=cb.company_id " +
                "WHERE cb.companymain_id=:companyId " +
                "ORDER BY c.company_name", "companyId", companyMainId);
    }


    public void addBranch(Integer companyMainId, Integer companyId) {
        String sqlText = buildInsertClause("companybranch",
                new String[]{"companymain_id", "company_id"},
                new String[]{"companymainId", "companyId"});
        Map<String, Object> params = new HashMap<>();
        params.put("companymainId", companyMainId);
        params.put("companyId", companyId);
        executeSQL(sqlText, params);
    }

    public Integer getParentCompany(Integer companyId) {
        return findQueryForObject(Integer.class, "" +
                "SELECT cb.companymain_id " +
                "FROM companybranch cb " +
                "WHERE cb.company_id=:companyId", "companyId", companyId);
    }

    @Override
    public void create() {
        Resource resource;
        resource = new ClassPathResource("sql/700100-company.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("company created");
    }


    @Override
    public int load() {
        Company[] data = new Company[]{
                new Company(6, 1, null, "Рогов и Копытов, ООО", "ООО \"Рогов и Копытов\"", null,
                        "г.Пермь, ул. Ленина 46", "+79883456745", null, null,
                        "Продают все кроме родины", 0,
                        new Date(), 0, 0, 0, 0, "г.Пермь, ул. Ленина 46", 1),
                new Company(7, 1, null, "Лен и Ого, ООО", "ООО \"Лен и Ого\"", null,
                        "г.Пермь, ул. Крыжовная 11", "+79883456745", null, null, (String) null, 0,
                        new Date(), 0, 0, 0, 0, "г.Пермь, ул. Крыжовная 11", 1),
                new Company(8, 1, null, "Постоянные ребята, ООО", "ООО Постоянные ребята", null,
                        "г.Пермь, ул. Людей 369", "+79111111111", null, null, (String) null, 1,
                        new Date(), 0, 0, 0, 0, "г.Пермь, ул. Людей 369\"", 1),
        };
        insert(Arrays.asList(data));

        setProductOwner(6);
        addBranch(6, 7);

        logger.info(String.format("%d company loaded", data.length));
        DatabaseUtils.setSequence("company_id_gen", data.length + 1);
        return data.length;
    }

}

