package biz.gelicon.core.components.core.attribute;

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
public class AttributeRepository implements TableRepository<Attribute> {

    private static final Logger logger = LoggerFactory.getLogger(AttributeRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400480-attribute.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("attribute created");
    }

    @Override
    public int load() {
        if (true) return 0;
        Attribute[] data =  new Attribute[] {
                new Attribute(4,57,404,null,0,null)
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d attribute loaded", data.length));
        return data.length;
    }

}

