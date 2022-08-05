package biz.gelicon.core.components.erp.todo.sgood;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.erp.todo.clustergood.Clustergood;
import biz.gelicon.core.components.erp.todo.sgoodtype.Sgoodtype;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 10.06.2021 19:18 */
@Table(
    name = "sgood",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sgood_code"})
    }
)
@TableDescription("Материал или услуга")
public class Sgood {
    public final static int SGOOD_ROOT_ID = -1;

    @Id
    @Column(name = "sgood_id",nullable = false)
    public Integer sgoodId;

    @ManyToOne(targetEntity = Sgoodtype.class)
    @Column(name = "sgoodtype_id", nullable = false)
    private Integer sgoodTypeId;

    @ManyToOne(targetEntity = Clustergood.class)
    @Column(name = "clustergood_id", nullable = false)
    private Integer clustergoodId;

    @Column(name = "sgood_code", nullable = false)
    @Size(max = 50, message = "Поле \"Код\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Код\" не может быть пустым")
    private String sgoodCode;

    @Column(name = "sgood_name", nullable = false)
    @Size(max = 255, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String sgoodName;

    @Column(name = "sgood_name1", nullable = true)
    @Size(max = 255, message = "Поле \"Характеристика\" должно содержать не более {max} символов")
    private String sgoodName1;

    @Column(name = "sgood_datebeg", nullable = false)
    @NotNull(message = "Поле \"Дата открытия\" не может быть пустым")
    private Date sgoodDatebeg;

    @Column(name = "sgood_dateend", nullable = false)
    @NotNull(message = "Поле \"Дата закрытия\" не может быть пустым")
    private Date sgoodDateend;

    @Column(name = "sgood_gost", nullable = true)
    @Size(max = 255, message = "Поле \"ГОСТ\" должно содержать не более {max} символов")
    private String sgoodGost;

    @Column(name = "sgood_description", nullable = true)
    @Size(max = 2000, message = "Поле \"Описание\" должно содержать не более {max} символов")
    private String sgoodDescription;

    @ManyToOne(targetEntity = Sgood.class)
    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    public Integer getSgoodId() {
        return sgoodId;
    }

    public void setSgoodId(Integer value) {
        this.sgoodId = value;
    }

    public Integer getSgoodTypeId() {
        return sgoodTypeId;
    }

    public void setSgoodTypeId(Integer value) {
        this.sgoodTypeId = value;
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


    public Sgood() {}

    public Sgood(
            Integer sgoodId,
            Integer clustergoodId,
            Integer sgoodTypeId,
            String sgoodName,
            String sgoodName1,
            String sgoodCode,
            Date sgoodDatebeg,
            Date sgoodDateend,
            String sgoodGost,
            String sgoodDescription,
            Integer parentId) {
        this.sgoodId = sgoodId;
        this.sgoodTypeId = sgoodTypeId;
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
}

