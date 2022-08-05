package biz.gelicon.core.components.erp.todo.appendix;

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

import java.util.*;

@Repository
public class AppendixRepository implements TableRepository<Appendix> {

    private static final Logger logger = LoggerFactory.getLogger(AppendixRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void bindWithDocumntReal(Integer appendixId, List<Integer> ids) {
        if(ids.isEmpty()) return;
        String sqlText = "" +
                "INSERT INTO docrealappendix (docrealappendix_id, documentreal_id, appendix_id) " +
                "VALUES (:docRealAppendixId, :documentRealId, :appendixId)";
        ids.stream().forEach(documentRealId->{
            executeSQL(sqlText, Map.of(
                    "docRealAppendixId",DatabaseUtils.sequenceNextValue("docrealappendix_id_gen"),
                    "appendixId",appendixId,
                    "documentRealId",documentRealId
            ));
        });

    }

    public void unBindWithDocumntReal(Integer appendixId, List<Integer> ids) {
        if(ids.isEmpty()) return;

        String sqlText = "" +
                "DELETE FROM docrealappendix " +
                "WHERE appendix_id=:appendixId " +
                    "AND documentreal_id IN (:documentrealIds)";
        executeSQL(sqlText, Map.of(
                "appendixId",appendixId,
                "documentrealIds",ids
        ));
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700600-appendix.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("appendix created");
    }

    @Override
    public void dropForTest() {
        String[] dropTableBefore = new String[]{

        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.drop();

        String[] dropTableAfter = new String[]{
                "docrealappendix",
                "proguserappendixtype"
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {
        Appendix[] data =  new Appendix[] {
                new Appendix(1, 211, 1, "file.txt", "1",
                        null, new Date(), null, null,
                        null, "txt", null, null, new Date(), null),
                new Appendix(2, 211, 3, "super.bat", "1",
                        null, new Date(), null, null,
                        null, null, null, null, new Date(), null),
                new Appendix(3, 1268, 2, "text.pdf", "1",
                        null, new Date(), null, null,
                        null, "pdf", null, null, new Date(), null)
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d appendix loaded", data.length));
        DatabaseUtils.setSequence("appendix_id_gen", data.length+1);
        return data.length;
    }

}

