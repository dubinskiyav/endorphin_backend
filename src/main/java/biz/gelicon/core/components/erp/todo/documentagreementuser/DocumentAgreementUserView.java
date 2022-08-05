package biz.gelicon.core.components.erp.todo.documentagreementuser;

import biz.gelicon.core.components.erp.todo.documentagreementuser.DocumentAgreementUser;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 12:03 */
@Schema(description = "Представление объекта \"Пользователь шаблона согласования документа\"")
public class DocumentAgreementUserView {

    @Schema(description = "Идентификатор \"Пользователь шаблона согласования документа\"")
    @Column(name="documentagreementuser_id")
    public Integer documentAgreementUserId;

    @Schema(description = "Шаблон согласования ИД")
    @Column(name="documentagreement_id")
    private Integer documentAgreementId;

    @Schema(description = "Пользователь ИД")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Дата начала")
    @Column(name="docagreementuser_datebeg")
    private Date documentAgreementUserDocagreementuserDatebeg;

    @Schema(description = "Дата окончания")
    @Column(name="docagreementuser_dateend")
    private Date documentAgreementUserDocagreementuserDateend;

    @Schema(description = "Дата назначения")
    @Column(name="docagreementuser_dateset")
    private Date documentAgreementUserDocagreementuserDateset;


    public DocumentAgreementUserView() {}

    public DocumentAgreementUserView(
            Integer documentAgreementUserId,
            Integer documentAgreementId,
            Integer proguserId,
            Date documentAgreementUserDocagreementuserDatebeg,
            Date documentAgreementUserDocagreementuserDateend,
            Date documentAgreementUserDocagreementuserDateset) {
        this.documentAgreementUserId = documentAgreementUserId;
        this.documentAgreementId = documentAgreementId;
        this.proguserId = proguserId;
        this.documentAgreementUserDocagreementuserDatebeg = documentAgreementUserDocagreementuserDatebeg;
        this.documentAgreementUserDocagreementuserDateend = documentAgreementUserDocagreementuserDateend;
        this.documentAgreementUserDocagreementuserDateset = documentAgreementUserDocagreementuserDateset;
    }

    public DocumentAgreementUserView(DocumentAgreementUser entity) {
        this.documentAgreementUserId = entity.getDocumentAgreementUserId();
        this.documentAgreementId = entity.getDocumentAgreementId();
        this.proguserId = entity.getProguserId();
        this.documentAgreementUserDocagreementuserDatebeg = entity.getAgreementUserDatebeg();
        this.documentAgreementUserDocagreementuserDateend = entity.getAgreementUserDateend();
        this.documentAgreementUserDocagreementuserDateset = entity.getAgreementUserDateset();
    }

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

    public Date getDocumentAgreementUserDocagreementuserDatebeg() {
        return documentAgreementUserDocagreementuserDatebeg;
    }

    public void setDocumentAgreementUserDocagreementuserDatebeg(Date value) {
        this.documentAgreementUserDocagreementuserDatebeg = value;
    }

    public Date getDocumentAgreementUserDocagreementuserDateend() {
        return documentAgreementUserDocagreementuserDateend;
    }

    public void setDocumentAgreementUserDocagreementuserDateend(Date value) {
        this.documentAgreementUserDocagreementuserDateend = value;
    }

    public Date getDocumentAgreementUserDocagreementuserDateset() {
        return documentAgreementUserDocagreementuserDateset;
    }

    public void setDocumentAgreementUserDocagreementuserDateset(Date value) {
        this.documentAgreementUserDocagreementuserDateset = value;
    }


}

