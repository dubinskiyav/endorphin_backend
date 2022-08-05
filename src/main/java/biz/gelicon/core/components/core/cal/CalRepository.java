package biz.gelicon.core.components.core.cal;

import biz.gelicon.core.MainApplication;
import biz.gelicon.core.components.core.application.Application;
import biz.gelicon.core.components.core.daycal.Daycal;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.DateUtils;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public class CalRepository implements TableRepository<Cal> {

    private static final Logger logger = LoggerFactory.getLogger(CalRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700110-cal.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("cal created");
    }

    @Override
    public int load() {
        LocalDate dateBeg = DateUtils.stringToDate("01.01.2022")
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateEnd = DateUtils.stringToDate("31.12.2024")
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate d = dateBeg; !d.isAfter(dateEnd); d = d.plusDays(1)) {
            dates.add(d);
        }
        // теперь в dates все даты в интервале
        // Надо их все добавить
        String sqlText = "INSERT INTO cal ("
                + "cal_id, "
                + "yearcal_id, "
                + "monthcal_id, "
                + "daycal_id, "
                + "dayofweek_id, "
                + "weeknumber_id, "
                + "cal_value, "
                + "cal_notation"
                + ")VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        for (LocalDate ld: dates
        ) {
            int yearcal_id = ld.getYear();
            int monthcal_id = ld.getMonthValue();
            int daycal_id = ld.getDayOfMonth();
            int cal_id = yearcal_id * 10000 + monthcal_id * 100 + daycal_id;
            int dayofweek_id = ld.getDayOfWeek().getValue();
            LocalDate date = LocalDate.of(yearcal_id, monthcal_id, daycal_id);
            int weeknumber_id = LocalDate.of(yearcal_id, monthcal_id, daycal_id)
                    .get(WeekFields.of(Locale.getDefault()).weekOfYear())
                    + 1;
            Date cal_value = java.sql.Date.valueOf(ld);
            String cal_notation = DateUtils.dateToStr(cal_value);
            executeSQL(sqlText,new Object[]{
                    cal_id,
                    yearcal_id,
                    monthcal_id,
                    daycal_id,
                    dayofweek_id,
                    weeknumber_id,
                    cal_value,
                    cal_notation
            });
        }
        logger.info(String.format("%d cal loaded", dates.size()));
        DatabaseUtils.setSequence("cal_id_gen", dates.size()+1);

        return dates.size();
    }

}

