package biz.gelicon.core.components.erp.todo.documentrealagreeuser;

import biz.gelicon.core.components.erp.todo.documentrealagreeuser.DocumentRealAgreeUser;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 10:35 */
@Schema(description = "Представление объекта \"Пользователь согласования документа\"")
public class DocumentRealAgreeUserView {

    @Schema(description = "Идентификатор \"Пользователь согласования документа\"")
    @Column(name="documentrealagreeuser_id")
    public Integer documentRealAgreeUserId;

    @Schema(description = "Согласование документа ИД")
    @Column(name="documentrealagree_id")
    private Integer documentRealAgreeId;

    @Schema(description = "Пользователь ИД")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Дата начала")
    @Column(name="docrealagreeuser_datebeg")
    private Date agreeUserDatebeg;

    @Schema(description = "Дата окончания")
    @Column(name="docrealagreeuser_dateend")
    private Date agreeUserDateend;

    @Schema(description = "Дата назначения")
    @Column(name="docrealagreeuser_dateset")
    private Date agreeUserDateset;


    public DocumentRealAgreeUserView() {}

    public DocumentRealAgreeUserView(
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

    public DocumentRealAgreeUserView(DocumentRealAgreeUser entity) {
        this.documentRealAgreeUserId = entity.getDocumentRealAgreeUserId();
        this.documentRealAgreeId = entity.getDocumentRealAgreeId();
        this.proguserId = entity.getProguserId();
        this.agreeUserDatebeg = entity.getAgreeUserDatebeg();
        this.agreeUserDateend = entity.getAgreeUserDateend();
        this.agreeUserDateset = entity.getAgreeUserDateset();
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

