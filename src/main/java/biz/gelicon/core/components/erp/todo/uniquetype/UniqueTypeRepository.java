package biz.gelicon.core.components.erp.todo.uniquetype;

import biz.gelicon.core.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UniqueTypeRepository implements TableRepository<UniqueType> {

    private static final Logger logger = LoggerFactory.getLogger(UniqueTypeRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public UniqueType findForDocument(Integer docid) {
        List<UniqueType> list = findQuery("SELECT ut.* " +
                        "FROM uniquetypedocument utd " +
                            "INNER JOIN uniquetype ut ON ut.uniquetype_id=utd.uniquetype_id " +
                        "WHERE utd.document_id=:document_id",
                "document_id", docid);
        return list.isEmpty()?null:list.get(0);
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600190-uniquetype.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("uniquetype created");
    }

    @Override
    public void dropForTest() {
        String[] dropTableBefore = new String[]{
                "uniquetypedocument"
        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.dropForTest();

        String[] dropTableAfter = new String[]{
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {
        UniqueType[] data =  new UniqueType[] {
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d uniquetype loaded", data.length));
        return data.length;
    }

}

