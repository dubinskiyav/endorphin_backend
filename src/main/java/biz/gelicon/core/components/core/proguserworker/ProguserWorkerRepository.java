package biz.gelicon.core.components.core.proguserworker;

import biz.gelicon.core.components.core.capconfig.Capconfig;
import biz.gelicon.core.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

@Repository
public class ProguserWorkerRepository implements TableRepository<ProguserWorker> {

    private static final Logger logger = LoggerFactory.getLogger(ProguserWorkerRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700560-proguserworker.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("proguserworker created");
    }

    @Override
    public int load() {
        return 0;
    }

}

