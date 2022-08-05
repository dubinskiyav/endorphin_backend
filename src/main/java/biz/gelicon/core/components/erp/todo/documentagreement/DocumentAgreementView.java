package biz.gelicon.core.components.erp.todo.documentagreement;

import biz.gelicon.core.components.erp.todo.documentagreement.DocumentAgreement;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 08.07.2021 12:03 */
@Schema(description = "Представление объекта \"Шаблон согласования документа\"")
public class DocumentAgreementView {

    @Schema(description = "Идентификатор \"Шаблон согласования документа\"")
    @Column(name="documentagreement_id")
    public Integer documentAgreementId;

    @Schema(description = "Тип документа ИД")
    @Column(name="document_id")
    private Integer documentId;

    @Schema(description = "Номер")
    @Column(name="documentagreement_number")
    private Integer documentAgreementNumber;

    @Schema(description = "Наименование")
    @Column(name="documentagreement_name")
    private String documentAgreementName;

    @Schema(description = "Уровень визирования")
    @Column(name="documentagreement_level")
    private Integer documentAgreementLevel;

    @Schema(description = "Обязательность визы")
    @Column(name="documentagreement_required")
    private Integer documentAgreementRequired;

    @Schema(description = "Срок согласования (дни)")
    @Column(name="documentagreement_days")
    private Integer documentAgreementDays;

    @Schema(description = "Срок согласования (часы)")
    @Column(name="documentagreement_hours")
    private Integer documentAgreementHours;


    public DocumentAgreementView() {}

    public DocumentAgreementView(
            Integer documentAgreementId,
            Integer documentId,
            Integer subjectwgId,
            Integer documentAgreementNumber,
            String documentAgreementName,
            Integer documentAgreementLevel,
            Integer documentAgreementRequired,
            Integer documentAgreementDays,
            Integer documentAgreementHours) {
        this.documentAgreementId = documentAgreementId;
        this.documentId = documentId;
        this.documentAgreementNumber = documentAgreementNumber;
        this.documentAgreementName = documentAgreementName;
        this.documentAgreementLevel = documentAgreementLevel;
        this.documentAgreementRequired = documentAgreementRequired;
        this.documentAgreementDays = documentAgreementDays;
        this.documentAgreementHours = documentAgreementHours;
    }

    public DocumentAgreementView(DocumentAgreement entity) {
        this.documentAgreementId = entity.getDocumentAgreementId();
        this.documentId = entity.getDocumentId();
        this.documentAgreementNumber = entity.getDocumentAgreementNumber();
        this.documentAgreementName = entity.getDocumentAgreementName();
        this.documentAgreementLevel = entity.getDocumentAgreementLevel();
        this.documentAgreementRequired = entity.getDocumentAgreementRequired();
        this.documentAgreementDays = entity.getDocumentAgreementDays();
        this.documentAgreementHours = entity.getDocumentAgreementHours();
    }

    public Integer getDocumentAgreementId() {
        return documentAgreementId;
    }

    public void setDocumentAgreementId(Integer value) {
        this.documentAgreementId = value;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer value) {
        this.documentId = value;
    }

    public Integer getDocumentAgreementNumber() {
        return documentAgreementNumber;
    }

    public void setDocumentAgreementNumber(Integer value) {
        this.documentAgreementNumber = value;
    }

    public String getDocumentAgreementName() {
        return documentAgreementName;
    }

    public void setDocumentAgreementName(String value) {
        this.documentAgreementName = value;
    }

    public Integer getDocumentAgreementLevel() {
        return documentAgreementLevel;
    }

    public void setDocumentAgreementLevel(Integer value) {
        this.documentAgreementLevel = value;
    }

    public Integer getDocumentAgreementRequired() {
        return documentAgreementRequired;
    }

    public void setDocumentAgreementRequired(Integer value) {
        this.documentAgreementRequired = value;
    }

    public Integer getDocumentAgreementDays() {
        return documentAgreementDays;
    }

    public void setDocumentAgreementDays(Integer value) {
        this.documentAgreementDays = value;
    }

    public Integer getDocumentAgreementHours() {
        return documentAgreementHours;
    }

    public void setDocumentAgreementHours(Integer value) {
        this.documentAgreementHours = value;
    }


}

