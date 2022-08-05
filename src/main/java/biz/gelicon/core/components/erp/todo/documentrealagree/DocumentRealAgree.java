package biz.gelicon.core.components.erp.todo.documentrealagree;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 08.07.2021 10:12 */
@Table(
    name = "documentrealagree",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"documentreal_id", "documentrealagree_number"})
    }
)
@TableDescription("Согласование документа")
public class DocumentRealAgree {

    @Id
    @Column(name = "documentrealagree_id",nullable = false)
    public Integer documentRealAgreeId;

    @ManyToOne(targetEntity = DocumentReal.class)
    @Column(name = "documentreal_id", nullable = false)
    @NotNull(message = "Поле \"Документ ИД\" не может быть пустым")
    private Integer documentRealId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = true)
    private Integer proguserId;

    @ManyToOne(targetEntity = DocumentRealAgree.class)
    @Column(name = "documentrealagreelink_id", nullable = true)
    private Integer documentRealaAreeLinkId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "progusercreate_id", nullable = true)
    private Integer proguserCreateId;

    @Column(name = "documentrealagree_number", nullable = false)
    @NotNull(message = "Поле \"Номер\" не может быть пустым")
    private Integer documentRealAgreeNumber;

    @Column(name = "documentrealagree_date", nullable = true)
    private Date documentRealAgreeDate;

    @Column(name = "documentrealagree_remark", nullable = true)
    @Size(max = 255, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String documentRealAgreeRemark;

    @Column(name = "documentrealagree_name", nullable = false)
    @Size(max = 50, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String documentRealAgreeName;

    @Column(name = "documentrealagree_level", nullable = false)
    @NotNull(message = "Поле \"Уровень визирования\" не может быть пустым")
    private Integer documentRealAgreeLevel;

    @Column(name = "documentrealagree_required", nullable = false)
    @NotNull(message = "Поле \"Обязательность визы\" не может быть пустым")
    private Integer documentRealAgreeRequired;

    @Column(name = "documentrealagree_status", nullable = true)
    private Integer documentRealAgreeStatus;

    @Column(name = "documentrealagree_notagree", nullable = true)
    private Integer documentRealAgreeNotagree;

    @Column(name = "documentrealagree_datereq", nullable = true)
    private Date documentRealAgreeDatereq;

    public Integer getDocumentRealAgreeId() {
        return documentRealAgreeId;
    }

    public void setDocumentRealAgreeId(Integer value) {
        this.documentRealAgreeId = value;
    }

    public Integer getDocumentRealId() {
        return documentRealId;
    }

    public void setDocumentRealId(Integer value) {
        this.documentRealId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Integer getDocumentRealaAreeLinkId() {
        return documentRealaAreeLinkId;
    }

    public void setDocumentRealaAreeLinkId(Integer value) {
        this.documentRealaAreeLinkId = value;
    }

    public Integer getProguserCreateId() {
        return proguserCreateId;
    }

    public void setProguserCreateId(Integer value) {
        this.proguserCreateId = value;
    }

    public Integer getDocumentRealAgreeNumber() {
        return documentRealAgreeNumber;
    }

    public void setDocumentRealAgreeNumber(Integer value) {
        this.documentRealAgreeNumber = value;
    }

    public Date getDocumentRealAgreeDate() {
        return documentRealAgreeDate;
    }

    public void setDocumentRealAgreeDate(Date value) {
        this.documentRealAgreeDate = value;
    }

    public String getDocumentRealAgreeRemark() {
        return documentRealAgreeRemark;
    }

    public void setDocumentRealAgreeRemark(String value) {
        this.documentRealAgreeRemark = value;
    }

    public String getDocumentRealAgreeName() {
        return documentRealAgreeName;
    }

    public void setDocumentRealAgreeName(String value) {
        this.documentRealAgreeName = value;
    }

    public Integer getDocumentRealAgreeLevel() {
        return documentRealAgreeLevel;
    }

    public void setDocumentRealAgreeLevel(Integer value) {
        this.documentRealAgreeLevel = value;
    }

    public Integer getDocumentRealAgreeRequired() {
        return documentRealAgreeRequired;
    }

    public void setDocumentRealAgreeRequired(Integer value) {
        this.documentRealAgreeRequired = value;
    }

    public Integer getDocumentRealAgreeStatus() {
        return documentRealAgreeStatus;
    }

    public void setDocumentRealAgreeStatus(Integer value) {
        this.documentRealAgreeStatus = value;
    }

    public Integer getDocumentRealAgreeNotagree() {
        return documentRealAgreeNotagree;
    }

    public void setDocumentRealAgreeNotagree(Integer value) {
        this.documentRealAgreeNotagree = value;
    }

    public Date getDocumentRealAgreeDatereq() {
        return documentRealAgreeDatereq;
    }

    public void setDocumentRealAgreeDatereq(Date value) {
        this.documentRealAgreeDatereq = value;
    }


    public DocumentRealAgree() {}

    public DocumentRealAgree(
            Integer documentRealAgreeId,
            Integer documentRealId,
            Integer proguserId,
            Integer documentRealAreeLinkId,
            Integer proguserCreateId,
            Integer documentRealAgreeNumber,
            Date documentRealAgreeDate,
            String documentRealAgreeRemark,
            String documentRealAgreeName,
            Integer documentRealAgreeLevel,
            Integer documentRealAgreeRequired,
            Integer documentRealAgreeStatus,
            Integer documentRealAgreeNotagree,
            Date documentRealAgreeDatereq) {
        this.documentRealAgreeId = documentRealAgreeId;
        this.documentRealId = documentRealId;
        this.proguserId = proguserId;
        this.documentRealaAreeLinkId = documentRealAreeLinkId;
        this.proguserCreateId = proguserCreateId;
        this.documentRealAgreeNumber = documentRealAgreeNumber;
        this.documentRealAgreeDate = documentRealAgreeDate;
        this.documentRealAgreeRemark = documentRealAgreeRemark;
        this.documentRealAgreeName = documentRealAgreeName;
        this.documentRealAgreeLevel = documentRealAgreeLevel;
        this.documentRealAgreeRequired = documentRealAgreeRequired;
        this.documentRealAgreeStatus = documentRealAgreeStatus;
        this.documentRealAgreeNotagree = documentRealAgreeNotagree;
        this.documentRealAgreeDatereq = documentRealAgreeDatereq;
    }
}

