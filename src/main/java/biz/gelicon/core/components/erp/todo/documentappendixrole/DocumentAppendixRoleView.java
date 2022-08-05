package biz.gelicon.core.components.erp.todo.documentappendixrole;

import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRole;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 13.05.2022 11:23 */
@Schema(description = "Представление объекта \"Права на ЭМ документа\"")
public class DocumentAppendixRoleView {

    @Schema(description = "Идентификатор \"Права на ЭМ документа\"")
    @Column(name="documentappendixrole_id")
    public Integer documentAppendixRoleId;

    @Schema(description = "Роль ИД")
    @Column(name="accessrole_id")
    private Integer accessroleId;

    @Schema(description = "Тип электронного материала ИД")
    @Column(name="appendixtype_id")
    private Integer appendixtypeId;

    @Schema(description = "Тип документа ИД")
    @Column(name="document_id")
    private Integer documentId;

    @Schema(description = "Действие ИД")
    @Column(name="sqlaction_id")
    private Integer sqlactionId;

    @Column(name = "capclass_name", table = "capclass")
    private String capClassName;

    @Column(name = "document_name", table = "document")
    private String documentName;

    @Column(name = "accessrole_name", table = "accessrole")
    private String accessRoleName;

    @Column(name = "sqlaction_note", table = "sqlaction")
    private String sqlActionNote;


    public DocumentAppendixRoleView() {}

    public DocumentAppendixRoleView(
            Integer documentAppendixRoleId,
            Integer accessroleId,
            Integer appendixtypeId,
            Integer documentId,
            Integer sqlactionId) {
        this.documentAppendixRoleId = documentAppendixRoleId;
        this.accessroleId = accessroleId;
        this.appendixtypeId = appendixtypeId;
        this.documentId = documentId;
        this.sqlactionId = sqlactionId;
    }

    public DocumentAppendixRoleView(DocumentAppendixRole entity) {
        this.documentAppendixRoleId = entity.getDocumentAppendixRoleId();
        this.accessroleId = entity.getAccessroleId();
        this.appendixtypeId = entity.getAppendixtypeId();
        this.documentId = entity.getDocumentId();
        this.sqlactionId = entity.getSqlactionId();
    }

    public Integer getDocumentAppendixRoleId() {
        return documentAppendixRoleId;
    }

    public void setDocumentAppendixRoleId(Integer value) {
        this.documentAppendixRoleId = value;
    }

    public Integer getAccessroleId() {
        return accessroleId;
    }

    public void setAccessroleId(Integer value) {
        this.accessroleId = value;
    }

    public Integer getAppendixtypeId() {
        return appendixtypeId;
    }

    public void setAppendixtypeId(Integer value) {
        this.appendixtypeId = value;
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


}

