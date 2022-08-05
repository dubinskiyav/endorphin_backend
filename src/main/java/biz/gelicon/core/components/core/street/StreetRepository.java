package biz.gelicon.core.components.core.street;

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
public class StreetRepository implements TableRepository<Street> {

    private static final Logger logger = LoggerFactory.getLogger(StreetRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700424-street.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("street created");
    }

    @Override
    public int load() {
        Street[] data = new Street[] {
                new Street(1, 1, 1, "Ленина", "590000010000102",  null, null, new Date()),
                new Street(2, 1, 1, "Профессора Дедюкина", "590000010000111",  null, null, new Date()),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d street loaded", data.length));
        DatabaseUtils.setSequence("street_id_gen", data.length+1);
        return data.length;
    }

}

