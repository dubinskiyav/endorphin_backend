package biz.gelicon.core.components.core.capclass;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CapClassRepository implements TableRepository<CapClass> {

    private static final Logger logger = LoggerFactory.getLogger(CapClassRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CapClass findByCode(Integer capclasstypeId, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("capclasstypeId", capclasstypeId);
        params.put("capclassCode", code);
        List<CapClass> list = findWhere(
                "capclass_code=:capclassCode and capclasstype_id=:capclasstypeId", params);
        return list.isEmpty() ? null : list.get(0);
    }


    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400186-capclass.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("capclass created");
    }

    @Override
    public int load() {
        CapClass[] data = new CapClass[]{
                new CapClass(211, 21, "01", "Группа констант для заявок",
                        null, null, null, null, 0, null),
                new CapClass(1268, 21, "04", "Группа НДС",
                        null, null, null, null, 0, null),
                new CapClass(1897, 21, "НАЛОГ", "Налоги",
                        null, null, null, null, 0, null),
                new CapClass(57, 20, "01", "Сотрудники",
                        null, null, null, null, 0, null),
                new CapClass(555, 76, "01", "Файл",
                        null, null, null, null, 0, null),
                new CapClass(556, 76, "02", "Изображение",
                        null, null, null, null, 0, null),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d capclass loaded", data.length));
        DatabaseUtils.setSequence("capclass_id_gen", 1000000);
        return data.length;
    }

}

