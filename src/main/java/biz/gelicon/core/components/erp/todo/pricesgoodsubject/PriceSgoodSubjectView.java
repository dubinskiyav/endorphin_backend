package biz.gelicon.core.components.erp.todo.pricesgoodsubject;

import biz.gelicon.core.components.erp.todo.pricesgoodsubject.PriceSgoodSubject;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 16.10.2021 10:48 */
@Schema(description = "Представление объекта \"Прайс ТМЦ ОАУ\"")
public class PriceSgoodSubjectView {

    @Schema(description = "Идентификатор \"Прайс ТМЦ ОАУ\"")
    @Column(name="pricesgoodsubject_id")
    public Integer priceSgoodSubjectId;

    @Schema(description = "Уровень цены")
    @Column(name="pricecluster_id")
    private Integer priceclusterId;

    @Schema(description = "ТМЦ")
    @Column(name="sgood_id")
    private Integer sgoodId;

    @Schema(description = "Идентификатор владельца прайс-листа")
    @Column(name="subject_id")
    private Integer subjectId;

    @Schema(description = "Значение")
    @Column(name="pricesgoodsubject_value")
    private Double priceSgoodSubjectValue;

    @Schema(description = "Дата вступления в действие")
    @Column(name="pricesgoodsubject_date")
    private Date priceSgoodSubjectDate;


    public PriceSgoodSubjectView() {}

    public PriceSgoodSubjectView(
            Integer priceSgoodSubjectId,
            Integer priceclusterId,
            Integer sgoodId,
            Integer subjectId,
            Double priceSgoodSubjectValue,
            Date priceSgoodSubjectDate) {
        this.priceSgoodSubjectId = priceSgoodSubjectId;
        this.priceclusterId = priceclusterId;
        this.sgoodId = sgoodId;
        this.subjectId = subjectId;
        this.priceSgoodSubjectValue = priceSgoodSubjectValue;
        this.priceSgoodSubjectDate = priceSgoodSubjectDate;
    }

    public PriceSgoodSubjectView(PriceSgoodSubject entity) {
        this.priceSgoodSubjectId = entity.getPriceSgoodSubjectId();
        this.priceclusterId = entity.getPriceClusterId();
        this.sgoodId = entity.getSgoodId();
        this.subjectId = entity.getSubjectId();
        this.priceSgoodSubjectValue = entity.getPriceSgoodSubjectValue();
        this.priceSgoodSubjectDate = entity.getPriceSgoodSubjectDate();
    }

    public Integer getPriceSgoodSubjectId() {
        return priceSgoodSubjectId;
    }

    public void setPriceSgoodSubjectId(Integer value) {
        this.priceSgoodSubjectId = value;
    }

    public Integer getPriceclusterId() {
        return priceclusterId;
    }

    public void setPriceclusterId(Integer value) {
        this.priceclusterId = value;
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

