package biz.gelicon.core.components.erp.todo.documentrealtransit;

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

@Repository
public class DocumentRealTransitRepository implements TableRepository<DocumentRealTransit> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentRealTransitRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600800-documentrealtransit.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("documentrealtransit created");
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
        DocumentRealTransit[] data =  new DocumentRealTransit[] {
                new DocumentRealTransit(1, 1, 4, 1, new Date(), "Заявка №1 - Принята 1", new Date(), 0),
                new DocumentRealTransit(2, 1, 5, 1, new Date(), "Заявка №1 - Оформлена 2", new Date(), 1),
                new DocumentRealTransit(3, 1, 6, 1, new Date(), "Заявка №1 - Передана в работу 3", new Date(), 0),
                new DocumentRealTransit(4, 3, 1, 1, new Date(), "Счет на оплату №1 - Счет составлен 1", new Date(), 0),
                new DocumentRealTransit(5, 3, 2, 1, new Date(), "Счет на оплату №1 - Счет выставлен 2", new Date(), 0),
                new DocumentRealTransit(6, 3, 3, 1, new Date(), "Счет на оплату №1 - Счет оплачен 3", new Date(), 0),
                new DocumentRealTransit(7, 101, 4, 1, new Date(), "Заявка принята", new Date(), 0),
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d documentrealtransit loaded", data.length));
        DatabaseUtils.setSequence("documentrealtransit_id_gen", data.length+1);
        return data.length;
    }

}

