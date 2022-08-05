package biz.gelicon.core.components.core.subject;

import biz.gelicon.core.components.core.proguser.ProgUserRepository;
import biz.gelicon.core.components.core.subjecttype.SubjectType;
import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import biz.gelicon.core.utils.DateUtils;
import biz.gelicon.core.utils.OrmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SubjectRepository implements TableRepository<Subject> {

    private static final Logger logger = LoggerFactory.getLogger(SubjectRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${gelicon.run-as-test:false}")
    private Boolean runAsTest;

    @Autowired
    private ProgUserRepository progUserRepository;

    public Subject findSubjectLinkWithUser(Integer progUserId) {
        List<Subject> list = findQuery("" +
                "SELECT s.* " +
                "FROM subject s " +
                    "INNER JOIN progusersubject pus ON pus.subject_id=s.subject_id " +
                "WHERE pus.proguser_id=:proguserId", "proguserId", progUserId);
        return list.isEmpty()?null:list.get(0);
    }

    public void setSubjectLinkWithUser(Integer progUserId, Integer subjectId) {
        progUserRepository.setSubjectLinkWithUser(progUserId,subjectId);
    };

    public Subject newFolder(Integer id, Integer clusterId, String name, Integer currentUserId) {
        return new Subject(id,clusterId, SubjectType.FOLDER_SUBJECTTYPE_ID,name,
                DateUtils.getMinDate(),DateUtils.getMaxDate(),"",null,0,currentUserId,new Date(),
                currentUserId,new Date(),null,Subject.SUBJECT_ROOT_ID, -1);
        // dav 01.08.2022 исправил null на -1 для поля parentId
    }

    public Subject newItem(Integer id, Integer clusterId, String name, Integer currentUserId,Integer status) {
        return new Subject(id,clusterId, SubjectType.ITEM_SUBJECTTYPE_ID,name,
                DateUtils.getMinDate(),DateUtils.getMaxDate(),"",null,status,currentUserId,new Date(),
                currentUserId,new Date(),null,Subject.SUBJECT_ROOT_ID, -1);
    }

    public Subject newItem(Integer id, Integer clusterId, String name, String code, Integer currentUserId, Integer status) {
        return new Subject(id,clusterId, SubjectType.ITEM_SUBJECTTYPE_ID,name,
                DateUtils.getMinDate(),DateUtils.getMaxDate(),code,null,status,currentUserId,new Date(),
                currentUserId,new Date(),null,Subject.SUBJECT_ROOT_ID, -1);
    }

    /**
     * Получает список связанных с ОАУ с ид subjMainId объектов(чаще договоров, но ограничений нет)
     * с определенным типом. Отсортирован по дате связи в обратном порядке
     * @param subjMainId
     * @param type
     * @return
     */
    public List<Subject> getDogovors(int subjMainId,int type) {
        return findQuery("" +
                        "SELECT s.* " +
                        "FROM subjectdogovor sd INNER JOIN subject s ON s.subject_id=sd.subject_id2 " +
                        "WHERE sd.subject_id1=:subject_id AND sd.subjectdogovortype_id=:type " +
                        "ORDER BY sd.subjectdogovor_date DESC",
                Map.of("subject_id",subjMainId,
                        "type",subjMainId
                ));
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/600100-subject.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("subject created");
    }

    @Override
    public int insert(Subject subject) {
        // в product работает триггер
        if(runAsTest) {
            // определение paretnId
            if(subject.getParentId()==null) {
                SqlRowSet rs = jdbcTemplate.queryForRowSet(String.format("SELECT ss.subject_id " +
                        "FROM subcluster ss " +
                        "WHERE ss.cluster_id=%d", subject.getClusterId()));
                if(rs.next()) {
                    subject.setParentId(rs.getInt(1));
                }
            }
        }

        int result = TableRepository.super.insert(subject);

        if(result>0 && subject.getSubjectTypeId()==SubjectType.FOLDER_SUBJECTTYPE_ID) {
            String sqlText;

            // добавляем в clusterr
            int clusterId = DatabaseUtils.sequenceNextValue("clusterr_id_gen");
            sqlText = String.format("INSERT INTO clusterr VALUES (%d)",clusterId);
            OrmUtils.logSQL(sqlText);
            jdbcTemplate.update(sqlText);
            // добавляем в subcluster
            sqlText = String.format("INSERT INTO subcluster VALUES (%d,%d)",subject.getSubjectId(),clusterId);
            OrmUtils.logSQL(sqlText);
            jdbcTemplate.update(sqlText);

        }
        return result;
    }

    @Override
    public int delete(Integer id) {
        if(id<0) {
            throw new RuntimeException("Данные объекты не могут быть удалены");
        }
        return TableRepository.super.delete(id);
    }

    @Override
    public int load() {
        if (true) return 0;
        Subject[] data =  new Subject[] {
                newFolder(1,Subject.SUBJECT_ROOT_ID,"Покупатели",1),
                newFolder(2,Subject.SUBJECT_ROOT_ID,"Поставщики",1),
                newFolder(3,Subject.SUBJECT_ROOT_ID,"Прочие дебиторы/кредиторы",1),
                newFolder(4,1,"VIP-Клиенты",1),
                newFolder(5,1,"Постоянные клиенты",1),
                newItem(6,2,"Рогов и Копытов, ООО",1,0),
                newItem(7,2,"Лен и Ого, ООО",1,1),
                newItem(8,3,"Постоянные ребята, ООО",1,0),
        };
        insert(Arrays.asList(data));

        // свяжем с пользователем
        setSubjectLinkWithUser(1,6);

        logger.info(String.format("%d subject loaded", data.length));
        DatabaseUtils.setSequence("subject_id_gen", data.length+1);
        return data.length;
    }

}

