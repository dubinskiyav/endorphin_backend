package biz.gelicon.core.components.erp.todo.patternnotification;

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
public class PatternNotificationRepository implements TableRepository<PatternNotification> {

    private static final Logger logger = LoggerFactory.getLogger(PatternNotificationRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600500-patternnotification.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("patternnotification created");
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
        PatternNotification[] data =  new PatternNotification[] {
                new PatternNotification(
                        1,
                        CHANNEL_EMAIL,
                        EVNOT_DEF_DOCUMENT,
                        "<!DOCTYPE html>\n" +
                                "<html lang=\"ru\">\n" +
                                "<head>\n" +
                                "    <meta charset=\"UTF-8\">\n" +
                                "    <title>%TITLE%</title>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "<h2>Уважаемый %RECIPIENT_NAME%!</h2>\n" +
                                "<p>\n" +
                                "    Пользователем %PROGUSER_NAME% изменен документ %DOCUMENTREAL_NAME%. Статус документа %DOCUMENTREAL_STATUS%. Дата изменения документа %DOCUMENTREAL_DATE%\n" +
                                "</p>\n" +
                                "<p>\n" +
                                "    С уважением,\n" +
                                "    Служба поддержки пользователей\n" +
                                "</p>\n" +
                                "</body>\n" +
                                "</html>",
                        "Уведомление об изменении документа")
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d patternnotification loaded", data.length));
        DatabaseUtils.setSequence("patternnotification_id_gen", data.length+1);
        return data.length;
    }

}

