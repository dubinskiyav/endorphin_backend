package biz.gelicon.core.components.core.constantvalue;

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
import java.util.Date;

@Repository
public class ConstantValueRepository implements TableRepository<ConstantValue> {

    private static final Logger logger = LoggerFactory.getLogger(ConstantValueRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400500-constantvalue.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("constantvalue created");
    }

    @Override
    public int load() {
        if (true) return 0;
        ConstantValue[] data =  new ConstantValue[] {
                new ConstantValue(1,3,new Date(),"1")
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d constantvalue loaded", data.length));
        DatabaseUtils.setSequence("constantvalue_id_gen", data.length+1);
        return data.length;
    }

}

