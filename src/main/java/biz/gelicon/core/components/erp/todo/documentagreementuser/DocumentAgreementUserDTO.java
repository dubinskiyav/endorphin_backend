package biz.gelicon.core.components.erp.todo.documentagreementuser;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 12:03 */
@Schema(description = "Объект \"Пользователь шаблона согласования документа\"")
public class DocumentAgreementUserDTO {

    @Id
    @Schema(description = "Идентификатор \"Пользователь шаблона согласования документа\"")
    private Integer documentAgreementUserId;

    @Schema(description = "Шаблон согласования ИД")
    private Integer documentAgreementId;

    @Schema(description = "Пользователь ИД")
    private Integer proguserId;

    @Schema(description = "Дата начала")
    private Date documentAgreementUserDocagreementuserDatebeg;

    @Schema(description = "Дата окончания")
    private Date documentAgreementUserDocagreementuserDateend;

    @Schema(description = "Дата назначения")
    private Date documentAgreementUserDocagreementuserDateset;


    public DocumentAgreementUserDTO() {}

    public DocumentAgreementUserDTO(
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

    public DocumentAgreementUserDTO(DocumentAgreementUser entity) {
        this.documentAgreementUserId = entity.getDocumentAgreementUserId();
        this.documentAgreementId = entity.getDocumentAgreementId();
        this.proguserId = entity.getProguserId();
        this.documentAgreementUserDocagreementuserDatebeg = entity.getAgreementUserDatebeg();
        this.documentAgreementUserDocagreementuserDateend = entity.getAgreementUserDateend();
        this.documentAgreementUserDocagreementuserDateset = entity.getAgreementUserDateset();
    }

    public DocumentAgreementUser toEntity() {
        return toEntity(new DocumentAgreementUser());
    }

    public DocumentAgreementUser toEntity(DocumentAgreementUser entity) {
        entity.setDocumentAgreementUserId(this.documentAgreementUserId);
        entity.setDocumentAgreementId(this.documentAgreementId);
        entity.setProguserId(this.proguserId);
        entity.setAgreementUserDatebeg(this.documentAgreementUserDocagreementuserDatebeg);
        entity.setAgreementUserDateend(this.documentAgreementUserDocagreementuserDateend);
        entity.setAgreementUserDateset(this.documentAgreementUserDocagreementuserDateset);
        return entity;
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

