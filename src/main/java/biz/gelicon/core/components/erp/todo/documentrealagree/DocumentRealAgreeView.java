package biz.gelicon.core.components.erp.todo.documentrealagree;

import biz.gelicon.core.components.erp.todo.documentrealagree.DocumentRealAgree;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 10:12 */
@Schema(description = "Представление объекта \"Согласование документа\"")
public class DocumentRealAgreeView {

    @Schema(description = "Идентификатор \"Согласование документа\"")
    @Column(name="documentrealagree_id")
    public Integer documentRealAgreeId;

    @Schema(description = "Документ ИД")
    @Column(name="documentreal_id")
    private Integer documentRealId;

    @Schema(description = "Пользователь ИД")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Главная позиция согласования документа ИД")
    @Column(name="documentrealagreelink_id")
    private Integer documentRealAgreeLinkId;

    @Schema(description = "Пользователь создатель ИД")
    @Column(name="progusercreate_id")
    private Integer proguserCreateId;

    @Schema(description = "Номер")
    @Column(name="documentrealagree_number")
    private Integer documentRealAgreeNumber;

    @Schema(description = "Дата")
    @Column(name="documentrealagree_date")
    private Date documentRealAgreeDate;

    @Schema(description = "Примечание")
    @Column(name="documentrealagree_remark")
    private String documentRealAgreeRemark;

    @Schema(description = "Элемент штатного расписания ИД")
    @Column(name="subjectwg_id")
    private Integer subjectwgId;

    @Schema(description = "Наименование")
    @Column(name="documentrealagree_name")
    private String documentRealAgreeName;

    @Schema(description = "Уровень визирования")
    @Column(name="documentrealagree_level")
    private Integer documentRealAgreeLevel;

    @Schema(description = "Обязательность визы")
    @Column(name="documentrealagree_required")
    private Integer documentRealAgreeRequired;

    @Schema(description = "Статус")
    @Column(name="documentrealagree_status")
    private Integer documentRealAgreeStatus;

    @Schema(description = "Отказ согласования")
    @Column(name="documentrealagree_notagree")
    private Integer documentRealAgreeNotagree;

    @Schema(description = "Срок согласования")
    @Column(name="documentrealagree_datereq")
    private Date documentRealAgreeDatereq;


    public DocumentRealAgreeView() {}

    public DocumentRealAgreeView(
            Integer documentRealAgreeId,
            Integer documentRealId,
            Integer proguserId,
            Integer documentRealAgreeLinkId,
            Integer proguserCreateId,
            Integer documentRealAgreeNumber,
            Date documentRealAgreeDate,
            String documentRealAgreeRemark,
            Integer subjectwgId,
            String documentRealAgreeName,
            Integer documentRealAgreeLevel,
            Integer documentRealAgreeRequired,
            Integer documentRealAgreeStatus,
            Integer documentRealAgreeNotagree,
            Date documentRealAgreeDatereq) {
        this.documentRealAgreeId = documentRealAgreeId;
        this.documentRealId = documentRealId;
        this.proguserId = proguserId;
        this.documentRealAgreeLinkId = documentRealAgreeLinkId;
        this.proguserCreateId = proguserCreateId;
        this.documentRealAgreeNumber = documentRealAgreeNumber;
        this.documentRealAgreeDate = documentRealAgreeDate;
        this.documentRealAgreeRemark = documentRealAgreeRemark;
        this.subjectwgId = subjectwgId;
        this.documentRealAgreeName = documentRealAgreeName;
        this.documentRealAgreeLevel = documentRealAgreeLevel;
        this.documentRealAgreeRequired = documentRealAgreeRequired;
        this.documentRealAgreeStatus = documentRealAgreeStatus;
        this.documentRealAgreeNotagree = documentRealAgreeNotagree;
        this.documentRealAgreeDatereq = documentRealAgreeDatereq;
    }

    public DocumentRealAgreeView(DocumentRealAgree entity) {
        this.documentRealAgreeId = entity.getDocumentRealAgreeId();
        this.documentRealId = entity.getDocumentRealId();
        this.proguserId = entity.getProguserId();
        this.documentRealAgreeLinkId = entity.getDocumentRealaAreeLinkId();
        this.proguserCreateId = entity.getProguserCreateId();
        this.documentRealAgreeNumber = entity.getDocumentRealAgreeNumber();
        this.documentRealAgreeDate = entity.getDocumentRealAgreeDate();
        this.documentRealAgreeRemark = entity.getDocumentRealAgreeRemark();
        this.documentRealAgreeName = entity.getDocumentRealAgreeName();
        this.documentRealAgreeLevel = entity.getDocumentRealAgreeLevel();
        this.documentRealAgreeRequired = entity.getDocumentRealAgreeRequired();
        this.documentRealAgreeStatus = entity.getDocumentRealAgreeStatus();
        this.documentRealAgreeNotagree = entity.getDocumentRealAgreeNotagree();
        this.documentRealAgreeDatereq = entity.getDocumentRealAgreeDatereq();
    }

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

    public Integer getDocumentRealAgreeLinkId() {
        return documentRealAgreeLinkId;
    }

    public void setDocumentRealAgreeLinkId(Integer value) {
        this.documentRealAgreeLinkId = value;
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

    public Integer getSubjectwgId() {
        return subjectwgId;
    }

    public void setSubjectwgId(Integer value) {
        this.subjectwgId = value;
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


}

