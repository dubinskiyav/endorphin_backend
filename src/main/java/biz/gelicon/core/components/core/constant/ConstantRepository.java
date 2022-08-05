package biz.gelicon.core.components.core.constant;

import biz.gelicon.core.repository.TableRepository;
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
public class ConstantRepository implements TableRepository<Constant> {

    private static final Logger logger = LoggerFactory.getLogger(ConstantRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400490-constant.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("constant created");
    }

    @Override
    public int load() {
        if (true) return 0;
        Constant[] data =  new Constant[] {
                new Constant(3,211,1)
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d constant loaded", data.length));
        return data.length;
    }

}

