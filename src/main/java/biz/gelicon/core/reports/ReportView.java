package biz.gelicon.core.reports;

import biz.gelicon.core.reports.ReportDescriptionImpl;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Представление объекта \"Печатная форма\"")
public class ReportView {

    @Schema(description = "Код объекта \"Печатная форма\"")
    private String code;

    @Schema(description = "Имя объекта \"Печатная форма\"")
    private String name;

    @Schema(description = "Описание параметров \"Печатная форма\"")
    private Map<String, ReportDescriptionImpl.ParamDesc> params;

    @Schema(description = "Описание порядка следования (при вводе) параметров \"Печатная форма\"")
    private List<String> paramsOrder;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ReportDescriptionImpl.ParamDesc> getParams() {
        return params;
    }

    public void setParams(Map<String, ReportDescriptionImpl.ParamDesc> params) {
        this.params = params;
    }

    public List<String> getParamsOrder() {
        return paramsOrder;
    }

    public void setParamsOrder(List<String> paramsOrder) {
        this.paramsOrder = paramsOrder;
    }

    public ReportView(String code, String name, Map<String, ReportDescriptionImpl.ParamDesc> params, List<String> paramsOrder) {
        this.code = code;
        this.name = name;
        this.params = params;
        this.paramsOrder = paramsOrder;
    }

    public ReportView(ReportDescriptionImpl reportDesc) {
        this.code = reportDesc.getCode();
        this.name = reportDesc.getName();
        this.params = reportDesc.getParams();
        this.paramsOrder = reportDesc.getParamsOrder();
    }

}
