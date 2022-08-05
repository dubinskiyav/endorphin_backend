package biz.gelicon.core.components.core.capclasstype;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.capresource.Artifact;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/* Сущность сгенерирована 14.06.2021 10:04 */
@Table(
    name = "capclasstype",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"capclasstype_name"}),
        @UniqueConstraint(columnNames = {"capresource_id"})
    }
)
@TableDescription("Тип классификатора")
public class CapClassType {

    public static final int ATTRIBUTE_GROUP = 20;
    public static final int CONSTANT_GROUP = 21;

    @Id
    @Column(name = "capclasstype_id",nullable = false)
    public Integer capClassTypeId;

    @ManyToOne(targetEntity = Artifact.class)
    @Column(name = "capresource_id", nullable = false)
    private Integer capresourceId;

    @Column(name = "capclasstype_code", nullable = true)
    @Size(max = 10, message = "Поле \"Код\" должно содержать не более {max} символов")
    private String capClassTypeCode;

    @Column(name = "capclasstype_name", nullable = false)
    @Size(max = 128, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String capClassTypeName;

    public Integer getCapClassTypeId() {
        return capClassTypeId;
    }

    public void setCapClassTypeId(Integer value) {
        this.capClassTypeId = value;
    }

    public Integer getCapresourceId() {
        return capresourceId;
    }

    public void setCapresourceId(Integer value) {
        this.capresourceId = value;
    }

    public String getCapClassTypeCode() {
        return capClassTypeCode;
    }

    public void setCapClassTypeCode(String value) {
        this.capClassTypeCode = value;
    }

    public String getCapClassTypeName() {
        return capClassTypeName;
    }

    public void setCapClassTypeName(String value) {
        this.capClassTypeName = value;
    }


    public CapClassType() {}

    public CapClassType(
            Integer capClassTypeId,
            Integer capresourceId,
            String capClassTypeCode,
            String capClassTypeName) {
        this.capClassTypeId = capClassTypeId;
        this.capresourceId = capresourceId;
        this.capClassTypeCode = capClassTypeCode;
        this.capClassTypeName = capClassTypeName;
    }
}

