package biz.gelicon.core.components.core.capclass;

import biz.gelicon.core.components.core.capclass.CapClass;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;

/* Объект сгенерирован 02.07.2021 10:11 */
@Schema(description = "Объект \"Классификатор\"")
public class CapClassDTO {

    @Id
    @Schema(description = "Идентификатор \"Классификатор\"")
    private Integer capClassId;

    @Schema(description = "Тип классификатора ИД")
    private Integer capClassTypeId;

    @Schema(description = "Код")
    private String capClassCode;

    @Schema(description = "Наименование")
    private String capClassName;

    @Schema(description = "Значение")
    private Double capClassValue;

    @Schema(description = "Код сортировки")
    private String capClassSortcode;

    @Schema(description = "Флаг")
    private Integer capClassFlag;

    @Schema(description = "Флаг блокировки")
    private Integer capClassBlockflag;

    @Schema(description = "Примечание")
    private String capClassRemark;


    public CapClassDTO() {}

    public CapClassDTO(
            Integer capClassId,
            Integer capClassTypeId,
            String capClassCode,
            String capClassName,
            Double capClassValue,
            String capClassSortcode,
            Integer capClassFlag,
            Integer capClassBlockflag,
            String capClassRemark) {
        this.capClassId = capClassId;
        this.capClassTypeId = capClassTypeId;
        this.capClassCode = capClassCode;
        this.capClassName = capClassName;
        this.capClassValue = capClassValue;
        this.capClassSortcode = capClassSortcode;
        this.capClassFlag = capClassFlag;
        this.capClassBlockflag = capClassBlockflag;
        this.capClassRemark = capClassRemark;
    }

    public CapClassDTO(CapClass entity) {
        this.capClassId = entity.getCapClassId();
        this.capClassTypeId = entity.getCapClassTypeId();
        this.capClassCode = entity.getCapClassCode();
        this.capClassName = entity.getCapClassName();
        this.capClassValue = entity.getCapClassValue();
        this.capClassSortcode = entity.getCapClassSortcode();
        this.capClassFlag = entity.getCapClassFlag();
        this.capClassBlockflag = entity.getCapClassBlockflag();
        this.capClassRemark = entity.getCapClassRemark();
    }

    public CapClass toEntity() {
        return toEntity(new CapClass());
    }

    public CapClass toEntity(CapClass entity) {
        entity.setCapClassId(this.capClassId);
        entity.setCapClassTypeId(this.capClassTypeId);
        entity.setCapClassCode(this.capClassCode);
        entity.setCapClassName(this.capClassName);
        entity.setCapClassValue(this.capClassValue);
        entity.setCapClassSortcode(this.capClassSortcode);
        entity.setCapClassFlag(this.capClassFlag);
        entity.setCapClassBlockflag(this.capClassBlockflag);
        entity.setCapClassRemark(this.capClassRemark);
        return entity;
    }

    public Integer getCapClassId() {
        return capClassId;
    }

    public void setCapClassId(Integer value) {
        this.capClassId = value;
    }

    public Integer getCapClassTypeId() {
        return capClassTypeId;
    }

    public void setCapClassTypeId(Integer value) {
        this.capClassTypeId = value;
    }

    public String getCapClassCode() {
        return capClassCode;
    }

    public void setCapClassCode(String value) {
        this.capClassCode = value;
    }

    public String getCapClassName() {
        return capClassName;
    }

    public void setCapClassName(String value) {
        this.capClassName = value;
    }

    public Double getCapClassValue() {
        return capClassValue;
    }

    public void setCapClassValue(Double value) {
        this.capClassValue = value;
    }

    public String getCapClassSortcode() {
        return capClassSortcode;
    }

    public void setCapClassSortcode(String value) {
        this.capClassSortcode = value;
    }

    public Integer getCapClassFlag() {
        return capClassFlag;
    }

    public void setCapClassFlag(Integer value) {
        this.capClassFlag = value;
    }

    public Integer getCapClassBlockflag() {
        return capClassBlockflag;
    }

    public void setCapClassBlockflag(Integer value) {
        this.capClassBlockflag = value;
    }

    public String getCapClassRemark() {
        return capClassRemark;
    }

    public void setCapClassRemark(String value) {
        this.capClassRemark = value;
    }


}

