package biz.gelicon.core.components.core.constantvalue;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 16.06.2021 13:45 */
@Schema(description = "Объект \"Значения константы\"")
public class ConstantValueDTO {

    @Id
    @Schema(description = "Идентификатор \"Значения константы\"")
    private Integer constantValueId;

    @Schema(description = "Ресурс ИД")
    private Integer constantId;

    @Schema(description = "Дата")
    private Date constantValueDatebeg;

    @Schema(description = "Представление значения")
    private String constantValueDisplay;

    @Schema(description = "Значение типа boolean(0 или 1)")
    private Integer constantValueBoolean = 0;

    @Schema(description = "Значение типа date")
    private Date constantValueDate;

    @Schema(description = "Значение типа float")
    private Float constantValueFloat;

    @Schema(description = "Значение типа integer")
    private Integer constantValueInteger;

    @Schema(description = "Значение типа money")
    private Double constantValueMoney;

    @Schema(description = "Значение типа string")
    private String constantValueString;

    public ConstantValueDTO() {}

    public ConstantValueDTO(
            Integer constantValueId,
            Integer constantId,
            Date constantValueDatebeg,
            String constantValueDisplay) {
        this.constantValueId = constantValueId;
        this.constantId = constantId;
        this.constantValueDatebeg = constantValueDatebeg;
        this.constantValueDisplay = constantValueDisplay;
    }

    public ConstantValueDTO(ConstantValue entity) {
        this.constantValueId = entity.getConstantValueId();
        this.constantId = entity.getConstantId();
        this.constantValueDatebeg = entity.getConstantValueDatebeg();
        this.constantValueDisplay = entity.getConstantValueDisplay();
    }

    public ConstantValue toEntity() {
        return toEntity(new ConstantValue());
    }

    public ConstantValue toEntity(ConstantValue entity) {
        entity.setConstantValueId(this.constantValueId);
        entity.setConstantId(this.constantId);
        entity.setConstantValueDatebeg(this.constantValueDatebeg);
        entity.setConstantValueDisplay(this.constantValueDisplay);
        return entity;
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

    public Integer getConstantValueBoolean() {
        return constantValueBoolean;
    }

    public void setConstantValueBoolean(Integer constantValueBoolean) {
        this.constantValueBoolean = constantValueBoolean;
    }

    public Date getConstantValueDate() {
        return constantValueDate;
    }

    public void setConstantValueDate(Date constantValueDate) {
        this.constantValueDate = constantValueDate;
    }

    public Float getConstantValueFloat() {
        return constantValueFloat;
    }

    public void setConstantValueFloat(Float constantValueFloat) {
        this.constantValueFloat = constantValueFloat;
    }

    public Integer getConstantValueInteger() {
        return constantValueInteger;
    }

    public void setConstantValueInteger(Integer constantValueInteger) {
        this.constantValueInteger = constantValueInteger;
    }

    public Double getConstantValueMoney() {
        return constantValueMoney;
    }

    public void setConstantValueMoney(Double constantValueMoney) {
        this.constantValueMoney = constantValueMoney;
    }

    public String getConstantValueString() {
        return constantValueString;
    }

    public void setConstantValueString(String constantValueString) {
        this.constantValueString = constantValueString;
    }


}

