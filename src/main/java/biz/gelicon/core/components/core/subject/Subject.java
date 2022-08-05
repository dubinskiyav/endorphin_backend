package biz.gelicon.core.components.core.subject;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.clusterr.Clusterr;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.core.subjecttype.SubjectType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 19.04.2021 19:58 */
@Table(name = "subject")
@TableDescription("Объект аналитики")
public class Subject {
    public final static int SUBJECT_ROOT_ID = -1;

    @Id
    @Column(name = "subject_id",nullable = false)
    public Integer subjectId;

    @ManyToOne(targetEntity = Clusterr.class)
    @Column(name = "cluster_id", nullable = false)
    private Integer clusterId;

    @ManyToOne(targetEntity = SubjectType.class)
    @Column(name = "subjecttype_id", nullable = false)
    private Integer subjectTypeId;

    @Column(name = "subject_name", nullable = false)
    @Size(max = 255, message = "Поле \"Наименование объекта\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование объекта\" не может быть пустым")
    private String subjectName;

    @Column(name = "subject_datebeg", nullable = false)
    @NotNull(message = "Поле \"Дата открытия\" не может быть пустой")
    private Date subjectDatebeg;

    @Column(name = "subject_dateend", nullable = false)
    @NotNull(message = "Поле \"Дата закрытия\" не может быть пустой")
    private Date subjectDateend;

    @Column(name = "subject_code", nullable = false)
    @Size(max = 30, message = "Поле \"Код объекта\" должно содержать не более {max} символов")
    @NotNull(message = "Поле \"Код объекта\" не может быть пустым")
    private String subjectCode;

    @Column(name = "subject_description", nullable = true)
    @Size(max = 2000, message = "Поле \"Описание объекта\" должно содержать не более {max} символов")
    private String subjectDescription;

    @Column(name = "subject_status", nullable = false)
    @NotNull(message = "Поле \"Статус объекта\" не может быть пустым")
    private Integer subjectStatus;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    private Integer proguserId;

    @Column(name = "subject_datecreate", nullable = false)
    @NotNull(message = "Поле \"Дата создания\" не может быть пустой")
    private Date subjectDatecreate;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "lastproguser_id", nullable = false)
    private Integer lastProguserId;

    @Column(name = "subject_datemodify", nullable = false)
    @NotNull(message = "Поле \"Дата модификации\" не может быть пустой")
    private Date subjectDatemodify;

    @Column(name = "subject_remark", nullable = true)
    @Size(max = 255, message = "Поле \"Краткая характеристика\" должно содержать не более {max} символов")
    private String subjectRemark;

    @ManyToOne(targetEntity = Subject.class)
    @Column(name = "rootsubject_id", nullable = false)
    private Integer rootSubjectId;

    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public Integer getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(Integer subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Date getSubjectDatebeg() {
        return subjectDatebeg;
    }

    public void setSubjectDatebeg(Date subjectDatebeg) {
        this.subjectDatebeg = subjectDatebeg;
    }

    public Date getSubjectDateend() {
        return subjectDateend;
    }

    public void setSubjectDateend(Date subjectDateend) {
        this.subjectDateend = subjectDateend;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Integer getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(Integer subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer progUserId) {
        this.proguserId = progUserId;
    }

    public Date getSubjectDatecreate() {
        return subjectDatecreate;
    }

    public void setSubjectDatecreate(Date subjectDatecreate) {
        this.subjectDatecreate = subjectDatecreate;
    }

    public Integer getLastProguserId() {
        return lastProguserId;
    }

    public void setLastProguserId(Integer lastProgUserId) {
        this.lastProguserId = lastProgUserId;
    }

    public Date getSubjectDatemodify() {
        return subjectDatemodify;
    }

    public void setSubjectDatemodify(Date subjectDatemodify) {
        this.subjectDatemodify = subjectDatemodify;
    }

    public String getSubjectRemark() {
        return subjectRemark;
    }

    public void setSubjectRemark(String subjectRemark) {
        this.subjectRemark = subjectRemark;
    }

    public Integer getRootSubjectId() {
        return rootSubjectId;
    }

    public void setRootSubjectId(Integer subjectRootsubjectId) {
        this.rootSubjectId = subjectRootsubjectId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Subject() {}

    public Subject(
            Integer id,
            Integer clusterId,
            Integer subjecttypeId,
            String name,
            Date datebeg,
            Date dateend,
            String code,
            String description,
            Integer status,
            Integer proguserId,
            Date datecreate,
            Integer lastproguserId,
            Date datemodify,
            String remark,
            Integer rootsubjectId,
            Integer parentId) {
        this.subjectId = id;
        this.clusterId = clusterId;
        this.subjectTypeId = subjecttypeId;
        this.subjectName = name;
        this.subjectDatebeg = datebeg;
        this.subjectDateend = dateend;
        this.subjectCode = code;
        this.subjectDescription = description;
        this.subjectStatus = status;
        this.proguserId = proguserId;
        this.subjectDatecreate = datecreate;
        this.lastProguserId = lastproguserId;
        this.subjectDatemodify = datemodify;
        this.subjectRemark = remark;
        this.rootSubjectId = rootsubjectId;
        this.parentId = parentId;
    }
}

