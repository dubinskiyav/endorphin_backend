package biz.gelicon.core.components.erp.todo.documentrealagree;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 10:12 */
@Schema(description = "Объект \"Согласование документа\"")
public class DocumentRealAgreeDTO {

    @Id
    @Schema(description = "Идентификатор \"Согласование документа\"")
    private Integer documentRealAgreeId;

    @Schema(description = "Документ ИД")
    private Integer documentRealId;

    @Schema(description = "Пользователь ИД")
    private Integer proguserId;

    @Schema(description = "Главная позиция согласования документа ИД")
    private Integer documentRealAgreeLinkId;

    @Schema(description = "Пользователь создатель ИД")
    private Integer proguserCreateId;

    @Schema(description = "Номер")
    private Integer documentRealAgreeNumber;

    @Schema(description = "Дата")
    private Date documentRealAgreeDate;

    @Schema(description = "Примечание")
    private String documentRealAgreeRemark;

    @Schema(description = "Элемент штатного расписания ИД")
    private Integer subjectwgId;

    @Schema(description = "Наименование")
    private String documentRealAgreeName;

    @Schema(description = "Уровень визирования")
    private Integer documentRealAgreeLevel;

    @Schema(description = "Обязательность визы")
    private Integer documentRealAgreeRequired;

    @Schema(description = "Статус")
    private Integer documentRealAgreeStatus;

    @Schema(description = "Отказ согласования")
    private Integer documentRealAgreeNotagree;

    @Schema(description = "Срок согласования")
    private Date documentRealAgreeDatereq;


    public DocumentRealAgreeDTO() {}

    public DocumentRealAgreeDTO(
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

    public DocumentRealAgreeDTO(DocumentRealAgree entity) {
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

    public DocumentRealAgree toEntity() {
        return toEntity(new DocumentRealAgree());
    }

    public DocumentRealAgree toEntity(DocumentRealAgree entity) {
        entity.setDocumentRealAgreeId(this.documentRealAgreeId);
        entity.setDocumentRealId(this.documentRealId);
        entity.setProguserId(this.proguserId);
        entity.setDocumentRealaAreeLinkId(this.documentRealAgreeLinkId);
        entity.setProguserCreateId(this.proguserCreateId);
        entity.setDocumentRealAgreeNumber(this.documentRealAgreeNumber);
        entity.setDocumentRealAgreeDate(this.documentRealAgreeDate);
        entity.setDocumentRealAgreeRemark(this.documentRealAgreeRemark);
        entity.setDocumentRealAgreeName(this.documentRealAgreeName);
        entity.setDocumentRealAgreeLevel(this.documentRealAgreeLevel);
        entity.setDocumentRealAgreeRequired(this.documentRealAgreeRequired);
        entity.setDocumentRealAgreeStatus(this.documentRealAgreeStatus);
        entity.setDocumentRealAgreeNotagree(this.documentRealAgreeNotagree);
        entity.setDocumentRealAgreeDatereq(this.documentRealAgreeDatereq);
        return entity;
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

