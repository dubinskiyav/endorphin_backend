package biz.gelicon.core.components.core.capjobhistory;

import biz.gelicon.core.components.core.capcode.CapCode;
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

import static biz.gelicon.core.components.core.capcode.CapCode.CAPJOB_STATE;

@Repository
public class CapjobhistoryRepository implements TableRepository<Capjobhistory> {

    private static final Logger logger = LoggerFactory.getLogger(CapjobhistoryRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700300-capjobhistory.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("capjobhistory created");
    }

    @Override
    public int load() {
        if (true) return 0;
        Date dateBeg = DateUtils.getMinDate();
        Date dateEnd = DateUtils.getMaxDate();
        CapCode[] capjob_state = new CapCode[]{
                new CapCode(3,CAPJOB_STATE,"01","Состояние задания1",null,null)
        };
        Capjobhistory[] data =  new Capjobhistory[] {
                new Capjobhistory(1,1,3,dateBeg,dateEnd,null,null,null)
        };

        insert(Arrays.asList(data));
        logger.info(String.format("%d capjobhistory loaded", data.length));
        DatabaseUtils.setSequence("capjobhistory_id_gen", data.length+1);
        return data.length;
    }

}

