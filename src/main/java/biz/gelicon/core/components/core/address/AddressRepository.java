package biz.gelicon.core.components.core.address;

import biz.gelicon.core.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository implements TableRepository<Address> {

    private static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // в данной редакции нет таблиц связи Subject и Address
    public Address getSubjectAddress(Integer subjectId) {
        return null;
    }

    // в данной редакции нет таблиц связи Subject и Address
    public Address getSubjectFactAddress(Integer subjectId) {
        return null;
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/700434-address.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("address created");
    }

    @Override
    public int load() {
       return 0;
    }

}

