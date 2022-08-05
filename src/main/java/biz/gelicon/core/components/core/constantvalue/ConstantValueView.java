package biz.gelicon.core.components.core.constantvalue;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 16.06.2021 13:45 */
@Schema(description = "Представление объекта \"Значения константы\"")
public class ConstantValueView {

    @Schema(description = "Идентификатор \"Значения константы\"")
    @Column(name="constantvalue_id")
    public Integer constantValueId;

    @Schema(description = "Идентификатор артефакта-константы")
    @Column(name="constant_id")
    private Integer constantId;

    @Schema(description = "Дата")
    @Column(name="constantvalue_datebeg")
    private Date constantValueDatebeg;

    @Schema(description = "Значение представление")
    @Column(name="constantvalue_display")
    private String constantValueDisplay;


    public ConstantValueView() {}

    public ConstantValueView(
            Integer constantValueId,
            Integer constantId,
            Date constantValueDatebeg,
            String constantValueDisplay) {
        this.constantValueId = constantValueId;
        this.constantId = constantId;
        this.constantValueDatebeg = constantValueDatebeg;
        this.constantValueDisplay = constantValueDisplay;
    }

    public ConstantValueView(ConstantValue entity) {
        this.constantValueId = entity.getConstantValueId();
        this.constantId = entity.getConstantId();
        this.constantValueDatebeg = entity.getConstantValueDatebeg();
        this.constantValueDisplay = entity.getConstantValueDisplay();
    }

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


}

