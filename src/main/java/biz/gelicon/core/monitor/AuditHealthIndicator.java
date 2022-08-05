package biz.gelicon.core.monitor;

import biz.gelicon.core.audit.AuditUtils;
import biz.gelicon.core.config.MongoConfig;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Проверка доступности подсистемы аудита
 */
@Component
public class AuditHealthIndicator implements HealthIndicator {
    @Value("${gelicon.audit:false}")
    private Boolean audit;
    @Autowired
    private MongoConfig.MongoTemplateFactory mongoTemplateFactory;

    @Override
    public Health health() {
        String state = audit?"on":"off";
        Health.Builder status;

        if(!audit) {
            status = Health.down();
        } else {
            String dbName = AuditUtils.buildDatabaseName(LocalDate.now());
            try {
                MongoClient mongoCient = mongoTemplateFactory.newMongoClient();
                MongoTemplate template = mongoTemplateFactory.getMongoTemplateForRead(mongoCient, dbName);
                status = Health.up()
                        .withDetail("dbName",dbName);
            } catch (Exception ex) {
                status = Health.down()
                        .withDetail("dbName",dbName);
            }

        }
        return status
                .withDetail("state",state)
                .build();
    }
}
