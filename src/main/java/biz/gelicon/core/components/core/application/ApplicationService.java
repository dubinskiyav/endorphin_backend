package biz.gelicon.core.components.core.application;

import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import biz.gelicon.core.components.core.applicationrole.ApplicationRoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService extends BaseService<Application> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationValidator applicationValidator;
    @Autowired
    private AuthenticationBean authentication;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/application/mainSQLForRole.sql")
    Resource mainSQLForRole;


    // главный запрос для выбра всех модулей. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/application/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(applicationRepository, applicationValidator);
    }

    public List<ApplicationRoleView> getMainListForRole(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ApplicationRoleView>(mainSQLForRole)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .putColumnSubstitution("applicationRoleAccessFlag","applicationroleId")
                .setFrom(gridDataOption.buildFullTextJoin("application",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ApplicationView.class,ALIAS_MAIN))
                .setParam("type", Application.TYPE_GELICON_CORE_APP)
                .setParams(gridDataOption.buildQueryParams())
                .build(ApplicationRoleView.class)
                .execute();
    }

    public int getMainCountForRole(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ApplicationRoleView>(mainSQLForRole)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("application",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ApplicationView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .setParam("type", Application.TYPE_GELICON_CORE_APP)
                .build(ApplicationRoleView.class)
                .count();

    }

    public ApplicationRoleView getOneForRole(Integer id) {
        return new Query.QueryBuilder<ApplicationRoleView>(mainSQLForRole)
                .setPredicate(ALIAS_MAIN+".application_id=:applicationId")
                .setParam("type", Application.TYPE_GELICON_CORE_APP)
                .build(ApplicationRoleView.class)
                .executeOne("applicationId", id);
    }

    public List<ApplicationView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ApplicationView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("application",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ApplicationView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .setParam("type", Application.TYPE_GELICON_CORE_APP)
                .build(ApplicationView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ApplicationView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("application",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ApplicationView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .setParam("type", Application.TYPE_GELICON_CORE_APP)
                .build(ApplicationView.class)
                .count();

    }


    public void allow(Integer accessRoleId, Integer[] appIds) {
        for (Integer appId :appIds) {
            applicationRepository.allow(accessRoleId,appId);
        }
    }

    public void deny(Integer accessRoleId, Integer[] appIds) {
        for (Integer appId :appIds) {
            applicationRepository.deny(accessRoleId,appId);
        }
    }

    public List<ApplicationView> getAccessList(Integer proguserId) {
        List<Application> list;
        // текущий пользователь
        UserDetailsImpl ud = (UserDetailsImpl) authentication.getCurrentUserDetails();
        // для sysdba все модули доступны
        if (ud.containsSystemRole()) {
            list = applicationRepository.findQuery("" +
                    "SELECT a.* " +
                    "FROM application a " +
                    "WHERE a.application_type= " + Application.TYPE_GELICON_CORE_APP);
        } else {
            list = applicationRepository.findQuery("" +
                    "SELECT DISTINCT a.* " +
                    "FROM application a " +
                    "INNER JOIN applicationrole ar ON ar.application_id=a.application_id " +
                    "INNER JOIN proguserrole pur ON pur.accessrole_id=ar.accessrole_id " +
                    "WHERE a.application_type= " + Application.TYPE_GELICON_CORE_APP+
                    " AND (pur.proguser_id=:proguser_id)", "proguser_id", proguserId);
        }
        return list.stream()
                .map(ApplicationView::new)
                .collect(Collectors.toList());
    }


}

