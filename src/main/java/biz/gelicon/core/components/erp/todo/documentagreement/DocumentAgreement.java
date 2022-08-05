package biz.gelicon.core.components.erp.todo.documentagreement;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.document.Document;
import biz.gelicon.core.components.erp.todo.documentrealagree.DocumentRealAgree;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/* Сущность сгенерирована 08.07.2021 12:03 */
@Table(
    name = "documentagreement",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"document_id", "documentagreement_number"})
    }
)
@TableDescription("Шаблон согласования документа")
public class DocumentAgreement {

    @Id
    @Column(name = "documentagreement_id",nullable = false)
    public Integer documentAgreementId;

    @ManyToOne(targetEntity = Document.class)
    @Column(name = "document_id", nullable = false)
    @NotNull(message = "Поле \"Тип документа ИД\" не может быть пустым")
    private Integer documentId;

    @Column(name = "documentagreement_number", nullable = false)
    @NotNull(message = "Поле \"Номер\" не может быть пустым")
    private Integer documentAgreementNumber;

    @Column(name = "documentagreement_name", nullable = false)
    @Size(max = 50, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String documentAgreementName;

    @Column(name = "documentagreement_level", nullable = false)
    @NotNull(message = "Поле \"Уровень визирования\" не может быть пустым")
    private Integer documentAgreementLevel;

    @Column(name = "documentagreement_required", nullable = false)
    @NotNull(message = "Поле \"Обязательность визы\" не может быть пустым")
    private Integer documentAgreementRequired;

    @Column(name = "documentagreement_days", nullable = true)
    private Integer documentAgreementDays;

    @Column(name = "documentagreement_hours", nullable = true)
    private Integer documentAgreementHours;

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


    public DocumentAgreement() {}

    public DocumentAgreement(
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

    public DocumentRealAgree toDocumentRealAgree() {
        return new DocumentRealAgree(null,null,null,null,null,
                this.documentAgreementNumber,null,null,
                documentAgreementName,documentAgreementLevel,documentAgreementRequired,
                null,null,null);
    }
}

