package biz.gelicon.core.components.core.attribute;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.capclass.CapClass;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.core.subject.Subject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/* Сущность сгенерирована 10.06.2021 17:06 */
@Table(name = "attribute")
@TableDescription("Признак")
public class Attribute {

    @Id
    @Column(name = "attribute_id",nullable = false)
    public Integer id;

    @ManyToOne(targetEntity = CapClass.class)
    @Column(name = "attributegroup_id", nullable = false)
    private Integer attributegroupId;

    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "capcode_id", nullable = false)
    private Integer capcodeId;

    @Column(name = "capclasstype_id", nullable = true)
    private Integer capclasstypeId;

    @Column(name = "attribute_historicity", nullable = false)
    @NotNull(message = "Поле \"Историчный\" не может быть пустым")
    private Integer attributeHistoricity;

    @ManyToOne(targetEntity = Subject.class)
    @Column(name = "subject_id", nullable = true)
    private Integer subjectId;

    public Attribute(Integer artifactId) {
        this.id=artifactId;
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


    public Attribute() {}

    public Attribute(
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
}

