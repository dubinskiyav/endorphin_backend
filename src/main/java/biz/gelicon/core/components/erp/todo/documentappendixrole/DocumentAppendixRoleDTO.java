package biz.gelicon.core.components.erp.todo.documentappendixrole;

import biz.gelicon.core.deltads.RecordInfo;
import biz.gelicon.core.dto.SelectDisplayData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;

/* Объект сгенерирован 13.05.2022 11:23 */
@Schema(description = "Объект \"Права на ЭМ документа\"")
public class DocumentAppendixRoleDTO  implements RecordInfo {

    @Id
    @Schema(description = "Идентификатор \"Права на ЭМ документа\"")
    private Integer documentAppendixRoleId;

    @Schema(description = "Уникальный номер позиции в документа")
    private Integer posNumber;

    @Schema(description = "Роль")
    private SelectDisplayData<Integer> accessRole;

    @Schema(description = "Тип электронного материала")
    private SelectDisplayData<Integer> appendixType;

    @Schema(description = "Тип документа ИД")
    private Integer documentId;

    @Schema(description = "Действие ИД")
    private Integer sqlactionId;


    public DocumentAppendixRoleDTO() {}

    public DocumentAppendixRoleDTO(
            Integer documentAppendixRoleId,
            Integer documentId,
            Integer sqlactionId) {
        this.documentAppendixRoleId = documentAppendixRoleId;
        this.documentId = documentId;
        this.sqlactionId = sqlactionId;
    }

    public DocumentAppendixRoleDTO(DocumentAppendixRole entity) {
        this.documentAppendixRoleId = entity.getDocumentAppendixRoleId();
        this.documentId = entity.getDocumentId();
        this.sqlactionId = entity.getSqlactionId();
        this.accessRole=new SelectDisplayData<>(entity.getAccessroleId(),null);
        this.appendixType=new SelectDisplayData<>(entity.getAppendixtypeId(),null);
    }

    public DocumentAppendixRole toEntity() {
        return toEntity(new DocumentAppendixRole());
    }

    public DocumentAppendixRole toEntity(DocumentAppendixRole entity) {
        entity.setDocumentAppendixRoleId(this.documentAppendixRoleId);
        entity.setDocumentId(this.documentId);
        entity.setSqlactionId(this.sqlactionId);
        if(this.accessRole!=null) {
            entity.setAccessroleId(this.accessRole.getValue());
        }
        if(this.appendixType!=null) {
            entity.setAppendixtypeId(this.appendixType.getValue());
        }
        return entity;
    }

    public Integer getDocumentAppendixRoleId() {
        return documentAppendixRoleId;
    }

    public void setDocumentAppendixRoleId(Integer value) {
        this.documentAppendixRoleId = value;
    }

    public SelectDisplayData<Integer> getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(SelectDisplayData<Integer> accessRole) {
        this.accessRole = accessRole;
    }

    public SelectDisplayData<Integer> getAppendixType() {
        return appendixType;
    }

    public void setAppendixType(SelectDisplayData<Integer> appendixType) {
        this.appendixType = appendixType;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer value) {
        this.documentId = value;
    }

    public Integer getSqlactionId() {
        return sqlactionId;
    }

    public void setSqlactionId(Integer value) {
        this.sqlactionId = value;
    }

    public Integer getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(Integer posNumber) {
        this.posNumber = posNumber;
    }

    @JsonIgnore
    @Override
    public Integer getPositionNumber() {
        return posNumber;
    }
}

