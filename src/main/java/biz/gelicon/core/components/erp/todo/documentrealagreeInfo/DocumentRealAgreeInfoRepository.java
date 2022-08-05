package biz.gelicon.core.components.erp.todo.documentrealagreeInfo;

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
public class DocumentRealAgreeInfoRepository implements TableRepository<DocumentRealAgreeInfo> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentRealAgreeInfoRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600920-documentrealagreeinfo.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documentrealagreeinfo created");
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
        DocumentRealAgreeInfo[] data =  new DocumentRealAgreeInfo[] {
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d documentrealagreeinfo loaded", data.length));
        DatabaseUtils.setSequence("documentrealagreeinfo_id_gen", data.length+1);
        return data.length;
    }

}

