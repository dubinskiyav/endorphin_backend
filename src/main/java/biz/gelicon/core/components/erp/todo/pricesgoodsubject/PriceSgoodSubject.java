package biz.gelicon.core.components.erp.todo.pricesgoodsubject;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.subject.Subject;
import biz.gelicon.core.components.erp.todo.sgood.Sgood;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/* Сущность сгенерирована 16.10.2021 10:48 */
@Table(
    name = "pricesgoodsubject",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pricecluster_id", "sgood_id", "subject_id", "pricesgoodsubject_date"})
    }
)
@TableDescription("Прайс ТМЦ ОАУ")
public class PriceSgoodSubject {

    @Id
    @Column(name = "pricesgoodsubject_id",nullable = false)
    public Integer priceSgoodSubjectId;

    @Column(name = "pricecluster_id", nullable = false)
    @NotNull(message = "Поле \"Уровень цены\" не может быть пустым")
    private Integer priceClusterId;

    @ManyToOne(targetEntity = Sgood.class)
    @Column(name = "sgood_id", nullable = false)
    @NotNull(message = "Поле \"ТМЦ\" не может быть пустым")
    private Integer sgoodId;

    @ManyToOne(targetEntity = Subject.class)
    @Column(name = "subject_id", nullable = false)
    @NotNull(message = "Поле \"Идентификатор владельца прайс-листа\" не может быть пустым")
    private Integer subjectId;

    @Column(name = "pricesgoodsubject_value", nullable = false)
    @NotNull(message = "Поле \"Значение\" не может быть пустым")
    private Double priceSgoodSubjectValue;

    @Column(name = "pricesgoodsubject_date", nullable = false)
    @NotNull(message = "Поле \"Дата вступления в действие\" не может быть пустым")
    private Date priceSgoodSubjectDate;

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

    public PriceSgoodSubject() {}

    public PriceSgoodSubject(
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
}

