package biz.gelicon.core.components.erp.todo.documentagreement;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;

/* Объект сгенерирован 08.07.2021 12:03 */
@Schema(description = "Объект \"Шаблон согласования документа\"")
public class DocumentAgreementDTO {

    @Id
    @Schema(description = "Идентификатор \"Шаблон согласования документа\"")
    private Integer documentAgreementId;

    @Schema(description = "Тип документа ИД")
    private Integer documentId;

    @Schema(description = "Номер")
    private Integer documentAgreementNumber;

    @Schema(description = "Наименование")
    private String documentAgreementName;

    @Schema(description = "Уровень визирования")
    private Integer documentAgreementLevel;

    @Schema(description = "Обязательность визы")
    private Integer documentAgreementRequired;

    @Schema(description = "Срок согласования (дни)")
    private Integer documentAgreementDays;

    @Schema(description = "Срок согласования (часы)")
    private Integer documentAgreementHours;


    public DocumentAgreementDTO() {}

    public DocumentAgreementDTO(
            Integer documentAgreementId,
            Integer documentId,
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

    public DocumentAgreementDTO(DocumentAgreement entity) {
        this.documentAgreementId = entity.getDocumentAgreementId();
        this.documentId = entity.getDocumentId();
        this.documentAgreementNumber = entity.getDocumentAgreementNumber();
        this.documentAgreementName = entity.getDocumentAgreementName();
        this.documentAgreementLevel = entity.getDocumentAgreementLevel();
        this.documentAgreementRequired = entity.getDocumentAgreementRequired();
        this.documentAgreementDays = entity.getDocumentAgreementDays();
        this.documentAgreementHours = entity.getDocumentAgreementHours();
    }

    public DocumentAgreement toEntity() {
        return toEntity(new DocumentAgreement());
    }

    public DocumentAgreement toEntity(DocumentAgreement entity) {
        entity.setDocumentAgreementId(this.documentAgreementId);
        entity.setDocumentId(this.documentId);
        entity.setDocumentAgreementNumber(this.documentAgreementNumber);
        entity.setDocumentAgreementName(this.documentAgreementName);
        entity.setDocumentAgreementLevel(this.documentAgreementLevel);
        entity.setDocumentAgreementRequired(this.documentAgreementRequired);
        entity.setDocumentAgreementDays(this.documentAgreementDays);
        entity.setDocumentAgreementHours(this.documentAgreementHours);
        return entity;
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

