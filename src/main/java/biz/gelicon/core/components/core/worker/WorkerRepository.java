package biz.gelicon.core.components.core.worker;

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
import java.util.Date;
import java.util.List;

@Repository
public class WorkerRepository implements TableRepository<Worker> {

    private static final Logger logger = LoggerFactory.getLogger(WorkerRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Worker findWorkerLinkWithUser(Integer proguserId) {
        List<Worker> list = findQuery("" +
                "SELECT w.* " +
                "FROM worker w " +
                "INNER JOIN proguserworker puw ON puw.worker_id=w.worker_id " +
                "WHERE puw.proguser_id=:proguserId", "proguserId", proguserId);
        return list.isEmpty()?null:list.get(0);
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700550-worker.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("worker created");
    }

    @Override
    public int load() {
        Worker[] data =  new Worker[] {
                new Worker(1,"0001","Иванов","Иван","Иванович",1,new Date(),null,null,null,null,null),
                new Worker(2,"0002","Петров","Петр","",1,new Date(),null,null,null,null,null),
                new Worker(3,"0003","Сидоров","Захар","Кирилович",1,new Date(),null,null,null,null,null)
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d worker loaded", data.length));
        DatabaseUtils.setSequence("worker_id_gen", data.length+1);
        return data.length;
    }

}

