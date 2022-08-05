package biz.gelicon.core.components.core.capclass;

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
import java.util.List;

@Service
public class CapClassService extends BaseService<CapClass> {
    private static final Logger logger = LoggerFactory.getLogger(CapClassService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private CapClassRepository capClassRepository;
    @Autowired
    private CapClassValidator capClassValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/capclass/mainSQL.sql")
    Resource mainSQL;

    @Value("classpath:/query/capclass/mainSQLForOne.sql")
    Resource mainSQLForOne;


    @PostConstruct
    public void init() {
        init(capClassRepository, capClassValidator);
    }

    public List<CapClassView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<CapClassView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("capclass",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(CapClassView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(CapClassView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<CapClassView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("capclass",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(CapClassView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(CapClassView.class)
                .count();

    }


    public CapClassView getOne(Integer id) {
        return new Query.QueryBuilder<CapClassView>(mainSQLForOne)
                .setPredicate(ALIAS_MAIN+".capclass_id=:capclassId")
                .build(CapClassView.class)
                .executeOne("capclassId", id);
    }
}

