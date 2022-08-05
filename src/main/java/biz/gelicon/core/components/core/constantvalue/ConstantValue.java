package biz.gelicon.core.components.core.constantvalue;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.constant.Constant;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 16.06.2021 13:45 */
@Table(
    name = "constantvalue",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"constantvalue_datebeg", "constant_id"})
    }
)
@TableDescription("Значения константы")
public class ConstantValue {

    @Id
    @Column(name = "constantvalue_id",nullable = false)
    public Integer constantValueId;

    @ManyToOne(targetEntity = Constant.class)
    @Column(name = "constant_id", nullable = false)
    @NotNull(message = "Поле \"Ресурс ИД\" не может быть пустым")
    private Integer constantId;

    @Column(name = "constantvalue_datebeg", nullable = false)
    @NotNull(message = "Поле \"Дата\" не может быть пустым")
    private Date constantValueDatebeg;

    @Column(name = "constantvalue_display", nullable = false)
    @Size(max = 255, message = "Поле \"Значение представление\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Значение представление\" не может быть пустым")
    private String constantValueDisplay;

    public Integer getConstantValueId() {
        return constantValueId;
    }

    public void setConstantValueId(Integer value) {
        this.constantValueId = value;
    }

    public Integer getConstantId() {
        return constantId;
    }

    public void setConstantId(Integer value) {
        this.constantId = value;
    }

    public Date getConstantValueDatebeg() {
        return constantValueDatebeg;
    }

    public void setConstantValueDatebeg(Date value) {
        this.constantValueDatebeg = value;
    }

    public String getConstantValueDisplay() {
        return constantValueDisplay;
    }

    public void setConstantValueDisplay(String value) {
        this.constantValueDisplay = value;
    }


    public ConstantValue() {}

    public ConstantValue(
            Integer constantValueId,
            Integer constantId,
            Date constantValueDatebeg,
            String constantValueDisplay) {
        this.constantValueId = constantValueId;
        this.constantId = constantId;
        this.constantValueDatebeg = constantValueDatebeg;
        this.constantValueDisplay = constantValueDisplay;
    }
}

