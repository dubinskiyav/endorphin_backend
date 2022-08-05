package biz.gelicon.core.components.core.document;

import biz.gelicon.core.deltads.MemoryDataSet;
import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRoleDTO;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitDTO;
import biz.gelicon.core.components.erp.todo.uniquetype.UniqueType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;

/* Объект сгенерирован 03.06.2021 13:44 */
@Schema(description = "Объект \"Тип документа\"")
public class DocumentDTO {

    @Id
    @Schema(description = "Идентификатор \"Тип документа\"")
    private Integer documentId;

    @Schema(description = "Наименование")
    private String documentName;

    @Schema(description = "Тип уникальности")
    public Integer uniqueTypeId;

    @Schema(description = "Наименование \"Тип уникальности\"")
    private String uniqueTypeName;

    @Schema(description = "Модель статусов документа")
    private MemoryDataSet<DocumentTransitDTO> transits;

    @Schema(description = "Права на ЭМ")
    private MemoryDataSet<DocumentAppendixRoleDTO> appendixRoles;

    public DocumentDTO() {}

    public DocumentDTO(
            Integer documentId,
            String documentName) {
        this.documentId = documentId;
        this.documentName = documentName;
    }

    public DocumentDTO(Document entity) {
        this.documentId = entity.getDocumentId();
        this.documentName = entity.getDocumentName();
    }

    public Document toEntity() {
        return toEntity(new Document());
    }

    public Document toEntity(Document entity) {
        entity.setDocumentId(this.documentId);
        entity.setDocumentName(this.documentName);
        return entity;
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

    public void setUniqueType(UniqueType ut) {
        if(ut==null) {
            this.uniqueTypeId = null;
            this.uniqueTypeName = null;
        } else {
            this.uniqueTypeId = ut.getUniqueTypeId();
            this.uniqueTypeName = ut.getUniqueTypeName();
        }
    }

    public MemoryDataSet<DocumentTransitDTO> getTransits() {
        return transits;
    }

    public void setTransits(MemoryDataSet<DocumentTransitDTO> transits) {
        this.transits = transits;
    }

    public MemoryDataSet<DocumentAppendixRoleDTO> getAppendixRoles() {
        return appendixRoles;
    }

    public void setAppendixRoles(MemoryDataSet<DocumentAppendixRoleDTO> appendixRoles) {
        this.appendixRoles = appendixRoles;
    }
}

