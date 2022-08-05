package biz.gelicon.core.components.core.capjob;

import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;

@Repository
public class CapjobRepository implements TableRepository<Capjob> {

    private static final Logger logger = LoggerFactory.getLogger(CapjobRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700200-capjob.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("capjob created");
    }

    @Override
    public int load() {
        if (true) return 0;
        Date dateBeg = DateUtils.getMinDate();
        Date dateEnd = DateUtils.getMaxDate();
        Capjob[] data =  new Capjob[] {
                new Capjob(1,5,1,"Тестовое задание1",1,2,16,null,9906,1,dateBeg,dateEnd,null,null,null,null,null,null),
                new Capjob(2,6,1,"Тестовое задание2",1,2,1,null,9905,1,dateBeg,dateEnd,null,null,null,null,null,null)
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d capjob loaded", data.length));
        DatabaseUtils.setSequence("capjob_id_gen", data.length+1);
        return data.length;
    }

}

