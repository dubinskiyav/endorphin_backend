package biz.gelicon.core.components.core.attribute;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 11.06.2021 13:39 */
@Schema(description = "Представление объекта \"Признак\"")
public class AttributeView {

    @Schema(description = "Идентификатор \"Признак\"")
    @Column(name="attribute_id")
    public Integer id;

    @Schema(description = "Группа признаков ИД")
    @Column(name="attributegroup_id")
    private Integer attributegroupId;

    @Schema(description = "Имя группы атрибута")
    @Column(name="capclass_name",table = "capclass")
    private String attributegroupName;

    @Schema(description = "Кодификатор ИД")
    @Column(name="capcode_id")
    private Integer capcodeId;

    @Schema(description = "Имя кодификатора")
    @Column(name="capcode_name",table = "capcode")
    private String capcodeName;

    @Schema(description = "Тип классификатора ИД")
    @Column(name="capclasstype_id")
    private Integer capclasstypeId;

    @Schema(description = "Имя классификатора")
    @Column(name="capclasstype_name",table = "capclasstype")
    private String capclasstypeName;

    @Schema(description = "Историчный")
    @Column(name="attribute_historicity")
    private Integer attributeHistoricity;

    @Schema(description = "Объект аналитики ИД")
    @Column(name="subject_id")
    private Integer subjectId;

    @Schema(description = "Имя ОАУ")
    @Column(name="subject_name",table = "subject")
    private String subjectName;

    public AttributeView() {}

    public AttributeView(
            Integer id,
            Integer attributegroupId,
            Integer capcodeId,
            Integer capclasstypeId,
            Integer attributeHistoricity,
            Integer subjectId) {
        this.id = id;
        this.attributegroupId = attributegroupId;
        this.capcodeId = capcodeId;
        this.capclasstypeId = capclasstypeId;
        this.attributeHistoricity = attributeHistoricity;
        this.subjectId = subjectId;
    }

    public AttributeView(Attribute entity) {
        this.id = entity.getId();
        this.attributegroupId = entity.getAttributegroupId();
        this.capcodeId = entity.getCapcodeId();
        this.capclasstypeId = entity.getCapclasstypeId();
        this.attributeHistoricity = entity.getAttributeHistoricity();
        this.subjectId = entity.getSubjectId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getAttributegroupId() {
        return attributegroupId;
    }

    public void setAttributegroupId(Integer value) {
        this.attributegroupId = value;
    }

    public Integer getCapcodeId() {
        return capcodeId;
    }

    public void setCapcodeId(Integer value) {
        this.capcodeId = value;
    }

    public Integer getCapclasstypeId() {
        return capclasstypeId;
    }

    public void setCapclasstypeId(Integer value) {
        this.capclasstypeId = value;
    }

    public Integer getAttributeHistoricity() {
        return attributeHistoricity;
    }

    public void setAttributeHistoricity(Integer value) {
        this.attributeHistoricity = value;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer value) {
        this.subjectId = value;
    }

    public String getAttributegroupName() {
        return attributegroupName;
    }

    public void setAttributegroupName(String attributegroupName) {
        this.attributegroupName = attributegroupName;
    }

    public String getCapcodeName() {
        return capcodeName;
    }

    public void setCapcodeName(String capcodeName) {
        this.capcodeName = capcodeName;
    }

    public String getCapclasstypeName() {
        return capclasstypeName;
    }

    public void setCapclasstypeName(String capclasstypeName) {
        this.capclasstypeName = capclasstypeName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}

