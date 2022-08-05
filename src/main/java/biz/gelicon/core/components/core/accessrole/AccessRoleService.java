package biz.gelicon.core.components.core.accessrole;

import biz.gelicon.core.security.AuthenticationBean;
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
public class AccessRoleService extends BaseService<AccessRole> {
    private static final Logger logger = LoggerFactory.getLogger(AccessRoleService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private AccessRoleRepository accessRoleRepository;
    @Autowired
    private AccessRoleValidator accessRoleValidator;
    @Autowired
    private AuthenticationBean authenticationBean;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/accessrole/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(accessRoleRepository, accessRoleValidator);
    }

    public List<AccessRoleView> getMainList(GridDataOption gridDataOption) {
        boolean onlyVisibleFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "onlyVisible".equals(nf.getName()));
        return new Query.QueryBuilder<AccessRoleView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("accessrole",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(AccessRoleView.class,ALIAS_MAIN))
                .injectSQLIf(onlyVisibleFound,"/*ONLY_VISIBLE_FILTER*/","AND "+ALIAS_MAIN + ".accessrole_visible<>0")
                .setParams(gridDataOption.buildQueryParams())
                .build(AccessRoleView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        boolean onlyVisibleFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "onlyVisible".equals(nf.getName()));
        return new Query.QueryBuilder<AccessRoleView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("accessrole",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(AccessRoleView.class,ALIAS_MAIN))
                .injectSQLIf(onlyVisibleFound,"/*ONLY_VISIBLE_FILTER*/","AND "+ALIAS_MAIN + ".accessrole_visible<>0")
                .setParams(gridDataOption.buildQueryParams())
                .build(AccessRoleView.class)
                .count();

    }


    public AccessRoleView getOne(Integer id) {
        return new Query.QueryBuilder<AccessRoleView>(mainSQL)
                .setPredicate(ALIAS_MAIN+".accessrole_id=:accessroleId")
                .build(AccessRoleView.class)
                .executeOne("accessroleId", id);
    }

    public boolean hasRole(String roleName) {
        List<AccessRole> accessRole = accessRoleRepository.findWhere("accessrole_name = :accessRoleName", "accessRoleName", roleName);
        if (accessRole.isEmpty()) {
            return false;
        } else {
            Integer accessRoleId = accessRole.get(0).getAccessRoleId();
            return authenticationBean.getCurrentUserDetails().getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("R#" + accessRoleId));
        }
    }
}

