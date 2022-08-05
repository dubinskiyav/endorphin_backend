package biz.gelicon.core.components.erp.todo.notificationsetting;

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

import static biz.gelicon.core.components.core.capcode.CapCode.CHANNEL_EMAIL;
import static biz.gelicon.core.components.core.capcode.CapCode.EVNOT_DEF_DOCUMENT;

@Repository
public class NotificationSettingRepository implements TableRepository<NotificationSetting> {

    private static final Logger logger = LoggerFactory.getLogger(NotificationSettingRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600400-notificationsetting.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("notificationsetting created");
    }

    @Override
    public void dropForTest() {
        String[] dropTableBefore = new String[]{
        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.dropForTest();

        String[] dropTableAfter = new String[]{
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {
        NotificationSetting[] data =  new NotificationSetting[] {
                new NotificationSetting(1,1,EVNOT_DEF_DOCUMENT,CHANNEL_EMAIL),
                new NotificationSetting(2,1,1,CHANNEL_EMAIL)
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d notificationsetting loaded", data.length));
        DatabaseUtils.setSequence("notificationsetting_id_gen", data.length+1);
        return data.length;
    }

}

