package biz.gelicon.core.components.core.town;

import biz.gelicon.core.components.core.country.Country;
import biz.gelicon.core.components.core.townsubordinate.TownSubordinate;
import biz.gelicon.core.components.core.towntype.TownType;
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
public class TownRepository implements TableRepository<Town> {

    private static final Logger logger = LoggerFactory.getLogger(TownRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700416-town.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("town created");
    }

    @Override
    public int load() {
        Town[] data =  new Town[] {
                new Town(1, TownType.CITY, TownSubordinate.CAPITAL_OF_REGION, Country.RUSSIA,"Пермь","59",
                        "342",null,new Date()),
                new Town(2, TownType.CITY, TownSubordinate.CAPITAL_OF_REGION, Country.RUSSIA,"Санкт-Петербург","08",
                        "342",null,new Date()),
                new Town(3, TownType.CITY, TownSubordinate.CAPITAL_OF_REGION, Country.RUSSIA,"Владивосток","21",
                        "342",null,new Date())
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d town loaded", data.length));
        DatabaseUtils.setSequence("town_id_gen", data.length+1);
        return data.length;
    }

}

