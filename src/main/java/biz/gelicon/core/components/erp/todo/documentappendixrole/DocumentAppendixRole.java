package biz.gelicon.core.components.erp.todo.documentappendixrole;

import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.capclass.CapClass;
import biz.gelicon.core.components.core.document.Document;
import biz.gelicon.core.components.core.sqlaction.SqlAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/* Сущность сгенерирована 13.05.2022 11:23 */
@Table(
    name = "documentappendixrole",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accessrole_id", "appendixtype_id", "document_id", "sqlaction_id"})
    }
)
public class DocumentAppendixRole {

    @Id
    @Column(name = "documentappendixrole_id",nullable = false)
    public Integer documentAppendixRoleId;

    @ManyToOne(targetEntity = AccessRole.class)
    @ColumnDescription("Роль ИД")
    @Column(name = "accessrole_id", nullable = false)
    @NotNull(message = "Поле \"Роль\" не может быть пустым")
    private Integer accessroleId;

    @ManyToOne(targetEntity = CapClass.class)
    @ColumnDescription("Тип электронного материала ИД")
    @Column(name = "appendixtype_id", nullable = false)
    @NotNull(message = "Поле \"Тип электронного материала\" не может быть пустым")
    private Integer appendixtypeId;

    @ManyToOne(targetEntity = Document.class)
    @ColumnDescription("Тип документа ИД")
    @Column(name = "document_id", nullable = false)
    @NotNull(message = "Поле \"Тип документа\" не может быть пустым")
    private Integer documentId;

    @ManyToOne(targetEntity = SqlAction.class)
    @ColumnDescription("Действие ИД")
    @Column(name = "sqlaction_id", nullable = false)
    @NotNull(message = "Поле \"Действие\" не может быть пустым")
    private Integer sqlactionId;

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


    public DocumentAppendixRole() {}

    public DocumentAppendixRole(
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
}

