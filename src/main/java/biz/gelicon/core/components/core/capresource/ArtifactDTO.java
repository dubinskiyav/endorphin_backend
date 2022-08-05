package biz.gelicon.core.components.core.capresource;

import biz.gelicon.core.components.core.capresource.Artifact;
import biz.gelicon.core.dto.SelectDisplayData;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* Объект сгенерирован 21.05.2021 11:59 */
@Schema(description = "Объект \"Артефакт\"")
public class ArtifactDTO {

    @Id
    @Schema(description = "Идентификатор \"Артефакт\"")
    private Integer artifactId;

    @Schema(description = "Код")
    private String artifactCode;

    @Schema(description = "Наименование")
    private String artifactName;

    @Schema(description = "Тип")
    private Integer resourceTypeId;

    @Schema(description = "Имя типа артефакта")
    private String resourceTypeName;

    @Schema(description = "Флаг активности")
    private Integer artifactVisibleFlag;

    @Schema(description = "Автор")
    private String artifactAutor;

    @Schema(description = "Дата создания")
    private Date artifactDate;

    @Schema(description = "Дата последней модификации")
    private Date artifactLastmodify;

    @Schema(description = "Пользователь, выполнивший последнюю модификацию")
    private Integer lastProguserId;

    @Schema(description = "Имя рользователя, выполнивший последнюю модификацию")
    private String lastProguserName;


    @Schema(description = "Видимость из документов")
    private List<Integer> artifactDocumentIds = new ArrayList<>();

    @Schema(description = "Видимость из модулей")
    private List<Integer> artifactApplicationIds  = new ArrayList<>();

    // для констант
    @Schema(description = "Тип константы")
    private Integer constantTypeId;

    @Schema(description = "Группа константы")
    private Integer constantGroupId;

    @Schema(description = "Имя группу констант")
    private String constantGroupName;

    // для признаков
    @Schema(description = "Тип признака")
    private Integer attributeTypeId;

    @Schema(description = "Имя типа признака")
    private String attributeTypeName;

    @Schema(description = "Группа признака")
    private Integer attributeGroupId;

    @Schema(description = "Тип справочника признака")
    private Integer attributeCapClassTypeId;

    @Schema(description = "Флаг признака \"Исторический\"")
    private Integer attributeHistoricityFlag;

    @Schema(description = "Имя группы атрибута")
    private String attributegroupName;

    @Schema(description = "Имя классификатора")
    private String attributeCapclasstypeName;

    @Schema(description = "Уровень ОАУ")
    private SelectDisplayData attributeSubject;

    public ArtifactDTO() {}

    public ArtifactDTO(
            Integer artifactId,
            String artifactCode,
            String artifactName,
            Integer resourceTypeId) {
        this.artifactId = artifactId;
        this.artifactCode = artifactCode;
        this.artifactName = artifactName;
        this.resourceTypeId = resourceTypeId;
    }

    public ArtifactDTO(Artifact entity) {
        this.artifactId = entity.getArtifactId();
        this.artifactCode = entity.getArtifactCode();
        this.artifactName = entity.getArtifactName();
        this.resourceTypeId = entity.getResourceTypeId();
        this.lastProguserId = entity.getLastProguserId();
        this.artifactVisibleFlag = entity.getArtifactVisibleFlag();
        this.artifactAutor = entity.getArtifactAutor();
        this.artifactDate= entity.getArtifactDate();
        this.artifactLastmodify= entity.getArtifactLastmodify();
    }

    public Artifact toEntity() {
        return toEntity(new Artifact());
    }

    public Artifact toEntity(Artifact entity) {
        entity.setArtifactId(this.artifactId);
        entity.setArtifactCode(this.artifactCode);
        entity.setArtifactName(this.artifactName);
        entity.setResourceTypeId(this.resourceTypeId);
        entity.setLastProguserId(this.lastProguserId);
        entity.setArtifactVisibleFlag(this.artifactVisibleFlag);
        entity.setArtifactAutor(this.artifactAutor);
        entity.setArtifactDate(this.artifactDate);
        entity.setArtifactLastmodify(this.artifactLastmodify);
        return entity;
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

    public Integer getArtifactVisibleFlag() {
        return artifactVisibleFlag;
    }

    public void setArtifactVisibleFlag(Integer artifactVisibleFlag) {
        this.artifactVisibleFlag = artifactVisibleFlag;
    }

    public String getArtifactAutor() {
        return artifactAutor;
    }

    public void setArtifactAutor(String artifactAutor) {
        this.artifactAutor = artifactAutor;
    }

    public Date getArtifactDate() {
        return artifactDate;
    }

    public void setArtifactDate(Date artifactDate) {
        this.artifactDate = artifactDate;
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

    public String getResourceTypeName() {
        return resourceTypeName;
    }

    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    public String getLastProguserName() {
        return lastProguserName;
    }

    public void setLastProguserName(String lastProguserName) {
        this.lastProguserName = lastProguserName;
    }

    public List<Integer> getArtifactDocumentIds() {
        return artifactDocumentIds;
    }

    public void setArtifactDocumentIds(List<Integer> artifactDocumentIds) {
        this.artifactDocumentIds = artifactDocumentIds;
    }

    public List<Integer> getArtifactApplicationIds() {
        return artifactApplicationIds;
    }

    public void setArtifactApplicationIds(List<Integer> artifactApplicationIds) {
        this.artifactApplicationIds = artifactApplicationIds;
    }

    public Integer getConstantTypeId() {
        return constantTypeId;
    }

    public void setConstantTypeId(Integer constantTypeId) {
        this.constantTypeId = constantTypeId;
    }

    public Integer getConstantGroupId() {
        return constantGroupId;
    }

    public void setConstantGroupId(Integer constantGroupId) {
        this.constantGroupId = constantGroupId;
    }

    public Integer getAttributeTypeId() {
        return attributeTypeId;
    }

    public void setAttributeTypeId(Integer attributeTypeId) {
        this.attributeTypeId = attributeTypeId;
    }

    public Integer getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Integer attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public Integer getAttributeCapClassTypeId() {
        return attributeCapClassTypeId;
    }

    public void setAttributeCapClassTypeId(Integer attributeCapClassTypeId) {
        this.attributeCapClassTypeId = attributeCapClassTypeId;
    }

    public Integer getAttributeHistoricityFlag() {
        return attributeHistoricityFlag;
    }

    public void setAttributeHistoricityFlag(Integer attributeHistoricityFlag) {
        this.attributeHistoricityFlag = attributeHistoricityFlag;
    }

    public String getConstantGroupName() {
        return constantGroupName;
    }

    public void setConstantGroupName(String constantGroupName) {
        this.constantGroupName = constantGroupName;
    }

    public String getAttributegroupName() {
        return attributegroupName;
    }

    public void setAttributegroupName(String attributegroupName) {
        this.attributegroupName = attributegroupName;
    }

    public String getAttributeTypeName() {
        return attributeTypeName;
    }

    public void setAttributeTypeName(String attributeTypeName) {
        this.attributeTypeName = attributeTypeName;
    }

    public String getAttributeCapclasstypeName() {
        return attributeCapclasstypeName;
    }

    public void setAttributeCapclasstypeName(String attributeCapclasstypeName) {
        this.attributeCapclasstypeName = attributeCapclasstypeName;
    }

    public SelectDisplayData getAttributeSubject() {
        return attributeSubject;
    }

    public void setAttributeSubject(SelectDisplayData attributeSubject) {
        this.attributeSubject = attributeSubject;
    }
}

