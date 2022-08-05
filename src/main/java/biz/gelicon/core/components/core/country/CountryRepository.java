package biz.gelicon.core.components.core.country;

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
public class CountryRepository implements TableRepository<Country> {

    private static final Logger logger = LoggerFactory.getLogger(CountryRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700400-country.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("country created");
    }

    @Override
    public int load() {
        Country[] data =  new Country[] {
                new Country(192,"РОССИЯ", "643"),
                new Country(24,"АЗЕРБАЙДЖАН", "031"),
                new Country(31,"АРМЕНИЯ", "051"),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d country loaded", data.length));
        DatabaseUtils.setSequence("country_id_gen", data.length+1);
        return data.length;
    }

}

