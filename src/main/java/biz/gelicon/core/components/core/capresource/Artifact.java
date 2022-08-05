package biz.gelicon.core.components.core.capresource;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.artifacts.ArtifactCapconfigKind;
import biz.gelicon.core.artifacts.ArtifactTranKinds;
import biz.gelicon.core.components.core.proguser.Proguser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 20.05.2021 18:30 */
@Table(name = "capresource")
@TableDescription("Ресурс системы")
public class Artifact {

    @Id
    @Column(name = "capresource_id", nullable = false)
    private Integer artifactId;

    @Column(name = "capresource_code", nullable = false)
    @Size(max = 50, message = "Поле \"Код\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Код\" не может быть пустым")
    private String artifactCode;

    @Column(name = "capresource_name", nullable = false)
    @Size(max = 255, message = "Поле \"Имя\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    private String artifactName;

    @Column(name = "resourcetype_id", nullable = false)
    @NotNull(message = "Поле \"Тип артефакта\" не может быть пустым")
    private Integer resourceTypeId;

    @Column(name = "resourcetrantype_id", nullable = false)
    @NotNull(message = "Поле \"Язык\" не может быть пустым")
    private Integer resourcetrantypeId;

    @Column(name = "capconfig_id", nullable = false)
    @NotNull(message = "Поле \"Конфигурация\" не может быть пустым")
    private Integer capconfigId;

    /**
     * Побитная маска. 2 бит соответствует видимости
     */
    private final int VISIBLE_FLAG = 0b0100; // Видимый (второй бит)
    @Column(name = "capresource_active", nullable = false)
    @NotNull(message = "Поле \"Активность\" не может быть пустым")
    @JsonIgnore
    private Integer artifactActive = VISIBLE_FLAG; // Видимый (второй бит)

    @Column(name = "capresource_autor", nullable = true)
    @Size(max = 50, message = "Поле \"Автор\" должно содержать не более {max} символов")
    private String artifactAutor;

    @Column(name = "capresource_date", nullable = true)
    private Date artifactDate;

    @Column(name = "capresource_lastmodify", nullable = true)
    private Date artifactLastmodify;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "lastproguser_id", nullable = true)
    private Integer lastProguserId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    private Integer proguserId;

    public Artifact() {
        this.resourcetrantypeId = ArtifactTranKinds.JAVA.getResourceTranType(); // Здесь всегда JAVA
        this.capconfigId = ArtifactCapconfigKind.BASIC.getResourceCapconfigType(); // Базовая
        this.proguserId = Proguser.SYSDBA_PROGUSER_ID;
    }

    public Artifact(
            Integer artifactId,
            String artifactCode,
            String artifactName,
            Integer resourceTypeId
    ) {
        this();
        this.artifactId = artifactId;
        this.artifactCode = artifactCode;
        this.artifactName = artifactName;
        this.resourceTypeId = resourceTypeId;
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

    /**
     *
     * @return  ВИДИМОСТЬ артефакта из поля capresource_active. 1 - видимый, 0 - невидимый
     */
    public Integer getArtifactVisibleFlag() {
        // Второй бит - видимость
        if (artifactActive == null) return 0;
        return (artifactActive & VISIBLE_FLAG) != 0 ? 1 : 0;
    }

    /**
     * Устанавливает ВИДИМОСТЬ артецакт - устанавливает второй бит в поле capresource_active
     * @param artifactVisible 1 - делает видимым, 0 - невидимым
     */
    public void setArtifactVisibleFlag(Integer artifactVisible) {
        if (artifactVisible == null || artifactVisible == 0) {
            this.artifactActive -= (this.artifactActive & VISIBLE_FLAG);
        } else {
            this.artifactActive = this.artifactActive | VISIBLE_FLAG;
        }
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

    public Integer getResourcetrantypeId() {
        return resourcetrantypeId;
    }

    public void setResourcetrantypeId(Integer resourcetrantypeId) {
        this.resourcetrantypeId = resourcetrantypeId;
    }

    public Integer getCapconfigId() {
        return capconfigId;
    }

    public void setCapconfigId(Integer capconfigId) {
        this.capconfigId = capconfigId;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer proguserId) {
        this.proguserId = proguserId;
    }

    public Integer getArtifactActive() {
        return artifactActive;
    }

    public void setArtifactActive(Integer artifactActive) {
        this.artifactActive = artifactActive;
    }
}

