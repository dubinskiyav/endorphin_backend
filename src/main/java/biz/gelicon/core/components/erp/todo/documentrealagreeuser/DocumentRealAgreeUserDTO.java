package biz.gelicon.core.components.erp.todo.documentrealagreeuser;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 10:35 */
@Schema(description = "Объект \"Пользователь согласования документа\"")
public class DocumentRealAgreeUserDTO {

    @Id
    @Schema(description = "Идентификатор \"Пользователь согласования документа\"")
    private Integer documentRealAgreeUserId;

    @Schema(description = "Согласование документа ИД")
    private Integer documentRealAgreeId;

    @Schema(description = "Пользователь ИД")
    private Integer proguserId;

    @Schema(description = "Дата начала")
    private Date agreeUserDatebeg;

    @Schema(description = "Дата окончания")
    private Date agreeUserDateend;

    @Schema(description = "Дата назначения")
    private Date agreeUserDateset;


    public DocumentRealAgreeUserDTO() {}

    public DocumentRealAgreeUserDTO(
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

    public DocumentRealAgreeUserDTO(DocumentRealAgreeUser entity) {
        this.documentRealAgreeUserId = entity.getDocumentRealAgreeUserId();
        this.documentRealAgreeId = entity.getDocumentRealAgreeId();
        this.proguserId = entity.getProguserId();
        this.agreeUserDatebeg = entity.getAgreeUserDatebeg();
        this.agreeUserDateend = entity.getAgreeUserDateend();
        this.agreeUserDateset = entity.getAgreeUserDateset();
    }

    public DocumentRealAgreeUser toEntity() {
        return toEntity(new DocumentRealAgreeUser());
    }

    public DocumentRealAgreeUser toEntity(DocumentRealAgreeUser entity) {
        entity.setDocumentRealAgreeUserId(this.documentRealAgreeUserId);
        entity.setDocumentRealAgreeId(this.documentRealAgreeId);
        entity.setProguserId(this.proguserId);
        entity.setAgreeUserDatebeg(this.agreeUserDatebeg);
        entity.setAgreeUserDateend(this.agreeUserDateend);
        entity.setAgreeUserDateset(this.agreeUserDateset);
        return entity;
    }

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


}

