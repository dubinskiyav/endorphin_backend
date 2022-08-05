package biz.gelicon.core.components.core.monthcal;

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
import java.util.HashMap;
import java.util.Map;

@Repository
public class MonthcalRepository implements TableRepository<Monthcal> {

    private static final Logger logger = LoggerFactory.getLogger(MonthcalRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700104-monthcal.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("monthcal created");
    }

    @Override
    public int load() {
        Map<Integer,String> map = new HashMap<>();
        map.put( 1,"Январь");
        map.put( 2,"Февраль");
        map.put( 3,"Март");
        map.put( 4,"Апрель");
        map.put( 5,"Май");
        map.put( 6,"Июнь");
        map.put( 7,"Июль");
        map.put( 8,"Август");
        map.put( 9,"Сентябрь");
        map.put(10,"Октябрь");
        map.put(11,"Ноябрь");
        map.put(12,"Декабрь");
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            String sqlText = "INSERT INTO monthcal ("
                    + "  monthcal_id, "
                    + "  monthcal_value, "
                    + "  monthcal_notation, "
                    + "  monthcal_shortnotation"
                    + ") VALUES(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sqlText);
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                ps.setInt(1,entry.getKey());
                ps.setInt(2,entry.getKey());
                ps.setString(3,entry.getValue());
                String s = String.format("%02d", entry.getKey());
                ps.setString(4, s);
                //ps.executeUpdate();
                // todo dav 02.08.2022
                //  непонятно почему не работает
                 executeSQL(sqlText,new Object[]{
                        entry.getKey(),
                        entry.getKey(),
                        entry.getValue(),
                        String.format("%02d", entry.getKey())
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info(String.format("%d monthcal loaded", map.size()));
        DatabaseUtils.setSequence("monthcal_id_gen", map.size()+1);
        return map.size();
    }

}

