package biz.gelicon.core.components.core.capresourcerole;

import biz.gelicon.core.annotations.SQLExpression;
import biz.gelicon.core.components.core.capresource.Artifact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 21.05.2021 11:59 */
@Schema(description = "Представление объекта \"Артефакт\"")
public class ArtifactRoleView {

    @Schema(description = "Идентификатор \"Артефакт\"")
    @Column(name="capresource_id")
    public Integer artifactId;

    @Schema(description = "Код")
    @Column(name="capresource_code")
    private String artifactCode;

    @Schema(description = "Наименование")
    @Column(name="capresource_name")
    private String artifactName;

    @Schema(description = "Тип артефакта")
    @Column(name="resourcetype_id")
    private Integer resourceTypeId;

    @Schema(description = "Имя типа артефакта")
    @Column(name="resourcetype_name", table = "resourcetype")
    private String resourceTypeName;

    @JsonIgnore
    @Column(name="capresourcerole_id", table = "capresourcerole")
    private Integer allowArtifactRoleId;

    public ArtifactRoleView() {}

    public ArtifactRoleView(
            Integer artifactId,
            String artifactCode,
            String artifactName,
            Integer resourceTypeId) {
        this.artifactId = artifactId;
        this.artifactCode = artifactCode;
        this.artifactName = artifactName;
        this.resourceTypeId = resourceTypeId;
    }

    public ArtifactRoleView(Artifact entity) {
        this.artifactId = entity.getArtifactId();
        this.artifactCode = entity.getArtifactCode();
        this.artifactName = entity.getArtifactName();
        this.resourceTypeId = entity.getResourceTypeId();
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer value) {
        this.artifactId = value;
    }

    public String getArtifactCode() {
        return artifactCode;
    }

    public void setArtifactCode(String value) {
        this.artifactCode = value;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String value) {
        this.artifactName = value;
    }

    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer value) {
        this.resourceTypeId = value;
    }

    public String getResourceTypeName() {
        return resourceTypeName;
    }

    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    public Integer getAllowArtifactRoleId() {
        return allowArtifactRoleId;
    }

    public void setAllowArtifactRoleId(Integer allowArtifactRoleId) {
        this.allowArtifactRoleId = allowArtifactRoleId;
    }

    @Schema(description = "Доступ")
    // в случае capresourcerole_id is null будет 0, иначе 1
    @SQLExpression("coalesce(capresourcerole_id-capresourcerole_id+1,0)")
    public Integer getRoleAllowFlag() {
        return allowArtifactRoleId!=null?1:0;
    }

}

