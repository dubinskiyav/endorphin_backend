package biz.gelicon.core.components.erp.todo.documenttransit;

import biz.gelicon.core.annotations.Cast;
import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.document.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/* Сущность сгенерирована 18.06.2021 17:28 */
@Table(
    name = "documenttransit",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"document_id", "documenttransit_number"}),
        @UniqueConstraint(columnNames = {"document_id", "documenttransit_name"})
    }
)
@TableDescription("Статус типа документа")
public class DocumentTransit {

    @Id
    @Column(name = "documenttransit_id",nullable = false)
    public Integer documentTransitId;

    @ManyToOne(targetEntity = Document.class)
    @Column(name = "document_id", nullable = false)
    @NotNull(message = "Поле \"Тип документа ИД\" не может быть пустым")
    private Integer documentId;

    @Column(name = "documenttransit_number", nullable = false)
    @NotNull(message = "Поле \"Порядковый номер\" не может быть пустым")
    private Integer documentTransitNumber;

    @Column(name = "documenttransit_name", nullable = false)
    @Size(max = 50, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String documentTransitName;

    @Column(name = "documenttransit_level", nullable = false)
    @NotNull(message = "Поле \"Уровень статуса\" не может быть пустым")
    private Integer documentTransitLevel;

    @Column(name = "documenttransit_required", nullable = false)
    @NotNull(message = "Поле \"Обязательность\" не может быть пустым")
    private Integer documentTransitRequired;

    @Column(name = "documenttransit_candelete", nullable = false)
    @NotNull(message = "Поле \"Возможность удаления\" не может быть пустым")
    private Integer documentTransitCandelete;

    @Column(name = "documenttransit_useadmin", nullable = false)
    @NotNull(message = "Поле \"Необходимость статуса\" не может быть пустым")
    private Integer documentTransitUseadmin;

    @Column(name = "documenttransit_canedit", nullable = false)
    @NotNull(message = "Поле \"Возможность изменения\" не может быть пустым")
    private Integer documentTransitCanedit;

    @Column(name = "documenttransit_twicecheck", nullable = false)
    @NotNull(message = "Поле \"Запрет повторения\" не может быть пустым")
    private Integer documentTransitTwicecheck;

    @Column(name = "documenttransit_flaghistory", nullable = false)
    @NotNull(message = "Поле \"Логирование изменений\" не может быть пустым")
    private Integer documentTransitFlaghistory;

    @Column(name = "documenttransit_flagone", nullable = false)
    @NotNull(message = "Поле \"Флаг первого статуса\" не может быть пустым")
    private Integer documentTransitFlagone;

    @Column(name = "documenttransit_locksubj", nullable = false)
    @NotNull(message = "Поле \"Блокировка ОАУ документа\" не может быть пустым")
    private Integer documentTransitLocksubj;

    @Column(name = "documenttransit_agreeedit", nullable = false)
    @NotNull(message = "Поле \"Возможность изменения листа согласования\" не может быть пустым")
    private Integer documentTransitAgreeedit;

    @Column(name = "documenttransit_color", nullable = false)
    @NotNull(message = "Поле \"Цвет\" не может быть пустым")
    private Integer documentTransitColor;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @Cast(fromDatabase ="varchar(18)",toDatabase = Integer.class)
    @JoinColumn(name="accessrole_id", table="documenttransitrole")
    private List<String> accessRoles;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @Cast(fromDatabase ="varchar(18)",toDatabase = Integer.class)
    @JoinColumn(name="documenttransitparent_id", referencedColumnName = "documenttransitchild_id",table="documenttransitlink")
    private List<String> transitChildIds;

    public Integer getDocumentTransitId() {
        return documentTransitId;
    }

    public void setDocumentTransitId(Integer value) {
        this.documentTransitId = value;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer value) {
        this.documentId = value;
    }

    public Integer getDocumentTransitNumber() {
        return documentTransitNumber;
    }

    public void setDocumentTransitNumber(Integer value) {
        this.documentTransitNumber = value;
    }

    public String getDocumentTransitName() {
        return documentTransitName;
    }

    public void setDocumentTransitName(String value) {
        this.documentTransitName = value;
    }

    public Integer getDocumentTransitLevel() {
        return documentTransitLevel;
    }

    public void setDocumentTransitLevel(Integer value) {
        this.documentTransitLevel = value;
    }

    public Integer getDocumentTransitRequired() {
        return documentTransitRequired;
    }

    public void setDocumentTransitRequired(Integer value) {
        this.documentTransitRequired = value;
    }

    public Integer getDocumentTransitCandelete() {
        return documentTransitCandelete;
    }

    public void setDocumentTransitCandelete(Integer value) {
        this.documentTransitCandelete = value;
    }

    public Integer getDocumentTransitUseadmin() {
        return documentTransitUseadmin;
    }

    public void setDocumentTransitUseadmin(Integer value) {
        this.documentTransitUseadmin = value;
    }

    public Integer getDocumentTransitCanedit() {
        return documentTransitCanedit;
    }

    public void setDocumentTransitCanedit(Integer value) {
        this.documentTransitCanedit = value;
    }

    public Integer getDocumentTransitTwicecheck() {
        return documentTransitTwicecheck;
    }

    public void setDocumentTransitTwicecheck(Integer value) {
        this.documentTransitTwicecheck = value;
    }

    public Integer getDocumentTransitFlaghistory() {
        return documentTransitFlaghistory;
    }

    public void setDocumentTransitFlaghistory(Integer value) {
        this.documentTransitFlaghistory = value;
    }

    public Integer getDocumentTransitFlagone() {
        return documentTransitFlagone;
    }

    public void setDocumentTransitFlagone(Integer value) {
        this.documentTransitFlagone = value;
    }

    public Integer getDocumentTransitLocksubj() {
        return documentTransitLocksubj;
    }

    public void setDocumentTransitLocksubj(Integer value) {
        this.documentTransitLocksubj = value;
    }

    public Integer getDocumentTransitAgreeedit() {
        return documentTransitAgreeedit;
    }

    public void setDocumentTransitAgreeedit(Integer value) {
        this.documentTransitAgreeedit = value;
    }

    public Integer getDocumentTransitColor() {
        return documentTransitColor;
    }

    public void setDocumentTransitColor(Integer documentTransitColor) {
        this.documentTransitColor = documentTransitColor;
    }

    public List<String> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<String> accessRoles) {
        this.accessRoles = accessRoles;
    }

    public List<String> getTransitChildIds() {
        return transitChildIds;
    }

    public void setTransitChildIds(List<String> transitChildIds) {
        this.transitChildIds = transitChildIds;
    }

    public DocumentTransit() {}

    public DocumentTransit(
            Integer documentTransitId,
            Integer documentId,
            Integer documentTransitNumber,
            String documentTransitName,
            Integer documentTransitLevel,
            Integer documentTransitRequired,
            Integer documentTransitCandelete,
            Integer documentTransitUseadmin,
            Integer documentTransitCanedit,
            Integer documentTransitTwicecheck,
            Integer documentTransitFlaghistory,
            Integer documentTransitFlagone,
            Integer documentTransitLocksubj,
            Integer documentTransitAgreeedit,
            Integer documentTransitColor
            ) {
        this.documentTransitId = documentTransitId;
        this.documentId = documentId;
        this.documentTransitNumber = documentTransitNumber;
        this.documentTransitName = documentTransitName;
        this.documentTransitLevel = documentTransitLevel;
        this.documentTransitRequired = documentTransitRequired;
        this.documentTransitCandelete = documentTransitCandelete;
        this.documentTransitUseadmin = documentTransitUseadmin;
        this.documentTransitCanedit = documentTransitCanedit;
        this.documentTransitTwicecheck = documentTransitTwicecheck;
        this.documentTransitFlaghistory = documentTransitFlaghistory;
        this.documentTransitFlagone = documentTransitFlagone;
        this.documentTransitLocksubj = documentTransitLocksubj;
        this.documentTransitAgreeedit = documentTransitAgreeedit;
        this.documentTransitColor = documentTransitColor;
    }
}

