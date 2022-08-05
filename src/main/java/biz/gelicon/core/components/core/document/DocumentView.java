package biz.gelicon.core.components.core.document;

import biz.gelicon.core.components.core.document.Document;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 03.06.2021 13:44 */
@Schema(description = "Представление объекта \"Тип документа\"")
public class DocumentView {

    @Schema(description = "Идентификатор \"Тип документа\"")
    @Column(name="document_id")
    public Integer documentId;

    @Schema(description = "Наименование")
    @Column(name="document_name")
    private String documentName;

    @Schema(description = "Идентификатор \"Тип уникальности\"")
    @Column(name="uniquetype_id",table = "uniquetype")
    public Integer uniqueTypeId;

    @Schema(description = "Наименование \"Тип уникальности\"")
    @Column(name="uniquetype_name",table = "uniquetype")
    private String uniqueTypeName;

    public DocumentView() {}

    public DocumentView(
            Integer documentId,
            String documentName) {
        this.documentId = documentId;
        this.documentName = documentName;
    }

    public DocumentView(Document entity) {
        this.documentId = entity.getDocumentId();
        this.documentName = entity.getDocumentName();
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer value) {
        this.documentId = value;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String value) {
        this.documentName = value;
    }

    public Integer getUniqueTypeId() {
        return uniqueTypeId;
    }

    public void setUniqueTypeId(Integer uniqueTypeId) {
        this.uniqueTypeId = uniqueTypeId;
    }

    public String getUniqueTypeName() {
        return uniqueTypeName;
    }

    public void setUniqueTypeName(String uniqueTypeName) {
        this.uniqueTypeName = uniqueTypeName;
    }
}

