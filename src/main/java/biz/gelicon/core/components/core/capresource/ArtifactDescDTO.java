package biz.gelicon.core.components.core.capresource;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Объект \"Артефакт\"")
public class ArtifactDescDTO {
    @Schema(description = "Код объекта \"Артефакт\"")
    private String code;

    @Schema(description = "Имя объекта \"Артефакт\"")
    private String name;

    @Schema(description = "Тип объекта \"Артефакт\"")
    private Integer kind;

    @Schema(description = "Свойства объекта \"Артефакт\"")
    private Map<String,Object> properties;

    @Schema(description = "Параметры для вызова объекта \"Артефакт\"")
    private Map<String,Object> params;

    public ArtifactDescDTO() {
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

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

