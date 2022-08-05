package biz.gelicon.core.components.core.dayofweek;

import biz.gelicon.core.components.core.daycal.Daycal;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DayofweekRepository implements TableRepository<Dayofweek> {

    private static final Logger logger = LoggerFactory.getLogger(DayofweekRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700102-dayofweek.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("dayofweek created");
    }

    private class week {
        int number;
        String notation;
        String shortnotation;

        public week(int number, String notation, String shortnotation) {
            this.number = number;
            this.notation = notation;
            this.shortnotation = shortnotation;
        }
    }
    @Override
    public int load() {
        List<week> weeks = new ArrayList<>();
        weeks.add(new week(1, "Понедельник", "ПН"));
        weeks.add(new week(2, "Вторник",     "ВТ"));
        weeks.add(new week(3, "Среда",       "СР"));
        weeks.add(new week(4, "Четверг",     "ЧТ"));
        weeks.add(new week(5, "Пятница",     "ПТ"));
        weeks.add(new week(6, "Суббота",     "СБ"));
        weeks.add(new week(7, "Воскресенье", "ВС"));
        String sqlText = "INSERT INTO dayofweek ("
                + " dayofweek_id, "
                + " dayofweek_value, "
                + " dayofweek_notation, "
                + " dayofweek_shortnotation "
                + ") VALUES(?, ?, ?, ?)";
        weeks.stream().forEach(w -> {
            executeSQL(sqlText,new Object[]{
                    w.number,
                    w.number,
                    w.notation,
                    w.shortnotation
            });
        });
        logger.info(String.format("%d dayofweek loaded", weeks.size()));
        DatabaseUtils.setSequence("dayofweek_id_gen", weeks.size()+1);
        return weeks.size();
    }

}

