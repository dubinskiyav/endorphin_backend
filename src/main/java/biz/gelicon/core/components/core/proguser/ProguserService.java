package biz.gelicon.core.components.core.proguser;

import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.proguserchannel.ProguserChannel;
import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
import biz.gelicon.core.components.core.proguserchannel.ProguserChannelRepository;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProguserService extends BaseService<Proguser> {
    private static final Logger logger = LoggerFactory.getLogger(ProguserService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private ProgUserRepository proguserRepository;
    @Autowired
    private ProguserValidator proguserValidator;
    @Autowired
    private ProguserChannelRepository proguserChannelRepository;
    @Autowired
    private AccessRoleRepository accessRoleRepository;

    @Value("classpath:/query/proguser/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(proguserRepository, proguserValidator);
    }

    public List<ProguserView> getMainList(GridDataOption gridDataOption) {
        Map<String, String> subsColumns = new HashMap<>();
        subsColumns.put("proguser_status_display","CC.capcode_name");
        subsColumns.put("proguserchannel_address","PUC.proguserchannel_address");

        boolean roleFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "roleId".equals(nf.getName()) && !nf.getValue().equals(-1));
        boolean statusFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "status".equals(nf.getName()) && !nf.getValue().equals(-1));

        return new Query.QueryBuilder<ProguserView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("proguser",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ProguserView.class,ALIAS_MAIN,subsColumns))
                .injectSQLIf(roleFound, "/*ROLE_FILTER*/","AND PUR.accessrole_id = :roleId")
                .injectSQLIf(statusFound, "/*STATUS_FILTER*/","AND m0.proguser_status_id = :status")
                .setParams(gridDataOption.buildQueryParams())
                .build(ProguserView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        Map<String, String> subsColumns = new HashMap<>();
        subsColumns.put("proguser_status_display","CC.capcode_name");
        subsColumns.put("proguserchannel_address","PUC.proguserchannel_address");

        boolean roleFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "roleId".equals(nf.getName()) && !nf.getValue().equals(-1));
        boolean statusFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "status".equals(nf.getName()) && !nf.getValue().equals(-1));

        return new Query.QueryBuilder<ProguserView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("proguser",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ProguserView.class,ALIAS_MAIN,subsColumns))
                .setParams(gridDataOption.buildQueryParams())
                .injectSQLIf(roleFound, "/*ROLE_FILTER*/","AND PUR.accessrole_id = :roleId")
                .injectSQLIf(statusFound, "/*STATUS_FILTER*/","AND m0.proguser_status_id = :status")
                .build(ProguserView.class)
                .count();

    }


    public ProguserView getOne(Integer id) {
        return new Query.QueryBuilder<ProguserView>(mainSQL)
                .setPredicate(ALIAS_MAIN+".proguser_id=:proguserId")
                .build(ProguserView.class)
                .executeOne("proguserId", id);
    }

    public ProguserChannel getlEmail(Integer proguserId) {
        return proguserChannelRepository.getlEmail(proguserId);
    }

    public void saveEmail(Integer proguserId, String address) {
        proguserChannelRepository.saveEmail(proguserId,address);
    }


    public List<AccessRole> getRoleList(Integer proguserId) {
        List<AccessRole> list = accessRoleRepository.findByUser(proguserId);
        return list.stream()
                .sorted((r1,r2)->r1.getAccessRoleName().compareTo(r2.getAccessRoleName()))
                .collect(Collectors.toList());
    }

    public void saveRoles(Integer proguserId, List<Integer> accessRoleIds) {
        // удалили все связи ролей с пользователем
        accessRoleRepository.unbindProgUser(proguserId);
        // создаем новые связи ролей с пользователем
        accessRoleIds.stream()
                .forEach(roleId->accessRoleRepository.bindWithProgUser(roleId,proguserId));

    }

    public List<ProguserView> getListForFind(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ProguserView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("proguser",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ProguserView.class,ALIAS_MAIN,null))
                .setParams(gridDataOption.buildQueryParams())
                .build(ProguserView.class)
                .execute();

    }

    public UserDetails buildUserDetail(Integer proguserId) {
        Proguser pu = proguserRepository.findById(proguserId);
        return new UserDetailsImpl(pu,accessRoleRepository.findByUser(proguserId));
    }
}

