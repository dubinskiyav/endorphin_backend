package biz.gelicon.core.view;

import biz.gelicon.core.components.erp.todo.uniquetype.UniqueType;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 04.06.2021 10:39 */
@Schema(description = "Представление объекта \"Тип уникальности\"")
public class UniqueTypeView {

    @Schema(description = "Идентификатор \"Тип уникальности\"")
    @Column(name="uniquetype_id")
    public Integer uniqueTypeId;

    @Schema(description = "Наименование")
    @Column(name="uniquetype_name")
    private String uniqueTypeName;


    public UniqueTypeView() {}

    public UniqueTypeView(
            Integer uniqueTypeId,
            String uniqueTypeName) {
        this.uniqueTypeId = uniqueTypeId;
        this.uniqueTypeName = uniqueTypeName;
    }

    public UniqueTypeView(UniqueType entity) {
        this.uniqueTypeId = entity.getUniqueTypeId();
        this.uniqueTypeName = entity.getUniqueTypeName();
    }

    public Integer getUniqueTypeId() {
        return uniqueTypeId;
    }

    public void setUniqueTypeId(Integer value) {
        this.uniqueTypeId = value;
    }

    public String getUniqueTypeName() {
        return uniqueTypeName;
    }

    public void setUniqueTypeName(String value) {
        this.uniqueTypeName = value;
    }


}

