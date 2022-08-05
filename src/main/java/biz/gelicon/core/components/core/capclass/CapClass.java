package biz.gelicon.core.components.core.capclass;

import biz.gelicon.core.annotations.TableDescription;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/* Сущность сгенерирована 06.06.2021 19:36 */
@Table(
    name = "capclass",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"capclasstype_id", "capclass_code"})
    }
)
@TableDescription("Классификатор")
public class CapClass {

    @Id
    @Column(name = "capclass_id",nullable = false)
    public Integer capClassId;

    @Column(name = "capclasstype_id", nullable = false)
    private Integer capClassTypeId;

    @Column(name = "capclass_code", nullable = false)
    @Size(max = 20, message = "Поле \"Код\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Код\" не может быть пустым")
    private String capClassCode;

    @Column(name = "capclass_name", nullable = false)
    @Size(max = 255, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String capClassName;

    @Column(name = "capclass_value", nullable = true)
    private Double capClassValue;

    @Column(name = "capclass_sortcode", nullable = true)
    @Size(max = 10, message = "Поле \"Код сортировки\" должно содержать не более {max} символов")
    private String capClassSortcode;

    @Column(name = "capclass_flag", nullable = true)
    private Integer capClassFlag;

    @Column(name = "capclass_blockflag", nullable = false)
    @NotNull(message = "Поле \"Флаг блокировки\" не может быть пустым")
    private Integer capClassBlockflag;

    @Column(name = "capclass_remark", nullable = true)
    @Size(max = 255, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String capClassRemark;

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


    public CapClass() {}

    public CapClass(
            Integer capClassId,
            Integer capClassTypeId,
            String capClassCode,
            String capClassName,
            Double capClassValue,
            String capClassSortcode,
            byte[] capClassText,
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
}

