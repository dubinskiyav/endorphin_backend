package biz.gelicon.core.components.erp.todo.sgood;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 10.06.2021 19:18 */
@Schema(description = "Объект \"Материал или услуга\"")
public class SgoodDTO {

    @Id
    @Schema(description = "Идентификатор \"Материал или услуга\"")
    private Integer sgoodId;

    @Schema(description = "Тип товара ИД")
    private Integer sgoodtypeId;

    @Schema(description = "Уровень ИД")
    private Integer clustergoodId;

    @Schema(description = "Код")
    private String sgoodCode;

    @Schema(description = "Наименование")
    private String sgoodName;

    @Schema(description = "Характеристика")
    private String sgoodName1;

    @Schema(description = "Дата открытия")
    private Date sgoodDatebeg;

    @Schema(description = "Дата закрытия")
    private Date sgoodDateend;

    @Schema(description = "ГОСТ")
    private String sgoodGost;

    @Schema(description = "Описание")
    private String sgoodDescription;

    @Schema(description = "Родительский материал ИД")
    private Integer parentId;


    public SgoodDTO() {}

    public SgoodDTO(
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

    public SgoodDTO(Sgood entity) {
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

    public Sgood toEntity() {
        return toEntity(new Sgood());
    }

    public Sgood toEntity(Sgood entity) {
        entity.setSgoodId(this.sgoodId);
        entity.setSgoodTypeId(this.sgoodtypeId);
        entity.setClustergoodId(this.clustergoodId);
        entity.setSgoodCode(this.sgoodCode);
        entity.setSgoodName(this.sgoodName);
        entity.setSgoodName1(this.sgoodName1);
        entity.setSgoodDatebeg(this.sgoodDatebeg);
        entity.setSgoodDateend(this.sgoodDateend);
        entity.setSgoodGost(this.sgoodGost);
        entity.setSgoodDescription(this.sgoodDescription);
        entity.setParentId(this.parentId);
        return entity;
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


}

