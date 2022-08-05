package biz.gelicon.core.components.core.subject;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 20.04.2021 10:04 */
@Schema(description = "Объект \"Объект аналитики\"")
public class SubjectDTO {

    @Id
    @Schema(description = "Идентификатор \"Объект аналитики\"")
    private Integer subjectId;

    @Schema(description = "cluster_id")
    private Integer clusterId;

    @Schema(description = "subjecttype_id")
    private Integer subjectTypeId;

    @Schema(description = "subject_name")
    private String subjectName;

    @Schema(description = "subject_datebeg")
    private Date subjectDatebeg;

    @Schema(description = "subject_dateend")
    private Date subjectDateend;

    @Schema(description = "subject_code")
    private String subjectCode;

    @Schema(description = "subject_description")
    private String subjectDescription;

    @Schema(description = "subject_status")
    private Integer subjectStatus;

    @Schema(description = "proguser_id")
    private Integer proguserId;

    @Schema(description = "subject_datecreate")
    private Date subjectDatecreate;

    @Schema(description = "lastproguser_id")
    private Integer lastProguserId;

    @Schema(description = "subject_datemodify")
    private Date subjectDatemodify;

    @Schema(description = "subject_remark")
    private String subjectRemark;

    @Schema(description = "rootsubject_id")
    private Integer rootSubjectId;

    @Schema(description = "parent_id")
    private Integer parentId;

    public SubjectDTO() {}

    public SubjectDTO(
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
    }

    public SubjectDTO(Subject entity) {
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
    }

    public Subject toEntity() {
        return toEntity(new Subject());
    }

    public Subject toEntity(Subject entity) {
        entity.setSubjectId(this.subjectId);
        entity.setClusterId(this.clusterId);
        entity.setSubjectTypeId(this.subjectTypeId);
        entity.setSubjectName(this.subjectName);
        entity.setSubjectDatebeg(this.subjectDatebeg);
        entity.setSubjectDateend(this.subjectDateend);
        entity.setSubjectCode(this.subjectCode);
        entity.setSubjectDescription(this.subjectDescription);
        entity.setSubjectStatus(this.subjectStatus);
        entity.setProguserId(this.proguserId);
        entity.setSubjectDatecreate(this.subjectDatecreate);
        entity.setLastProguserId(this.lastProguserId);
        entity.setSubjectDatemodify(this.subjectDatemodify);
        entity.setSubjectRemark(this.subjectRemark);
        entity.setRootSubjectId(this.rootSubjectId);
        entity.setParentId(this.parentId);
        return entity;
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


}

