package biz.gelicon.core.components.core.town;

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
public class TownService extends BaseService<Town> {
    private static final Logger logger = LoggerFactory.getLogger(TownService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private TownRepository townRepository;
    @Autowired
    private TownValidator townValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist
    @Value("classpath:/query/town/town-main.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(townRepository, townValidator);
    }

    public List<TownView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<TownView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("town",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(TownView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(TownView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<TownView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("town",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(TownView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(TownView.class)
                .count();

    }

    public List<TownView> getListForFind(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<TownView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("town",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(TownView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(TownView.class)
                .execute();
    }
}

