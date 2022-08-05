package biz.gelicon.core.components.core.subject;

import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class SubjectService extends BaseService<Subject> {
    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectValidator subjectValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/subject/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(subjectRepository, subjectValidator);
    }

    public List<SubjectView> getMainList(GridDataOption gridDataOption, Date onDate) {
        gridDataOption.addFilter("date",onDate);
        return new Query.QueryBuilder<SubjectView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("subject",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(SubjectView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(SubjectView.class)
                .execute();
    }

    public List<SubjectView> getListForFind(GridDataOption gridDataOption) {
        gridDataOption.addFilter("date",new Date());
        return new Query.QueryBuilder<SubjectView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("subject",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(SubjectView.class,ALIAS_MAIN,null))
                .setParams(gridDataOption.buildQueryParams())
                .build(SubjectView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<SubjectView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("subject",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(SubjectView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(SubjectView.class)
                .count();

    }


    public SubjectView getOne(Integer id) {
        return new Query.QueryBuilder<SubjectView>(mainSQL)
                .setPredicate(ALIAS_MAIN+".subject_id=:subjectId")
                .build(SubjectView.class)
                .executeOne("subjectId", id);
    }

    public List<SubjectView> getAllClusters(Date onDate) {
        if(onDate==null) onDate = new Date();
        return new Query.QueryBuilder<SubjectView>(
                "SELECT s.*, ss.cluster_id folder_id " +
                     "FROM subject s " +
                        "INNER JOIN subcluster ss ON ss.subject_id=s.subject_id " +
                     "WHERE s.subjecttype_id=1 and " +
                        ":showdate BETWEEN s.subject_datebeg AND s.subject_dateend " +
                     "ORDER BY s.parent_id, s.subject_name")
                .setParam("showdate",onDate)
                .build(SubjectView.class)
                .execute();
    }
}

