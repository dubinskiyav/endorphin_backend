package biz.gelicon.core.components.erp.todo.sgood;

import biz.gelicon.core.config.Config;
import biz.gelicon.core.config.EditionTag;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.ProcessNamedFilter;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SgoodService extends BaseService<Sgood> {
    private static final Logger logger = LoggerFactory.getLogger(SgoodService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private SgoodRepository sgoodRepository;
    @Autowired
    private SgoodValidator sgoodValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    final String mainSQL=
            "SELECT m0.*," +
                    "p.sgood_name parent_name, " +
                    "e.edizm_notation " +
                    "/*ATTR_PLACEHOLDER*/ " +
                    "/*PRICE_PLACEHOLDER*/ " +
                    "/*WEIGHT_PLACEHOLDER*/ " +
                    "/*PACKQUANTITY_PLACEHOLDER*/ " +
            "FROM sgood m0 " +
                    "INNER JOIN sgood p ON p.sgood_id=m0.parent_id " +
                    "LEFT OUTER JOIN sgoodedizm se ON se.sgood_id=m0.sgood_id " +
                    "LEFT OUTER JOIN edizm e ON e.edizm_id=se.edizm_id " +
                    "/*FROM_PLACEHOLDER*/ " +
            "WHERE m0.sgoodtype_id>1 and :date BETWEEN m0.sgood_datebeg AND m0.sgood_dateend /*WHERE_PLACEHOLDER*/ " +
            "/*ORDERBY_PLACEHOLDER*/";


    final String attrSQLPattern=",("+
            "select av1.sgoodattributevalue_value "+
            "from sgoodattributevalue av1 "+
            "where av1.sgood_id = m0.sgood_id and " +
            "av1.attribute_id = :attr%s " +
    ") as attr_%1$s ";

    final String weightSQLPattern=",("+
            "select sp1.sgoodparam_weight "+
            "from sgoodparam sp1 " +
            "where sp1.sgood_id = m0.sgood_id" +
    ") as sgood_weight_netto ";

    final String weightGrossSQLPattern=",("+
            "select sp1.sgoodparam_grossweight "+
            "from sgoodparam sp1 " +
            "where sp1.sgood_id = m0.sgood_id" +
            ") as sgood_weight_gross ";

    final String priceSQLPattern = ",("+
            "select p1.pricesgoodsubject_value "+
            "from pricesgoodsubject p1 "+
            "where p1.sgood_id = m0.sgood_id and "+
                "p1.subject_id = :subjectId and "+
                "p1.pricecluster_id = :priceType%s and "+
                "p1.pricesgoodsubject_date = ( "+
                    "select max(p2.pricesgoodsubject_date) "+
                    "from pricesgoodsubject p2 "+
                    "where p2.sgood_id =m0.sgood_id and "+
                        "p2.subject_id = :subjectId and "+
                        "p2.pricecluster_id = :priceType%1$s and "+
                        "p2.pricesgoodsubject_date < :dateForPrice "+
                ") "+
            ") as sgood_price_%1$s";

    final String packageSQLPattern = ",("+
            "select pg1.packagegood_quantity "+
            "from packagegood pg1 "+
            "where pg1.sgood_id = m0.sgood_id and " +
                "pg1.packagegood_type = :packageType " +
     ") as package_quantity ";

    @PostConstruct
    public void init() {
        init(sgoodRepository, sgoodValidator);
    }

    public List<SgoodView> getMainList(GridDataOption gridDataOption, Date onDate, ProcessNamedFilter fprocessor) {
        String attrFilters = buildAttrFields(gridDataOption);
        String priceFields = buildPriceFields(gridDataOption);
        String weightField = buildWeightField(gridDataOption);
        String packQuantityField = buildPackageField(gridDataOption);
        gridDataOption.addFilter("date",onDate);
        // нужно преобразовать dateForPrice в дату
        Object dateForPrice = gridDataOption.getFilters().get("dateForPrice");
        if(dateForPrice!=null) {
            long times = getTimesForPrice(dateForPrice);
            gridDataOption.setFilterValue("dateForPrice",new Date(times));
        }
        return new Query.QueryBuilder<SgoodView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("sgood",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(SgoodView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .injectSQLIf(!attrFilters.isEmpty(),"/*ATTR_PLACEHOLDER*/",attrFilters)
                .injectSQLIf(!priceFields.isEmpty(),"/*PRICE_PLACEHOLDER*/",priceFields)
                .injectSQLIf(!weightField.isEmpty(),"/*WEIGHT_PLACEHOLDER*/",weightField)
                .injectSQLIf(!packQuantityField.isEmpty(),"/*PACKQUANTITY_PLACEHOLDER*/",packQuantityField)
                .build(SgoodView.class)
                .execute();
    }

    public List<SgoodView> getListForFind(GridDataOption gridDataOption) {
        String attrFields = buildAttrFields(gridDataOption);
        String priceFields = buildPriceFields(gridDataOption);
        String weightField = buildWeightField(gridDataOption);
        String packQuantityField = buildPackageField(gridDataOption);
        gridDataOption.addFilter("date",new Date());
        // нужно преобразовать dateForPrice в дату
        Object dateForPrice = gridDataOption.getFilters().get("dateForPrice");
        if(dateForPrice!=null) {
            long times = getTimesForPrice(dateForPrice);
            gridDataOption.setFilterValue("dateForPrice",new Date(times));
        }
        return new Query.QueryBuilder<SgoodView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("sgood",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(SgoodView.class,ALIAS_MAIN,null))
                .setParams(gridDataOption.buildQueryParams())
                .injectSQLIf(!attrFields.isEmpty(),"/*ATTR_PLACEHOLDER*/",attrFields)
                .injectSQLIf(!priceFields.isEmpty(),"/*PRICE_PLACEHOLDER*/",priceFields)
                .injectSQLIf(!weightField.isEmpty(),"/*WEIGHT_PLACEHOLDER*/",weightField)
                .injectSQLIf(!packQuantityField.isEmpty(),"/*PACKQUANTITY_PLACEHOLDER*/",packQuantityField)
                .build(SgoodView.class)
                .execute();
    }

    private long getTimesForPrice(Object dateForPrice) {
        long times = 0;
        if(dateForPrice instanceof Integer) {
            times = ((Integer)dateForPrice).longValue();
        } else
        if(dateForPrice instanceof Long) {
            times = ((Long)dateForPrice).longValue();
        } else
        if(dateForPrice instanceof Date) {
            times = ((Date) dateForPrice).getTime();
        } else {
            throw new RuntimeException("Неверный тип параметра dateForPrice");
        }
        return times;
    }

    public int getMainCount(GridDataOption gridDataOption, ProcessNamedFilter fprocessor) {
        return new Query.QueryBuilder<SgoodView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("sgood",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(SgoodView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(SgoodView.class)
                .count();

    }


    public SgoodView getOne(Integer id) {
        return new Query.QueryBuilder<SgoodView>(mainSQL)
                .setPredicate(ALIAS_MAIN+".sgood_id=:sgoodId")
                .build(SgoodView.class)
                .executeOne("sgoodId", id);
    }

    public List<SgoodView> getAllClusters(Date onDate) {
        if(onDate==null) onDate = new Date();
        return new Query.QueryBuilder<SgoodView>(
                "SELECT s.*, ss.clustergood_id folder_id " +
                     "FROM sgood s " +
                        "INNER JOIN subclustergood ss ON ss.sgood_id=s.sgood_id " +
                     "WHERE s.sgoodtype_id=1 and " +
                        ":showdate BETWEEN s.sgood_datebeg AND s.sgood_dateend " +
                     "ORDER BY s.parent_id, s.sgood_name")
                .setParam("showdate",onDate)
                .build(SgoodView.class)
                .execute();
    }

    private String buildAttrFields(GridDataOption gridDataOption) {
        List<Integer> attrIds = (List<Integer>) gridDataOption.getFilters().get("attributes");
        if(attrIds==null) {
            return "";
        }
        return attrIds.stream()
                .map(id-> {
                    gridDataOption.addFilter("attr"+id,id);
                    return String.format(attrSQLPattern, id);
                })
                .collect(Collectors.joining(""));
    }

    private String buildPriceFields(GridDataOption gridDataOption) {
        List<Integer> priceTypeIds = (List<Integer>) gridDataOption.getFilters().get("priceTypes");
        if(priceTypeIds==null) {
            return "";
        }
        return priceTypeIds.stream()
                .map(id-> {
                    gridDataOption.addFilter("priceType"+id,id);
                    return String.format(priceSQLPattern, id);
                })
                .collect(Collectors.joining(""));
    }

    private String buildWeightField(GridDataOption gridDataOption) {
        Integer weightFlag = (Integer) gridDataOption.getFilters().get("weightFlag");
        if(weightFlag==null) {
            return "";
        }
        // для GC вес не хранится
        if(Config.CURRENT_EDITION_TAG== EditionTag.GELICON_UTILITIES) {
            return "";
        }
        return weightSQLPattern+weightGrossSQLPattern;
    }

    private String buildPackageField(GridDataOption gridDataOption) {
        Integer packTypeId = (Integer) gridDataOption.getFilters().get("packageType");
        if(packTypeId==null) {
            return "";
        }
        // для GC вес не хранится
        if(Config.CURRENT_EDITION_TAG== EditionTag.GELICON_UTILITIES) {
            return "";
        }
        return packageSQLPattern;
    }

}

