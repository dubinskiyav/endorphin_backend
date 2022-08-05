package biz.gelicon.core.components.core.notification;

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
public class NotificationRepository implements TableRepository<Notification> {

    private static final Logger logger = LoggerFactory.getLogger(NotificationRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600200-notification.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("notification created");
    }

    @Override
    public int load() {
        Notification[] data =  new Notification[] {
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d notification loaded", data.length));
        DatabaseUtils.setSequence("notification_id_gen", data.length+1);
        return data.length;
    }

}

