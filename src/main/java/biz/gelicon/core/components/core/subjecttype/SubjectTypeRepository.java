package biz.gelicon.core.components.core.subjecttype;

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
public class SubjectTypeRepository implements TableRepository<SubjectType> {

    private static final Logger logger = LoggerFactory.getLogger(SubjectTypeRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600090-subjecttype.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("subjecttype created");
    }

    @Override
    public int load() {
        SubjectType[] data =  new SubjectType[] {
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d subjecttype loaded", data.length));
        DatabaseUtils.setSequence("subjecttype_id_gen", data.length+1);
        return data.length;
    }

}

