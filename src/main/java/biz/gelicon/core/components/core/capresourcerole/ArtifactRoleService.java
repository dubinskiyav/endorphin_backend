package biz.gelicon.core.components.core.capresourcerole;

import biz.gelicon.core.artifacts.ArtifactTranKinds;
import biz.gelicon.core.components.core.capresource.Artifact;
import biz.gelicon.core.components.core.capresource.ArtifactRepository;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import biz.gelicon.core.components.core.capresource.ArtifactValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ArtifactRoleService extends BaseService<Artifact> {
    private static final Logger logger = LoggerFactory.getLogger(ArtifactRoleService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private ArtifactRepository artifactRepository;
    @Autowired
    private ArtifactValidator artifactValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/artifactrole/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(artifactRepository, artifactValidator);
    }

    public List<ArtifactRoleView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ArtifactRoleView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .putColumnSubstitution("roleAllowFlag","allowArtifactRoleId")
                .setFrom(gridDataOption.buildFullTextJoin("capresource","ft_capresourceaccess", ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ArtifactRoleView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .setParam("type", ArtifactTranKinds.JAVA.getResourceTranType())
                .build(ArtifactRoleView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ArtifactRoleView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("capresource",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ArtifactRoleView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .setParam("type", ArtifactTranKinds.JAVA.getResourceTranType())
                .build(ArtifactRoleView.class)
                .count();

    }


    public void restrict(Integer accessRoleId, Integer[] artifactIds) {
        for (Integer artiId :artifactIds) {
            artifactRepository.restrict(accessRoleId,artiId);
        }
    }

    public void allow(Integer accessRoleId, Integer[] artifactIds) {
        for (Integer artiId :artifactIds) {
            artifactRepository.allow(accessRoleId,artiId);
        }
    }

    public void dropLink(Integer accessRoleId, Integer[] artifactIds) {
        for (Integer artiId :artifactIds) {
            artifactRepository.dropLink(accessRoleId,artiId);
        }

    }
}

