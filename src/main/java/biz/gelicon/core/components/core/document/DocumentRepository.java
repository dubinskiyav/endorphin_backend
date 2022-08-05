package biz.gelicon.core.components.core.document;

import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.TwoTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;

@Repository
public class DocumentRepository implements TableRepository<Document> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer findUniqueType(Integer docid) {
        return findQueryForObject(Integer.class,
                "SELECT utd.uniquetype_id FROM uniquetypedocument utd " +
                        "WHERE utd.document_id=:document_id",
                "document_id", docid);
    }

    /**
     * Возвращает document_id из таблицы document для document_shortname
     * @param shortname document_shortname
     * @return document_id
     */
    public Integer getDocumentId(String shortname) {
        if (shortname == null) {
            return null;
        }
        return findQueryForObject(Integer.class, ""
                        + " SELECT document_id "
                        + " FROM   document "
                        + " WHERE  document_shortname = :document_shortname ",
                "document_shortname", shortname);
    }

    /**
     * Возвращает кортеж с папками в которых размещаются DocumentReal (по умолчанию) для типа документа
     * В поле кортежа: a -  clusterdocumentreal_id, b - documentreal_id
     * @param docid
     * @return
     */
    public TwoTuple<Integer,Integer> getDefaultCluster(Integer docid) {
        Map<String, Object> data = findQueryForOneMap("" +
                "SELECT dc.clusterdocumentreal_id, drc.documentreal_id " +
                "FROM documentcluster dc " +
                "INNER JOIN documentrealcluster drc ON drc.clusterdocumentreal_id=dc.clusterdocumentreal_id " +
                "WHERE dc.document_id=:documentId", "documentId", docid);
        if(data == null) {
            // это корень всех документов
            return new TwoTuple<>(1,1);
        }
        return new TwoTuple<>((Integer)data.get("clusterdocumentreal_id"),(Integer)data.get("documentreal_id"));

    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400350-document.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("document created");
    }

    @Override
    public int load() {
        Document[] data = new Document[]{
                new Document(1, "Счет на оплату", null, 0, 0),
                new Document(2, "Заявка", null, 0, 0),
                new Document(3, "Проводка", null, 0, 0),
                new Document(Document.DOCUUMENT_FOLDER, "Уровень", null, 0, 0),
                new Document(Document.DOCUUMENT_FOR_TEST, "Документ для тестов", null, 0, 0),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d document loaded", data.length));
        DatabaseUtils.setSequence("document_id_gen", data.length + 1);
        return data.length;
    }

}

