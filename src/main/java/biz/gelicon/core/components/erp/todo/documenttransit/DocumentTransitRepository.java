package biz.gelicon.core.components.erp.todo.documenttransit;

import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.QueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DocumentTransitRepository implements TableRepository<DocumentTransit> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentTransitRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DocumentTransit> findByDocument(Integer documentId) {
        return findWhere("document_id=:documentId","documentId",
                documentId, QueryUtils.allPagesAndSort("documenttransit_number"));
    }

    private boolean isAllowRole(Integer accessRoleId, Integer documenttransitId) {
        String sqlText = "SELECT COUNT(*) " +
                "FROM documenttransitrole " +
                "WHERE accessrole_id=:accessrole_id AND " +
                "documenttransit_id=:documenttransit_id";
        Map<String, Object> params = new HashMap<>();
        params.put("documenttransit_id", documenttransitId);
        params.put("accessrole_id", accessRoleId);
        Integer count = findQueryForObject(Integer.class, sqlText, params);
        return count.intValue() > 0;
    }

    public int allow(Integer accessRoleId, Integer documenttransitId) {
        if (isAllowRole(accessRoleId, documenttransitId)) {
            return 0;
        }
        String sqlText = buildInsertClause("documenttransitrole",
                new String[]{"documenttransitrole_id", "documenttransit_id", "accessrole_id"},
                new String[]{"documenttransitrole_id", "documenttransit_id", "accessrole_id"});
        int id = DatabaseUtils.sequenceNextValue("documenttransitrole_id_gen");
        Map<String, Object> params = new HashMap<>();
        params.put("documenttransitrole_id", id);
        params.put("documenttransit_id", documenttransitId);
        params.put("accessrole_id", accessRoleId);
        executeSQL(sqlText, params);
        return id;
    }

    public void dropAccessToRole(Integer accessRoleId, Integer documenttransitId) {
        String sqlText = "DELETE FROM documenttransitrole " +
                "WHERE accessrole_id=:accessrole_id AND documenttransit_id=:documenttransit_id";
        Map<String, Object> params = new HashMap<>();
        params.put("documenttransit_id", documenttransitId);
        params.put("accessrole_id", accessRoleId);
        executeSQL(sqlText, params);
    }

    private boolean isChildren(Integer parentId, Integer childId) {
        String sqlText = "SELECT COUNT(*) " +
                "FROM documenttransitlink " +
                "WHERE documenttransitchild_id=:child_id AND " +
                "documenttransitparent_id=:parent_id";
        Map<String, Object> params = new HashMap<>();
        params.put("child_id", childId);
        params.put("parent_id", parentId);
        Integer count = findQueryForObject(Integer.class, sqlText, params);
        return count.intValue() > 0;
    }

    public int addChuldren(Integer parentId, Integer childId) {
        if (isChildren(parentId, childId)) {
            return 0;
        }
        String sqlText = buildInsertClause("documenttransitlink",
                new String[]{"documenttransitlink_id", "documenttransitparent_id", "documenttransitchild_id"},
                new String[]{"documenttransitlink_id", "documenttransitparent_id", "documenttransitchild_id"});
        int id = DatabaseUtils.sequenceNextValue("documenttransitlink_id_gen");
        Map<String, Object> params = new HashMap<>();
        params.put("documenttransitlink_id", id);
        params.put("documenttransitparent_id", parentId);
        params.put("documenttransitchild_id", childId);
        executeSQL(sqlText, params);
        return id;
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600700-documenttransit.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documenttransit created");
    }

    @Override
    public void dropForTest() {
        String[] dropTableBefore = new String[]{
                "documenttransitrole",
                "documenttransitlink",
                "documenttransitsetting"
        };
        TableRepository.super.drop(dropTableBefore);

        TableRepository.super.dropForTest();

        String[] dropTableAfter = new String[]{
        };
        TableRepository.super.drop(dropTableAfter);
    }

    @Override
    public int load() {
        DocumentTransit[] data =  new DocumentTransit[] {
                new DocumentTransit(1,1,1,"Счет составлен 1",0,1,1,0,1,1,1,0,0,1,0),
                new DocumentTransit(2,1,2,"Счет выставлен 2",0,1,1,0,1,1,1,0,0,1,0),
                new DocumentTransit(3,1,3,"Счет оплачен   3",0,1,1,0,1,1,1,0,0,1,Color.HSBtoRGB(1.0F, 1.0F, 1.0F)),
                new DocumentTransit(4,2,1,"Заявка принята 1",0,1,1,0,1,1,1,0,0,1, Color.HSBtoRGB(0.1F, 1.0F, 0.3F)),
                new DocumentTransit(5,2,2,"Заявка оформлена 2",0,1,1,0,1,1,1,0,0,1,0),
                new DocumentTransit(6,2,3,"Заявка передана в работу 3",0,1,1,0,1,1,1,0,0,1,0),
                new DocumentTransit(7,2,4,"Заявка закрыта 4",0,1,1,0,1,1,1,0,0,1,Color.HSBtoRGB(0.6F, 0.0F, 0.9F)),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d documenttransit loaded", data.length));
        DatabaseUtils.setSequence("documenttransit_id_gen", data.length+1);
        // разрешения
        allow(1,1);
        allow(2,1);
        //дочерние
        addChuldren(1,2);


        return data.length;
    }

}

