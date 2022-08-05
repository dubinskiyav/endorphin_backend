package biz.gelicon.core.components.core.constantvalue;

import biz.gelicon.core.components.core.constant.Constant;
import biz.gelicon.core.components.core.constant.ConstantRepository;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import biz.gelicon.core.components.core.constant.Constant.ConstantType;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConstantValueService extends BaseService<ConstantValue> {
    private static final Logger logger = LoggerFactory.getLogger(ConstantValueService.class);
    public static final String ALIAS_MAIN = "m0";

    final String[] tableNames = new String[] {
            "cv_integer","cv_boolean","cv_string","cv_date",
            "cv_float","cv_money"
    };

    @Autowired
    private ConstantValueRepository constantValueRepository;
    @Autowired
    private ConstantValueValidator constantValueValidator;
    @Autowired
    private ConstantRepository constantRepository;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/constantvalue/mainSQL.sql")
    Resource mainSQL;

    @Value("classpath:/query/constantvalue/mainSQLForOne.sql")
    Resource mainSQLForOne;


    @PostConstruct
    public void init() {
        init(constantValueRepository, constantValueValidator);
    }

    public List<ConstantValueView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ConstantValueView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("constantvalue",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ConstantValueView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(ConstantValueView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<ConstantValueView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("constantvalue",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(ConstantValueView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(ConstantValueView.class)
                .count();

    }


    public ConstantValueView getOne(Integer idValue) {
        return new Query.QueryBuilder<ConstantValueView>(mainSQLForOne)
                .setPredicate(ALIAS_MAIN+".constantvalue_id=:constantvalueId")
                .build(ConstantValueView.class)
                .executeOne("constantvalueId", idValue);
    }

    public String getDisplayForConstant(Constant c, ConstantValueDTO constant) {
        switch (ConstantType.values()[c.getConstantType()-1]) {
            case INTEGER:
                return constant.getConstantValueInteger()==null?"":String.valueOf(constant.getConstantValueInteger());
            case BOOLEAN:
                return (constant.getConstantValueBoolean()==null || constant.getConstantValueBoolean().intValue()==0)?"0":"1";
            case STRING:
                return constant.getConstantValueString();
            case DATE:
                return constant.getConstantValueDate()==null?"":
                        new SimpleDateFormat("dd.MM.yyy").format(constant.getConstantValueDate());
            case FLOAT:
                return constant.getConstantValueFloat()==null?"":String.valueOf(constant.getConstantValueFloat());
            case MONEY:
                return constant.getConstantValueMoney()==null?"":
                        new DecimalFormat("#,##0.00").format(constant.getConstantValueMoney());
            default:
                return null;
        }

    }

    public Object getTypedValueForConstant(Constant c, ConstantValueDTO constant) {
        switch (ConstantType.values()[c.getConstantType()-1]) {
            case INTEGER:
                return constant.getConstantValueInteger();
            case BOOLEAN:
                return constant.getConstantValueBoolean();
            case STRING:
                return constant.getConstantValueString();
            case DATE:
                return constant.getConstantValueDate();
            case FLOAT:
                return constant.getConstantValueFloat();
            case MONEY:
                return constant.getConstantValueMoney();
            default:
                return null;
        }
    }

    public void setTypedValueForConstant(Constant c, ConstantValueDTO constant, Object value) {
        switch (ConstantType.values()[c.getConstantType()-1]) {
            case INTEGER:
                constant.setConstantValueInteger((Integer) value);
                break;
            case BOOLEAN:
                constant.setConstantValueBoolean((Integer) value);
                break;
            case STRING:
                constant.setConstantValueString((String) value);
                break;
            case DATE:
                constant.setConstantValueDate((Date) value);
                break;
            case FLOAT:
                if(value instanceof Double) {
                    constant.setConstantValueFloat(((Double) value).floatValue());
                } else {
                    constant.setConstantValueFloat((Float) value);
                }
                break;
            case MONEY:
                if(value instanceof BigDecimal) {
                    constant.setConstantValueMoney(((BigDecimal) value).doubleValue());
                } else {
                    constant.setConstantValueMoney((Double) value);
                }
                break;
        }
    }

    /**
     * Сохраняет типизированную константу
     * @param c - арефакт-константа
     * @param constantValueId - идентификатор существующей запиши значения константы для которой идео сохранение
     * @param constantValueDTO - DTO, содержащие типизированные данные
     */
    public void saveTypedValue(Constant c, Integer constantValueId, ConstantValueDTO constantValueDTO) {
        String tableName = tableNames[c.getConstantType() - 1];
        int count = executeSQLForConstantValue(constantValueDTO.getConstantValueId()==null,
                                                c, constantValueId, constantValueDTO, tableName);
        // был update, но запись не найдена
        if(constantValueDTO.getConstantValueId()!=null && count==0) {
            // порпобем всавить
            executeSQLForConstantValue(true,c, constantValueId, constantValueDTO, tableName);
        }
    }

    /**
     * Загружаем типизированную константу
     * @param c арефакт-константа
     * @param constantValueDTO DTO, в котором необходимы типизированные данные
     */
    public void loadTypedValue(Constant c, ConstantValueDTO constantValueDTO) {
        String tableName = tableNames[c.getConstantType() - 1];
        List<Map<String, Object>> typvalue = constantRepository.findQueryForMap("" +
                        "SELECT * FROM " + tableName + " " +
                        "WHERE constantvalue_id=:constantValueId",
                "constantValueId", constantValueDTO.getConstantValueId());
        if(!typvalue.isEmpty()) {
            setTypedValueForConstant(c,constantValueDTO,typvalue.get(0).get("cv_value"));
        }
    }

    private int executeSQLForConstantValue(boolean insertOrEditMode, Constant c,
                                           Integer constantValueId, ConstantValueDTO constantValueDTO, String tableName) {
        String sqlCmd;
        if(insertOrEditMode) {
            sqlCmd = constantRepository.buildInsertClause(tableName,
                    new String[]{"constantvalue_id","cv_value"},
                    new String[]{"constantValueId","cv_value"});
        } else {
            sqlCmd = constantRepository.buildUpdateClause(tableName,
                    new String[]{"cv_value"},
                    "constantvalue_id=:constantValueId");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("constantValueId",constantValueId);
        params.put("cv_value",getTypedValueForConstant(c,constantValueDTO));
        return constantRepository.executeSQL(sqlCmd, params);
    }
}

