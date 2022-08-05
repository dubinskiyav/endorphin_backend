package biz.gelicon.core.components.erp.todo.documentrealagreeuser;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.erp.todo.documentrealagree.DocumentRealAgree;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Date;

/* Сущность сгенерирована 08.07.2021 10:35 */
@Table(
    name = "documentrealagreeuser",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"documentrealagree_id", "proguser_id"})
    }
)
@TableDescription("Пользователь согласования документа")
public class DocumentRealAgreeUser {

    @Id
    @Column(name = "documentrealagreeuser_id",nullable = false)
    public Integer documentRealAgreeUserId;

    @ManyToOne(targetEntity = DocumentRealAgree.class)
    @Column(name = "documentrealagree_id", nullable = false)
    @NotNull(message = "Поле \"Согласование документа ИД\" не может быть пустым")
    private Integer documentRealAgreeId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    @NotNull(message = "Поле \"Пользователь ИД\" не может быть пустым")
    private Integer proguserId;

    @Column(name = "docrealagreeuser_datebeg", nullable = false)
    @NotNull(message = "Поле \"Дата начала\" не может быть пустым")
    private Date agreeUserDatebeg;

    @Column(name = "docrealagreeuser_dateend", nullable = false)
    @NotNull(message = "Поле \"Дата окончания\" не может быть пустым")
    private Date agreeUserDateend;

    @Column(name = "docrealagreeuser_dateset", nullable = false)
    @NotNull(message = "Поле \"Дата назначения\" не может быть пустым")
    private Date agreeUserDateset;

    public Integer getDocumentRealAgreeUserId() {
        return documentRealAgreeUserId;
    }

    public void setDocumentRealAgreeUserId(Integer value) {
        this.documentRealAgreeUserId = value;
    }

    public Integer getDocumentRealAgreeId() {
        return documentRealAgreeId;
    }

    public void setDocumentRealAgreeId(Integer value) {
        this.documentRealAgreeId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Date getAgreeUserDatebeg() {
        return agreeUserDatebeg;
    }

    public void setAgreeUserDatebeg(Date value) {
        this.agreeUserDatebeg = value;
    }

    public Date getAgreeUserDateend() {
        return agreeUserDateend;
    }

    public void setAgreeUserDateend(Date value) {
        this.agreeUserDateend = value;
    }

    public Date getAgreeUserDateset() {
        return agreeUserDateset;
    }

    public void setAgreeUserDateset(Date value) {
        this.agreeUserDateset = value;
    }


    public DocumentRealAgreeUser() {}

    public DocumentRealAgreeUser(
            Integer documentRealAgreeUserId,
            Integer documentRealAgreeId,
            Integer proguserId,
            Date agreeUserDatebeg,
            Date agreeUserDateend,
            Date agreeUserDateset) {
        this.documentRealAgreeUserId = documentRealAgreeUserId;
        this.documentRealAgreeId = documentRealAgreeId;
        this.proguserId = proguserId;
        this.agreeUserDatebeg = agreeUserDatebeg;
        this.agreeUserDateend = agreeUserDateend;
        this.agreeUserDateset = agreeUserDateset;
    }
}

