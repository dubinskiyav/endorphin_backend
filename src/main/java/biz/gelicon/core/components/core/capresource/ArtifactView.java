package biz.gelicon.core.components.core.capresource;

import biz.gelicon.core.annotations.SQLExpression;
import biz.gelicon.core.components.core.capresource.Artifact;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 06.06.2021 11:11 */
@Schema(description = "Представление объекта \"Артефакт\"")
public class ArtifactView {

    @Schema(description = "Идентификатор \"Артефакт\"")
    @Column(name = "capresource_id")
    public Integer artifactId;

    @Schema(description = "Код")
    @Column(name = "capresource_code")
    private String artifactCode;

    @Schema(description = "Имя")
    @Column(name = "capresource_name")
    private String artifactName;

    @Schema(description = "Тип")
    @Column(name = "resourcetype_id")
    private Integer resourceTypeId;

    @Schema(description = "Имя типа артефакта")
    @Column(name = "resourcetype_name",table = "resourcetype")
    private String resourceTypeName;

    @Schema(description = "Битовая маска активности")
    @Column(name = "capresource_active")
    private Integer artifactActive;

    @Schema(description = "Дата последней модификации")
    @Column(name = "capresource_lastmodify")
    private Date artifactLastmodify;

    @Schema(description = "Идентификатор пользователя, выполнивший последнюю модификацию")
    @Column(name = "lastproguser_id")
    private Integer lastProguserId;

    @Schema(description = "Имя рользователя, выполнивший последнюю модификацию")
    @Column(name = "proguser_name",table = "proguser")
    private String lastProguserName;


    public ArtifactView() {}

    public ArtifactView(Artifact entity) {
        this.artifactId = entity.getArtifactId();
        this.artifactCode = entity.getArtifactCode();
        this.resourceTypeId = entity.getResourceTypeId();
        this.artifactName = entity.getArtifactName();
        this.lastProguserId = entity.getLastProguserId();
        this.artifactActive = entity.getArtifactVisibleFlag();
        this.artifactLastmodify = entity.getArtifactLastmodify();
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    public String getArtifactCode() {
        return artifactCode;
    }

    public void setArtifactCode(String artifactCode) {
        this.artifactCode = artifactCode;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public Date getArtifactLastmodify() {
        return artifactLastmodify;
    }

    public void setArtifactLastmodify(Date artifactLastmodify) {
        this.artifactLastmodify = artifactLastmodify;
    }

    public Integer getLastProguserId() {
        return lastProguserId;
    }

    public void setLastProguserId(Integer lastProguserId) {
        this.lastProguserId = lastProguserId;
    }

    public Integer getArtifactActive() {
        return artifactActive;
    }

    public void setArtifactActive(Integer artifactActive) {
        this.artifactActive = artifactActive;
    }

    public String getLastProguserName() {
        return lastProguserName;
    }

    public void setLastProguserName(String lastProguserName) {
        this.lastProguserName = lastProguserName;
    }

    public String getResourceTypeName() {
        return resourceTypeName;
    }

    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    @SQLExpression("coalesce(g_bitand(capresource_active,4)/4,0)")
    public Integer getArtifactVisibleFlag() {
        // Второй бит - видимость
        return artifactActive==null?0:(artifactActive.intValue() & 4) != 0?1:0;
    }

    public void setArtifactVisibleFlag(Integer artifactVisible) {
        if (artifactVisible == null || artifactVisible.intValue() == 0) {
            this.artifactActive -= (this.artifactActive.intValue() & 4);
        } else {
            this.artifactActive = this.artifactActive | 4;
        }
    }

}

