package biz.gelicon.core.components.erp.todo.sgood;

import biz.gelicon.core.components.erp.todo.sgood.Sgood;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 10.06.2021 19:18 */
@Schema(description = "Представление объекта \"Материал или услуга\"")
public class SgoodView {

    @Schema(description = "Идентификатор \"Материал или услуга\"")
    @Column(name="sgood_id")
    public Integer sgoodId;

    @Schema(description = "Тип товара ИД")
    @Column(name="sgoodtype_id")
    private Integer sgoodtypeId;

    @Schema(description = "Уровень ИД")
    @Column(name="clustergood_id")
    private Integer clustergoodId;

    @Schema(description = "Код")
    @Column(name="sgood_code")
    private String sgoodCode;

    @Schema(description = "Наименование")
    @Column(name="sgood_name")
    private String sgoodName;

    @Schema(description = "Характеристика")
    @Column(name="sgood_name1")
    private String sgoodName1;

    @Schema(description = "Дата открытия")
    @Column(name="sgood_datebeg")
    private Date sgoodDatebeg;

    @Schema(description = "Дата закрытия")
    @Column(name="sgood_dateend")
    private Date sgoodDateend;

    @Schema(description = "ГОСТ")
    @Column(name="sgood_gost")
    private String sgoodGost;

    @Schema(description = "Описание")
    @Column(name="sgood_description")
    private String sgoodDescription;

    @Schema(description = "Родительский материал ИД")
    @Column(name="parent_id")
    private Integer parentId;

    @Schema(description = "Базовая единица измерения")
    @Column(name="edizm_notation",table = "eizm")
    private String edizmNotation;

    @Schema(description = "Вес(Нетто) единицы")
    @Column(name="sgood_weight_netto",table = "good")
    private String sgoodWeightNetto;

    @Schema(description = "Кол-во в упаковке")
    @Column(name="package_quantity",table = "packagegood")
    private Double packageQuantity;

    public SgoodView() {}

    public SgoodView(
            Integer sgoodId,
            Integer sgoodtypeId,
            Integer clustergoodId,
            String sgoodCode,
            String sgoodName,
            String sgoodName1,
            Date sgoodDatebeg,
            Date sgoodDateend,
            String sgoodGost,
            String sgoodDescription,
            Integer parentId) {
        this.sgoodId = sgoodId;
        this.sgoodtypeId = sgoodtypeId;
        this.clustergoodId = clustergoodId;
        this.sgoodCode = sgoodCode;
        this.sgoodName = sgoodName;
        this.sgoodName1 = sgoodName1;
        this.sgoodDatebeg = sgoodDatebeg;
        this.sgoodDateend = sgoodDateend;
        this.sgoodGost = sgoodGost;
        this.sgoodDescription = sgoodDescription;
        this.parentId = parentId;
    }

    public SgoodView(Sgood entity) {
        this.sgoodId = entity.getSgoodId();
        this.sgoodtypeId = entity.getSgoodTypeId();
        this.clustergoodId = entity.getClustergoodId();
        this.sgoodCode = entity.getSgoodCode();
        this.sgoodName = entity.getSgoodName();
        this.sgoodName1 = entity.getSgoodName1();
        this.sgoodDatebeg = entity.getSgoodDatebeg();
        this.sgoodDateend = entity.getSgoodDateend();
        this.sgoodGost = entity.getSgoodGost();
        this.sgoodDescription = entity.getSgoodDescription();
        this.parentId = entity.getParentId();
    }

    public Integer getSgoodId() {
        return sgoodId;
    }

    public void setSgoodId(Integer value) {
        this.sgoodId = value;
    }

    public Integer getSgoodtypeId() {
        return sgoodtypeId;
    }

    public void setSgoodtypeId(Integer value) {
        this.sgoodtypeId = value;
    }

    public Integer getClustergoodId() {
        return clustergoodId;
    }

    public void setClustergoodId(Integer value) {
        this.clustergoodId = value;
    }

    public String getSgoodCode() {
        return sgoodCode;
    }

    public void setSgoodCode(String value) {
        this.sgoodCode = value;
    }

    public String getSgoodName() {
        return sgoodName;
    }

    public void setSgoodName(String value) {
        this.sgoodName = value;
    }

    public String getSgoodName1() {
        return sgoodName1;
    }

    public void setSgoodName1(String value) {
        this.sgoodName1 = value;
    }

    public Date getSgoodDatebeg() {
        return sgoodDatebeg;
    }

    public void setSgoodDatebeg(Date value) {
        this.sgoodDatebeg = value;
    }

    public Date getSgoodDateend() {
        return sgoodDateend;
    }

    public void setSgoodDateend(Date value) {
        this.sgoodDateend = value;
    }

    public String getSgoodGost() {
        return sgoodGost;
    }

    public void setSgoodGost(String value) {
        this.sgoodGost = value;
    }

    public String getSgoodDescription() {
        return sgoodDescription;
    }

    public void setSgoodDescription(String value) {
        this.sgoodDescription = value;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer value) {
        this.parentId = value;
    }

    public String getEdizmNotation() {
        return edizmNotation;
    }

    public void setEdizmNotation(String edizmNotation) {
        this.edizmNotation = edizmNotation;
    }

    public String getSgoodWeightNetto() {
        return sgoodWeightNetto;
    }

    public void setSgoodWeightNetto(String sgoodWeightNetto) {
        this.sgoodWeightNetto = sgoodWeightNetto;
    }

    public Double getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(Double packageQuantity) {
        this.packageQuantity = packageQuantity;
    }
}

