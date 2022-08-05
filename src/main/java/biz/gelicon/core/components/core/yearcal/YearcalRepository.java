package biz.gelicon.core.components.core.yearcal;

import biz.gelicon.core.components.core.monthcal.Monthcal;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class YearcalRepository implements TableRepository<Yearcal> {

    private static final Logger logger = LoggerFactory.getLogger(YearcalRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700106-yearcal.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("yearcal created");
    }

    @Override
    public int load() {
        int count = 0;
        String sqlText = "INSERT INTO yearcal ("
                + "yearcal_id, "
                + "yearcal_value, "
                + "yearcal_notation, "
                + "yearcal_shortnotation"
                + " ) VALUES ("
                + ":yearcal_id, "
                + ":yearcal_value, "
                + ":yearcal_notation, "
                + ":yearcal_shortnotation"
                + ")";
            for (int i = 1999; i <= 2098; i++) {
                executeSQL(sqlText, Map.of(
                        "yearcal_id", i,
                        "yearcal_value", i,
                        "yearcal_notation", Integer.toString(i),
                        "yearcal_shortnotation", Integer.toString(i).substring(2)
                ));
                count++;
            }
        logger.info(String.format("%d yearcal loaded", count));
        DatabaseUtils.setSequence("yearcal_id_gen", count+1);
        return count;
    }

}

