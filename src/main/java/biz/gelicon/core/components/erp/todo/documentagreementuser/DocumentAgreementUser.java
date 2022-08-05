package biz.gelicon.core.components.erp.todo.documentagreementuser;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.erp.todo.documentagreement.DocumentAgreement;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Date;

/* Сущность сгенерирована 08.07.2021 12:03 */
@Table(
    name = "documentagreementuser",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"documentagreement_id", "proguser_id"})
    }
)
@TableDescription("Пользователь шаблона согласования документа")
public class DocumentAgreementUser {

    @Id
    @Column(name = "documentagreementuser_id",nullable = false)
    public Integer documentAgreementUserId;

    @ManyToOne(targetEntity = DocumentAgreement.class)
    @Column(name = "documentagreement_id", nullable = false)
    @NotNull(message = "Поле \"Шаблон согласования ИД\" не может быть пустым")
    private Integer documentAgreementId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    @NotNull(message = "Поле \"Пользователь ИД\" не может быть пустым")
    private Integer proguserId;

    @Column(name = "docagreementuser_datebeg", nullable = false)
    @NotNull(message = "Поле \"Дата начала\" не может быть пустым")
    private Date agreementUserDatebeg;

    @Column(name = "docagreementuser_dateend", nullable = false)
    @NotNull(message = "Поле \"Дата окончания\" не может быть пустым")
    private Date agreementUserDateend;

    @Column(name = "docagreementuser_dateset", nullable = false)
    @NotNull(message = "Поле \"Дата назначения\" не может быть пустым")
    private Date agreementUserDateset;

    public Integer getDocumentAgreementUserId() {
        return documentAgreementUserId;
    }

    public void setDocumentAgreementUserId(Integer value) {
        this.documentAgreementUserId = value;
    }

    public Integer getDocumentAgreementId() {
        return documentAgreementId;
    }

    public void setDocumentAgreementId(Integer value) {
        this.documentAgreementId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Date getAgreementUserDatebeg() {
        return agreementUserDatebeg;
    }

    public void setAgreementUserDatebeg(Date value) {
        this.agreementUserDatebeg = value;
    }

    public Date getAgreementUserDateend() {
        return agreementUserDateend;
    }

    public void setAgreementUserDateend(Date value) {
        this.agreementUserDateend = value;
    }

    public Date getAgreementUserDateset() {
        return agreementUserDateset;
    }

    public void setAgreementUserDateset(Date value) {
        this.agreementUserDateset = value;
    }


    public DocumentAgreementUser() {}

    public DocumentAgreementUser(
            Integer documentAgreementUserId,
            Integer documentAgreementId,
            Integer proguserId,
            Date agreementUserDatebeg,
            Date agreementUserDateend,
            Date agreementUserDateset) {
        this.documentAgreementUserId = documentAgreementUserId;
        this.documentAgreementId = documentAgreementId;
        this.proguserId = proguserId;
        this.agreementUserDatebeg = agreementUserDatebeg;
        this.agreementUserDateend = agreementUserDateend;
        this.agreementUserDateset = agreementUserDateset;
    }
}

