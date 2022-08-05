package biz.gelicon.core.components.erp.todo.pricesgoodsubject;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 16.10.2021 10:48 */
@Schema(description = "Объект \"Прайс ТМЦ ОАУ\"")
public class PriceSgoodSubjectDTO {

    @Id
    @Schema(description = "Идентификатор \"Прайс ТМЦ ОАУ\"")
    private Integer priceSgoodSubjectId;

    @Schema(description = "Уровень цены")
    private Integer priceClusterId;

    @Schema(description = "Идентификаор ТМЦ")
    private Integer sgoodId;

    @Schema(description = "Идентификатор владельца прайс-листа")
    private Integer subjectId;

    @Schema(description = "Значение")
    private Double priceSgoodSubjectValue;

    @Schema(description = "Дата вступления в действие")
    private Date priceSgoodSubjectDate;

    public PriceSgoodSubjectDTO() {}

    public PriceSgoodSubjectDTO(
            Integer priceSgoodSubjectId,
            Integer priceClusterId,
            Integer sgoodId,
            Integer subjectId,
            Double priceSgoodSubjectValue,
            Date priceSgoodSubjectDate) {
        this.priceSgoodSubjectId = priceSgoodSubjectId;
        this.priceClusterId = priceClusterId;
        this.sgoodId = sgoodId;
        this.subjectId = subjectId;
        this.priceSgoodSubjectValue = priceSgoodSubjectValue;
        this.priceSgoodSubjectDate = priceSgoodSubjectDate;
    }

    public PriceSgoodSubjectDTO(PriceSgoodSubject entity) {
        this.priceSgoodSubjectId = entity.getPriceSgoodSubjectId();
        this.priceClusterId = entity.getPriceClusterId();
        this.sgoodId = entity.getSgoodId();
        this.subjectId = entity.getSubjectId();
        this.priceSgoodSubjectValue = entity.getPriceSgoodSubjectValue();
        this.priceSgoodSubjectDate = entity.getPriceSgoodSubjectDate();
    }

    public PriceSgoodSubject toEntity() {
        return toEntity(new PriceSgoodSubject());
    }

    public PriceSgoodSubject toEntity(PriceSgoodSubject entity) {
        entity.setPriceSgoodSubjectId(this.priceSgoodSubjectId);
        entity.setPriceClusterId(this.priceClusterId);
        entity.setSgoodId(this.sgoodId);
        entity.setSubjectId(this.subjectId);
        entity.setPriceSgoodSubjectValue(this.priceSgoodSubjectValue);
        entity.setPriceSgoodSubjectDate(this.priceSgoodSubjectDate);
        return entity;
    }

    public Integer getPriceSgoodSubjectId() {
        return priceSgoodSubjectId;
    }

    public void setPriceSgoodSubjectId(Integer value) {
        this.priceSgoodSubjectId = value;
    }

    public Integer getPriceClusterId() {
        return priceClusterId;
    }

    public void setPriceClusterId(Integer value) {
        this.priceClusterId = value;
    }

    public Integer getSgoodId() {
        return sgoodId;
    }

    public void setSgoodId(Integer value) {
        this.sgoodId = value;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer value) {
        this.subjectId = value;
    }

    public Double getPriceSgoodSubjectValue() {
        return priceSgoodSubjectValue;
    }

    public void setPriceSgoodSubjectValue(Double value) {
        this.priceSgoodSubjectValue = value;
    }

    public Date getPriceSgoodSubjectDate() {
        return priceSgoodSubjectDate;
    }

    public void setPriceSgoodSubjectDate(Date value) {
        this.priceSgoodSubjectDate = value;
    }


}

