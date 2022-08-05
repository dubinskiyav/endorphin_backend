package biz.gelicon.core.components.core.capclasstype;

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
public class CapClassTypeRepository implements TableRepository<CapClassType> {

    private static final Logger logger = LoggerFactory.getLogger(CapClassTypeRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400184-capclasstype.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("capclasstype created");
    }

    @Override
    public int load() {
        CapClassType[] data = new CapClassType[]{
                new CapClassType(21, 211, "21", "Группы констант"),
                new CapClassType(20,  39, "20", "Группы признаков"),
                new CapClassType(76,  68, "76", "Типы Электронных материалов"),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d capclasstype loaded", data.length));
        DatabaseUtils.setSequence("capclasstype_id_gen", 1000000);
        return data.length;
    }

}

