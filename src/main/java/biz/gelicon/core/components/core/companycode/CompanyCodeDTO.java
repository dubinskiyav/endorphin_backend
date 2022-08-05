package biz.gelicon.core.components.core.companycode;

import io.swagger.v3.oas.annotations.media.Schema;

public class CompanyCodeDTO {

    @Schema(description = "Код контрагент ИД")
    private Integer companyCodeId;

    @Schema(description = "контрагент ИД")
    private Integer companyId;

    @Schema(description = "Классификатор ИД")
    private Integer capClassId;

    @Schema(description = "Код")
    private String code;

    public CompanyCodeDTO(){}

    public CompanyCodeDTO(Integer companyCodeId, Integer companyId, Integer capClassId, String code) {
        this.companyCodeId = companyCodeId;
        this.companyId = companyId;
        this.capClassId = capClassId;
        this.code = code;
    }

    public Integer getCompanyCodeId() {
        return companyCodeId;
    }

    public void setCompanyCodeId(Integer companyCodeId) {
        this.companyCodeId = companyCodeId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCapClassId() {
        return capClassId;
    }

    public void setCapClassId(Integer capClassId) {
        this.capClassId = capClassId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
