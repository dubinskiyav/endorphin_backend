package biz.gelicon.core.components.core.capclass;

import biz.gelicon.core.components.core.capclass.CapClass;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 06.06.2021 19:36 */
@Schema(description = "Представление объекта \"Классификатор\"")
public class CapClassView {

    @Schema(description = "Идентификатор \"Классификатор\"")
    @Column(name="capclass_id")
    public Integer capClassId;

    @Schema(description = "Тип классификатора ИД")
    @Column(name="capclasstype_id")
    private Integer capClassTypeId;

    @Schema(description = "Код")
    @Column(name="capclass_code")
    private String capClassCode;

    @Schema(description = "Наименование")
    @Column(name="capclass_name")
    private String capClassName;

    @Schema(description = "Значение")
    @Column(name="capclass_value")
    private Double capClassValue;

    @Schema(description = "Код сортировки")
    @Column(name="capclass_sortcode")
    private String capClassSortcode;

    @Schema(description = "Флаг")
    @Column(name="capclass_flag")
    private Integer capClassFlag;

    @Schema(description = "Флаг блокировки")
    @Column(name="capclass_blockflag")
    private Integer capClassBlockflag;

    @Schema(description = "Примечание")
    @Column(name="capclass_remark")
    private String capClassRemark;


    public CapClassView() {}

    public CapClassView(
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

    public CapClassView(CapClass entity) {
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

