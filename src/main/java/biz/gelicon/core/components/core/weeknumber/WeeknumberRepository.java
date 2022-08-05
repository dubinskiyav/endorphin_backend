package biz.gelicon.core.components.core.weeknumber;

import biz.gelicon.core.components.core.yearcal.Yearcal;
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

@Repository
public class WeeknumberRepository implements TableRepository<Weeknumber> {

    private static final Logger logger = LoggerFactory.getLogger(WeeknumberRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700108-weeknumber.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("weeknumber created");
    }

    @Override
    public int load() {
        int count = 54;
        String sqlText = "INSERT INTO weeknumber ("
                + "  weeknumber_id "
                + ") VALUES(?)";
        for (int i = 1; i <= count; i++) {
            executeSQL(sqlText,new Object[]{
                    i
            });
        }
        logger.info(String.format("%d weeknumber loaded", count));
        DatabaseUtils.setSequence("weeknumber_id_gen", count+1);
        return count;
    }

}

