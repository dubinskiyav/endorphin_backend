package biz.gelicon.core.components.erp.todo.sgood;

import biz.gelicon.core.components.erp.todo.sgoodtype.Sgoodtype;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.DateUtils;
import biz.gelicon.core.utils.OrmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class SgoodRepository implements TableRepository<Sgood> {

    private static final Logger logger = LoggerFactory.getLogger(SgoodRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${gelicon.run-as-test:false}")
    private Boolean runAsTest;

    public Sgood newFolder(Integer id, Integer clusterId, String name, String code) {
        return new Sgood(id,clusterId, Sgoodtype.FOLDER_SGOODTYPE_ID,name,null,code,
                DateUtils.getMinDate(),DateUtils.getMaxDate(),null,null,null);
    }

    public Sgood newItem(Integer id, Integer clusterId, Integer type, String name, String code) {
        return new Sgood(id,clusterId, type,name,null,code,
                DateUtils.getMinDate(),DateUtils.getMaxDate(),null,null,null);
    }

    @Override
    public int insert(Sgood sgood) {
        // в product работает триггер
        if(runAsTest) {
            // определение paretnId
            if(sgood.getParentId()==null) {
                SqlRowSet rs = jdbcTemplate.queryForRowSet(String.format("SELECT ss.sgood_id " +
                        "FROM subclustergood ss " +
                        "WHERE ss.clustergood_id=%d", sgood.getClustergoodId()));
                if(rs.next()) {
                    sgood.setParentId(rs.getInt(1));
                }
            }
        }

        int result = TableRepository.super.insert(sgood);
        if(result>0 && sgood.getSgoodTypeId()== Sgoodtype.FOLDER_SGOODTYPE_ID) {
            String sqlText;

            // добавляем в clustergood
            int clusterId = DatabaseUtils.sequenceNextValue("clustergood_id_gen");
            sqlText = String.format("INSERT INTO clustergood VALUES (%d)",clusterId);
            OrmUtils.logSQL(sqlText);
            jdbcTemplate.update(sqlText);
            // добавляем в subcluster
            sqlText = String.format("INSERT INTO subclustergood VALUES (%d,%d)",sgood.getSgoodId(),clusterId);
            OrmUtils.logSQL(sqlText);
            jdbcTemplate.update(sqlText);

        }
        return result;
    }


    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/500000-sgood.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("sgood created");
    }

    @Override
    public void dropForTest() {
        String[] dropTableBefore = new String[]{
                "subclustergood",
                "sgoodattributevalue",
                "sgoodedizm"
        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.dropForTest();

        String[] dropTableAfter = new String[]{
                "clustergood"
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {
        Sgood[] data =  new Sgood[] {
                newFolder(1, Sgood.SGOOD_ROOT_ID,"Товары","Т"),
                newFolder(2, Sgood.SGOOD_ROOT_ID,"Материалы","М"),
                newFolder(3, Sgood.SGOOD_ROOT_ID,"Услуги","У"),
                newItem(4,2, Sgoodtype.COMMODITY_SGOODTYPE_ID,"Яблоки","ЯБЛ"),
                newItem(5,2, Sgoodtype.COMMODITY_SGOODTYPE_ID,"Груши","ГР"),
                newItem(6,3, Sgoodtype.COMMODITY_SGOODTYPE_ID,"Болты","БЛТ"),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d sgood loaded", data.length));
        DatabaseUtils.setSequence("sgood_id_gen", data.length+1);
        return data.length;
    }

}

