package biz.gelicon.core.components.core.company;

import biz.gelicon.core.components.core.subject.Subject;
import biz.gelicon.core.components.core.subject.SubjectRepository;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.config.EditionTag;
import biz.gelicon.core.components.core.companycode.CompanyCodeDTO;
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
public class CompanyService extends BaseService<Company> {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyValidator companyValidator;
    @Autowired
    private SubjectRepository subjectRepository;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/company/mainSQL-erp.sql")
    Resource mainSQL_ERP;
    @Value("classpath:/query/company/mainSQL-gu.sql")
    Resource mainSQL_GU;
    // главный запрос для получения одной записи. используется в get
    @Value("classpath:/query/company/mainSQLForOne-erp.sql")
    Resource mainSQLForOne_ERP;
    @Value("classpath:/query/company/mainSQLForOne-gu.sql")
    Resource mainSQLForOne_GU;

    // инжектится в основной запрос при фильтре с атрибутом
    final String filterAttrSQL=String.format(
            "INNER JOIN subjectattributevalue sav ON sav.subject_id=%s.company_id " +
                    "AND sav.attribute_id=:attributeId"
        ,ALIAS_MAIN);


    @PostConstruct
    public void init() {
        init(companyRepository, companyValidator);
    }

    public List<CompanyView> getMainList(GridDataOption gridDataOption) {
        Integer attributeId = (Integer) gridDataOption.getFilters().get("attributeId");
        Resource mainSQL = Config.CURRENT_EDITION_TAG == EditionTag.GELICON_ERP ? mainSQL_ERP : mainSQL_GU;
        return new Query.QueryBuilder<CompanyView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("company",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(CompanyView.class,ALIAS_MAIN))
                .injectSQLIf(attributeId!=null,"/*ATTR_FILTER_PLACEHOLDER*/",filterAttrSQL)
                .setParams(gridDataOption.buildQueryParams())
                .build(CompanyView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        Integer attributeId = (Integer) gridDataOption.getFilters().get("attributeId");
        Resource mainSQL = Config.CURRENT_EDITION_TAG == EditionTag.GELICON_ERP ? mainSQL_ERP : mainSQL_GU;
        return new Query.QueryBuilder<CompanyView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("company",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(CompanyView.class,ALIAS_MAIN))
                .injectSQLIf(attributeId!=null,"/*ATTR_FILTER_PLACEHOLDER*/",filterAttrSQL)
                .setParams(gridDataOption.buildQueryParams())
                .build(CompanyView.class)
                .count();

    }

    public CompanyView getOne(Integer id) {
        Resource mainSQLForOne = Config.CURRENT_EDITION_TAG == EditionTag.GELICON_ERP ? mainSQLForOne_GU : mainSQLForOne_GU;
        return new Query.QueryBuilder<CompanyView>(mainSQLForOne)
                .setPredicate(ALIAS_MAIN+".company_id=:companyId")
                .build(CompanyView.class)
                .executeOne("companyId", id);
    }

    public List<CompanyView> getListForFind(GridDataOption gridDataOption) {
        Integer attributeId = (Integer) gridDataOption.getFilters().get("attributeId");
        Resource mainSQL = Config.CURRENT_EDITION_TAG == EditionTag.GELICON_ERP ? mainSQL_ERP : mainSQL_GU;
        return new Query.QueryBuilder<CompanyView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("company",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(CompanyView.class,ALIAS_MAIN,null))
                .injectSQLIf(attributeId!=null,"/*ATTR_FILTER_PLACEHOLDER*/",filterAttrSQL)
                .setParams(gridDataOption.buildQueryParams())
                .build(CompanyView.class)
                .execute();
    }

    public void saveCodeGU(Integer companyId, String companyInn, String companyKpp){
        CompanyCodeDTO dto = new CompanyCodeDTO();
        dto.setCompanyId(companyId);
        dto.setCapClassId(Company.CAPCODE_INN);
        dto.setCode(companyInn);
        if(companyInn != null) {
            if (!companyRepository.existCompanyCode(dto)) {
                companyRepository.insertCompanyCode(dto);
            } else {
                companyRepository.updateCompanyCode(dto);
            }
        }else{
            if (companyRepository.existCompanyCode(dto)) {
                companyRepository.deleteCompanyCode(dto.getCompanyId(), dto.getCapClassId());
            }
        }
        dto.setCapClassId(Company.CAPCODE_KPP);
        dto.setCode(companyKpp);
        if(companyKpp != null) {
            if (!companyRepository.existCompanyCode(dto)) {
                companyRepository.insertCompanyCode(dto);
            } else {
                companyRepository.updateCompanyCode(dto);
            }
        }else{
            if (companyRepository.existCompanyCode(dto)) {
                companyRepository.deleteCompanyCode(dto.getCompanyId(), dto.getCapClassId());
            }
        }
    }

    public void saveCodeERP(Integer companyId, String companyKpp){
        CompanyCodeDTO dto = new CompanyCodeDTO();
        dto.setCompanyId(companyId);
        dto.setCapClassId(Company.CAPCODE_KPP);
        dto.setCode(companyKpp);
        if(companyKpp != null) {
            if (!companyRepository.existCompanyCode(dto)) {
                companyRepository.insertCompanyCode(dto);
            } else {
                companyRepository.updateCompanyCode(dto);
            }
        }else{
            if (companyRepository.existCompanyCode(dto)) {
                companyRepository.deleteCompanyCode(dto.getCompanyId(), dto.getCapClassId());
            }
        }
    }

    public void deleteCompanyCode(int[] companyIds){
        for(int id: companyIds){
            companyRepository.deleteCompanyCode(id, null);
        }
    }

    public void deleteSubject(int[] companyIds){
        for(int id: companyIds){
            subjectRepository.delete(id);
        }
    }

    public Subject getSubject(Integer companyId){
        return subjectRepository.findById(companyId);
    }

}

