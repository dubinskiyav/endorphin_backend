package biz.gelicon.core.components.core.document;

import biz.gelicon.core.annotations.TableDescription;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 03.06.2021 13:44 */
@Table(
    name = "document",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"document_name"})
    }
)
@TableDescription("Тип документа")
public class Document {
    public static final int DOCUUMENT_FOLDER = 50;
    public static final int DOCUUMENT_FOR_TEST = 97;

    @Id
    @Column(name = "document_id",nullable = false)
    public Integer documentId;

    @Column(name = "document_name", nullable = false)
    @Size(max = 50, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String documentName;

    @Column(name = "document_shortname", nullable = true)
    @Size(max = 10, message = "Поле \"Краткое наименование\" должно содержать не более {max} символов")
    private String documentShortname;

    @Column(name = "document_historyflag", nullable = false)
    @NotNull(message = "Поле \"Флаг истории\" не может быть пустым")
    private Integer documentHistoryflag;

    @Column(name = "document_lockflag", nullable = false)
    @NotNull(message = "Поле \"Флаг блокировки\" не может быть пустым")
    private Integer documentLockflag;

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

    public String getDocumentShortname() {
        return documentShortname;
    }

    public void setDocumentShortname(String value) {
        this.documentShortname = value;
    }

    public Integer getDocumentHistoryflag() {
        return documentHistoryflag;
    }

    public void setDocumentHistoryflag(Integer value) {
        this.documentHistoryflag = value;
    }

    public Integer getDocumentLockflag() {
        return documentLockflag;
    }

    public void setDocumentLockflag(Integer value) {
        this.documentLockflag = value;
    }


    public Document() {}

    public Document(
            Integer documentId,
            String documentName,
            String documentShortname,
            Integer documentHistoryflag,
            Integer documentLockflag) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentShortname = documentShortname;
        this.documentHistoryflag = documentHistoryflag;
        this.documentLockflag = documentLockflag;
    }
}

