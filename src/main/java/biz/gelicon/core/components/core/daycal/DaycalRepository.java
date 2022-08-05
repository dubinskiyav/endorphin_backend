package biz.gelicon.core.components.core.daycal;

import biz.gelicon.core.components.core.capconfig.Capconfig;
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
public class DaycalRepository implements TableRepository<Daycal> {

    private static final Logger logger = LoggerFactory.getLogger(DaycalRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700100-daycal.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("daycal created");
    }

    @Override
    public int load() {
        int dayCount = 31;
        String sqlText = "INSERT INTO daycal ("
                + "  daycal_id, "
                + "  daycal_value, "
                + "  daycal_notation "
                + ") VALUES(?, ?, ?)";
        for (int i = 1; i <= dayCount; i++) {
            executeSQL(sqlText,new Object[]{
                    i,
                    i,
                    String.format("%02d", i)
            });
        }
        logger.info(String.format("%d daycal loaded", dayCount));
        DatabaseUtils.setSequence("daycal_id_gen", dayCount+1);
        return dayCount;
    }

}

