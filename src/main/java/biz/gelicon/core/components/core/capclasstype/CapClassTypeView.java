package biz.gelicon.core.components.core.capclasstype;

import biz.gelicon.core.components.core.capclasstype.CapClassType;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 14.06.2021 10:04 */
@Schema(description = "Представление объекта \"Тип классификатора\"")
public class CapClassTypeView {

    @Schema(description = "Идентификатор \"Тип классификатора\"")
    @Column(name="capclasstype_id")
    public Integer capClassTypeId;

    @Schema(description = "Идентификатор артефакта")
    @Column(name="capresource_id",table ="capresource")
    private Integer capresourceId;

    @Schema(description = "Код")
    @Column(name="capclasstype_code")
    private String capClassTypeCode;

    @Schema(description = "Наименование")
    @Column(name="capclasstype_name")
    private String capClassTypeName;


    public CapClassTypeView() {}

    public CapClassTypeView(
            Integer capClassTypeId,
            Integer capresourceId,
            String capClassTypeCode,
            String capClassTypeName) {
        this.capClassTypeId = capClassTypeId;
        this.capresourceId = capresourceId;
        this.capClassTypeCode = capClassTypeCode;
        this.capClassTypeName = capClassTypeName;
    }

    public CapClassTypeView(CapClassType entity) {
        this.capClassTypeId = entity.getCapClassTypeId();
        this.capresourceId = entity.getCapresourceId();
        this.capClassTypeCode = entity.getCapClassTypeCode();
        this.capClassTypeName = entity.getCapClassTypeName();
    }

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


}

