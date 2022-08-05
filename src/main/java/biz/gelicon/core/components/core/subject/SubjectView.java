package biz.gelicon.core.components.core.subject;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 20.04.2021 10:26 */
@Schema(description = "Представление объекта \"Объект аналитики\"")
public class SubjectView {

    @Schema(description = "Идентификатор \"Объект аналитики\"")
    @Column(name="subject_id")
    public Integer subjectId;

    @Schema(description = "Идентификатор уровня в котором находится объект аналитики")
    @Column(name="cluster_id")
    private Integer clusterId;

    @Schema(description = "Идентификатор объекта-уровня в котором находится объект аналитики")
    @Column(name="parent_id")
    private Integer parentId;

    @Schema(description = "Имя объекта-уровня в котором находится объект аналитики")
    @Column(name="parent_name")
    private String parentName;

    @Schema(description = "Тип объекта аналитики")
    @Column(name="subjecttype_id")
    private Integer subjectTypeId;

    @Schema(description = "Наименование")
    @Column(name="subject_name")
    private String subjectName;

    @Schema(description = "Дата открытия")
    @Column(name="subject_datebeg")
    private Date subjectDatebeg;

    @Schema(description = "Дата закрытия")
    @Column(name="subject_dateend")
    private Date subjectDateend;

    @Schema(description = "Код")
    @Column(name="subject_code")
    private String subjectCode;

    @Schema(description = "Описание")
    @Column(name="subject_description")
    private String subjectDescription;

    @Schema(description = "Статус")
    @Column(name="subject_status")
    private Integer subjectStatus;

    @Schema(description = "proguser_id")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Дата создания")
    @Column(name="subject_datecreate")
    private Date subjectDatecreate;

    @Schema(description = "lastproguser_id")
    @Column(name="lastproguser_id")
    private Integer lastProguserId;

    @Schema(description = "Дата модификации")
    @Column(name="subject_datemodify")
    private Date subjectDatemodify;

    @Schema(description = "Пояснение")
    @Column(name="subject_remark")
    private String subjectRemark;

    @Schema(description = "rootsubject_id")
    @Column(name="rootsubject_id")
    private Integer rootSubjectId;

    @Schema(description = "Если объект аналитики является уровнем, то здесь указывается его clusterId")
    @Column(name="folder_id")
    private Integer folderId;

    public SubjectView() {}

    public SubjectView(
            Integer subjectId,
            Integer clusterId,
            Integer subjectTypeId,
            String subjectName,
            Date subjectDatebeg,
            Date subjectDateend,
            String subjectCode,
            String subjectDescription,
            Integer subjectStatus,
            Integer proguserId,
            Date subjectDatecreate,
            Integer lastProguserId,
            Date subjectDatemodify,
            String subjectRemark,
            Integer rootSubjectId,
            Integer folderId,
            Integer parentId) {
        this.subjectId = subjectId;
        this.clusterId = clusterId;
        this.subjectTypeId = subjectTypeId;
        this.subjectName = subjectName;
        this.subjectDatebeg = subjectDatebeg;
        this.subjectDateend = subjectDateend;
        this.subjectCode = subjectCode;
        this.subjectDescription = subjectDescription;
        this.subjectStatus = subjectStatus;
        this.proguserId = proguserId;
        this.subjectDatecreate = subjectDatecreate;
        this.lastProguserId = lastProguserId;
        this.subjectDatemodify = subjectDatemodify;
        this.subjectRemark = subjectRemark;
        this.rootSubjectId = rootSubjectId;
        this.parentId = parentId;
        this.folderId = folderId;
    }

    public SubjectView(Subject entity,Integer folderId) {
        this.subjectId = entity.getSubjectId();
        this.clusterId = entity.getClusterId();
        this.subjectTypeId = entity.getSubjectTypeId();
        this.subjectName = entity.getSubjectName();
        this.subjectDatebeg = entity.getSubjectDatebeg();
        this.subjectDateend = entity.getSubjectDateend();
        this.subjectCode = entity.getSubjectCode();
        this.subjectDescription = entity.getSubjectDescription();
        this.subjectStatus = entity.getSubjectStatus();
        this.proguserId = entity.getProguserId();
        this.subjectDatecreate = entity.getSubjectDatecreate();
        this.lastProguserId = entity.getLastProguserId();
        this.subjectDatemodify = entity.getSubjectDatemodify();
        this.subjectRemark = entity.getSubjectRemark();
        this.rootSubjectId = entity.getRootSubjectId();
        this.parentId = entity.getParentId();
        this.folderId = folderId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer value) {
        this.subjectId = value;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer value) {
        this.clusterId = value;
    }

    public Integer getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(Integer value) {
        this.subjectTypeId = value;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String value) {
        this.subjectName = value;
    }

    public Date getSubjectDatebeg() {
        return subjectDatebeg;
    }

    public void setSubjectDatebeg(Date value) {
        this.subjectDatebeg = value;
    }

    public Date getSubjectDateend() {
        return subjectDateend;
    }

    public void setSubjectDateend(Date value) {
        this.subjectDateend = value;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String value) {
        this.subjectCode = value;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String value) {
        this.subjectDescription = value;
    }

    public Integer getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(Integer value) {
        this.subjectStatus = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Date getSubjectDatecreate() {
        return subjectDatecreate;
    }

    public void setSubjectDatecreate(Date value) {
        this.subjectDatecreate = value;
    }

    public Integer getLastProguserId() {
        return lastProguserId;
    }

    public void setLastProguserId(Integer value) {
        this.lastProguserId = value;
    }

    public Date getSubjectDatemodify() {
        return subjectDatemodify;
    }

    public void setSubjectDatemodify(Date value) {
        this.subjectDatemodify = value;
    }

    public String getSubjectRemark() {
        return subjectRemark;
    }

    public void setSubjectRemark(String value) {
        this.subjectRemark = value;
    }

    public Integer getRootSubjectId() {
        return rootSubjectId;
    }

    public void setRootSubjectId(Integer value) {
        this.rootSubjectId = value;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}

