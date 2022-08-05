package biz.gelicon.core.components.core.controlobject;

import biz.gelicon.core.security.Permission;
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
import java.util.Map;

@Service
public class ControlObjectService extends BaseService<ControlObject> {
    private static final Logger logger = LoggerFactory.getLogger(ControlObjectService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private ControlObjectRepository controlObjectRepository;
    @Autowired
    private ControlObjectValidator controlObjectValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/controlobject/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(controlObjectRepository, controlObjectValidator);
    }

    public List<ControlObjectView> getMainList(GridDataOption gridDataOption) {
        Map<String, Object> params = gridDataOption.buildQueryParams();
        params.put("sqlactionId", Permission.EXECUTE.ordinal());
        return new Query.QueryBuilder<ControlObjectView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .putColumnSubstitution("controlObjectRoleAccessFlag","controlobjectroleId")
                .setFrom(gridDataOption.buildFullTextJoin("controlobject",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ControlObjectView.class,ALIAS_MAIN))
                .setParams(params)
                .build(ControlObjectView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        Map<String, Object> params = gridDataOption.buildQueryParams();
        params.put("sqlactionId", Permission.EXECUTE.ordinal());
        return new Query.QueryBuilder<ControlObjectView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("controlobject",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ControlObjectView.class,ALIAS_MAIN))
                .setParams(params)
                .build(ControlObjectView.class)
                .count();

    }

    public ControlObjectView getOne(Integer id) {
        return new Query.QueryBuilder<ControlObjectView>(
                String.format(
                        "SELECT * " +
                        "FROM controlobject %s " +
                        "WHERE 1=1 /*WHERE_PLACEHOLDER*/",ALIAS_MAIN))
                .setPredicate(ALIAS_MAIN+".controlobject_id=:controlobjectId")
                .build(ControlObjectView.class)
                .executeOne("controlobjectId", id);
    }

    public void allow(Integer accessroleId, Integer[] controlObjectIds) {
        for (Integer coId :controlObjectIds) {
            controlObjectRepository.allow(accessroleId,coId,Permission.EXECUTE);
        }
    }

    public void deny(Integer accessroleId, Integer[] controlObjectIds) {
        for (Integer coId :controlObjectIds) {
            controlObjectRepository.deny(accessroleId,coId,Permission.EXECUTE);
        }
    }
}

