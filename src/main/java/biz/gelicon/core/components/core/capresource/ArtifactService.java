package biz.gelicon.core.components.core.capresource;

import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactTranKinds;
import biz.gelicon.core.dto.SelectDisplayData;
import biz.gelicon.core.components.core.attribute.Attribute;
import biz.gelicon.core.components.core.constant.Constant;
import biz.gelicon.core.components.core.attribute.AttributeService;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.components.core.constant.ConstantService;
import biz.gelicon.core.utils.*;
import biz.gelicon.core.components.core.attribute.AttributeView;
import biz.gelicon.core.components.core.constant.ConstantView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ArtifactService extends BaseService<Artifact> {
    private static final Logger logger = LoggerFactory.getLogger(ArtifactService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private ArtifactRepository artifactRepository;
    @Autowired
    private ArtifactValidator artifactValidator;
    @Autowired
    private ConstantService constantService;
    @Autowired
    private AttributeService attributeService;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    final String mainSQL=String.format(
            "SELECT %s.*," +
                    "rt.resourcetype_name, " +
                    "pu.proguser_name " +
             "FROM capresource %1$s /*FROM_PLACEHOLDER*/ " +
                    "INNER JOIN resourcetype rt ON rt.resourcetype_id= %1$s.resourcetype_id " +
                    "LEFT OUTER JOIN proguser pu ON pu.proguser_id= %1$s.lastproguser_id " +
             "WHERE 1=1 AND (:resourceTypeId = 0 or rt.resourcetype_id=:resourceTypeId) AND " +
                    "(rt.resourcetype_id NOT IN %s OR %1$s.resourcetrantype_id = %d)" +
                        "/*WHERE_PLACEHOLDER*/ " +  //WHERE_PLACEHOLDER если не пуст, всегда добавляет and, поэтому требуется 1=1
             "/*ORDERBY_PLACEHOLDER*/"
            ,ALIAS_MAIN,
            ConvertUtils.arrayToSQLString(ArtifactKinds.getExternalResourceType()),
            ArtifactTranKinds.JAVA.getResourceTranType()
    );


    @PostConstruct
    public void init() {
        init(artifactRepository, artifactValidator);
    }

    public List<ArtifactView> getMainList(GridDataOption gridDataOption) {
        Map<String, String> subsColumns = new HashMap<>();
        subsColumns.put("proguser_name", "pu.proguser_name");

        Map<String, Function<GridDataOption.QuickFilter, String>> processQuickFilter = new HashMap<>();
        processQuickFilter.put("artifactLastmodify",qf->String.format("cast(%s.capresource_lastmodify as date) = cast(:artifactLastmodify as date)",ALIAS_MAIN));
        gridDataOption.setProcessQuickFilter(processQuickFilter);

        return new Query.QueryBuilder<ArtifactView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .putColumnSubstitution("artifactVisibleFlag","artifactActive")
                .sortOrderFirst("rt.resourcetype_id")
                .setFrom(gridDataOption.buildFullTextJoin("capresource",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ArtifactView.class,ALIAS_MAIN,subsColumns))
                .setParams(gridDataOption.buildQueryParams())
                .setParamTransformer("artifactLastmodify",(Function<Long, Date>) arg -> new Date(arg))
                .build(ArtifactView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        Map<String, String> subsColumns = new HashMap<>();
        subsColumns.put("proguser_name", "pu.proguser_name");

        Map<String, Function<GridDataOption.QuickFilter, String>> processQuickFilter = new HashMap<>();
        processQuickFilter.put("artifactLastmodify",qf->String.format("cast(%s.capresource_lastmodify as date) = cast(:artifactLastmodify as date)",ALIAS_MAIN));
        gridDataOption.setProcessQuickFilter(processQuickFilter);

        return new Query.QueryBuilder<ArtifactView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("capresource",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ArtifactView.class,ALIAS_MAIN,subsColumns))
                .setParams(gridDataOption.buildQueryParams())
                .setParamTransformer("artifactLastmodify",(Function<Long, Date>) arg -> new Date(arg))
                .build(ArtifactView.class)
                .count();

    }

    public ArtifactView getOne(Integer id) {
        return new Query.QueryBuilder<ArtifactView>(mainSQL)
                .setPredicate(ALIAS_MAIN+".capresource_id=:capresourceId")
                .setParam("resourceTypeId",0)
                .build(ArtifactView.class)
                .executeOne("capresourceId", id);
    }

    public void loadForConstant(ArtifactDTO dto) {
        if(dto.getResourceTypeId() == ArtifactKinds.CONST.getResourceType()) {
            ConstantView cnst = constantService.getConstant(dto.getArtifactId());
            if(cnst!=null) {
                dto.setConstantGroupId(cnst.getConstantgroupId());
                dto.setConstantTypeId(cnst.getConstantType());
                dto.setConstantGroupName(cnst.getConstantgroupName());
            }

        }
    }

    public void loadForAttribute(ArtifactDTO dto) {
        if(dto.getResourceTypeId() == ArtifactKinds.ATTRIBUTE.getResourceType()) {
            AttributeView attr = attributeService.getAttribute(dto.getArtifactId());
            if(attr!=null) {
                dto.setAttributeGroupId(attr.getAttributegroupId());
                dto.setAttributegroupName(attr.getAttributegroupName());

                dto.setAttributeCapClassTypeId(attr.getCapclasstypeId());
                dto.setAttributeCapclasstypeName(attr.getCapclasstypeName());

                dto.setAttributeTypeId(attr.getCapcodeId());
                dto.setAttributeTypeName(attr.getCapcodeName());

                dto.setAttributeHistoricityFlag(attr.getAttributeHistoricity());

                dto.setAttributeSubject(new SelectDisplayData(attr.getSubjectId(),attr.getSubjectName()));
            }

        }
    }

    public void saveConstant(ArtifactDTO dto, Artifact savedArtifact) {

        if(dto.getResourceTypeId() == ArtifactKinds.CONST.getResourceType()) {
            Constant cnst = constantService.findById(dto.getArtifactId());
            if(cnst!=null) {
                constantFromDTO(dto, cnst);
                constantService.edit(cnst);
            } else {
                cnst = new Constant(savedArtifact.getArtifactId());
                constantFromDTO(dto, cnst);
                constantService.add(cnst);
            }
        }
    }


    public void saveAttribute(ArtifactDTO dto, Artifact savedArtifact) {
        if(dto.getResourceTypeId() == ArtifactKinds.ATTRIBUTE.getResourceType()) {
            Attribute attr = attributeService.findById(dto.getArtifactId());
            if(attr!=null) {
                attrFromDTO(dto, attr);
                attributeService.edit(attr);
            } else {
                attr = new Attribute(savedArtifact.getArtifactId());
                attrFromDTO(dto, attr);
                attributeService.add(attr);
            }
        }
    }

    private void constantFromDTO(ArtifactDTO dto, Constant cnst) {
        cnst.setConstantgroupId(dto.getConstantGroupId());
        cnst.setConstantType(dto.getConstantTypeId());
    }

    private void attrFromDTO(ArtifactDTO dto, Attribute attr) {
        attr.setAttributegroupId(dto.getAttributeGroupId());
        attr.setCapclasstypeId(dto.getAttributeCapClassTypeId());
        attr.setCapcodeId(dto.getAttributeTypeId());
        attr.setAttributeHistoricity(dto.getAttributeHistoricityFlag());
        SelectDisplayData<Integer> subj = dto.getAttributeSubject();
        attr.setSubjectId(subj!=null?subj.getValue():null);
    }

    public void saveApplicationBinding(ArtifactDTO dto, Artifact artifact) {
        // если не ПФ и нумератор
        if(UsefulUtils.indexOf(ArtifactKinds.getExternalResourceType(),artifact.getResourceTypeId())<0) {
            artifactRepository.dropApplicationBinding(artifact.getArtifactId());
            artifactRepository.insertApplicationBinding(artifact.getArtifactId(),dto.getArtifactApplicationIds());
        }
    }

    public void saveDocumentBinding(ArtifactDTO dto, Artifact artifact) {
        // если не ПФ и нумератор
        if(UsefulUtils.indexOf(ArtifactKinds.getExternalResourceType(),artifact.getResourceTypeId())<0) {
            artifactRepository.dropDocumentBinding(artifact.getArtifactId());
            artifactRepository.insertDocumentBinding(artifact.getArtifactId(),dto.getArtifactDocumentIds());
        }
    }

}

