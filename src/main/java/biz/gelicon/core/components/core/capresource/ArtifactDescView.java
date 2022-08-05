package biz.gelicon.core.components.core.capresource;

import biz.gelicon.artifacts.ArtifactDescription;
import biz.gelicon.core.artifacts.ArtifactDescriptionImpl;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Представление объекта \"Описание артефакта\"")
public class ArtifactDescView {
    @Schema(description = "Код объекта \"Описание артефакта\"")
    private String code;

    @Schema(description = "Имя объекта \"Описание артефакта\"")
    private String name;

    @Schema(description = "Тип объекта \"Описание артефакта\"")
    private Integer resourceTypeId;

    @Schema(description = "Свойства объекта \"Описание артефакта\"")
    private Map<String,Object> properties;

    public ArtifactDescView() {
    }

    public ArtifactDescView(ArtifactDescription r) {
        this.code = r.getCode();
        this.name = r.getName();
        this.resourceTypeId = r.getKind()+1;
        this.properties = ((ArtifactDescriptionImpl) r).getProps();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
