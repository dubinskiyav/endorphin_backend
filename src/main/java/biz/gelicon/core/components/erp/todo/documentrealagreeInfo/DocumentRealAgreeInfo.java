package biz.gelicon.core.components.erp.todo.documentrealagreeInfo;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.erp.todo.documentrealagree.DocumentRealAgree;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/* Сущность сгенерирована 08.07.2021 10:35 */
@Table(name = "documentrealagreeinfo")
@TableDescription("Комментарии к позиции листа согласования")
public class DocumentRealAgreeInfo {

    @Id
    @Column(name = "documentrealagreeinfo_id",nullable = false)
    public Integer documentRealAgreeInfoId;

    @ManyToOne(targetEntity = DocumentRealAgree.class)
    @Column(name = "documentrealagree_id", nullable = false)
    @NotNull(message = "Поле \"Согласование документа ИД\" не может быть пустым")
    private Integer documentRealAgreeId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    @NotNull(message = "Поле \"Пользователь ИД\" не может быть пустым")
    private Integer proguserId;

    @Column(name = "documentrealagreeinfo_date", nullable = false)
    @NotNull(message = "Поле \"Дата\" не может быть пустым")
    private Date documentRealAgreeInfoDate;

    @Column(name = "documentrealagreeinfo_text", nullable = false)
    @NotNull(message = "Поле \"Содержание\" не может быть пустым")
    private byte[] documentRealAgreeInfoText;

    public Integer getDocumentRealAgreeInfoId() {
        return documentRealAgreeInfoId;
    }

    public void setDocumentRealAgreeInfoId(Integer value) {
        this.documentRealAgreeInfoId = value;
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

    public Date getDocumentRealAgreeInfoDate() {
        return documentRealAgreeInfoDate;
    }

    public void setDocumentRealAgreeInfoDate(Date value) {
        this.documentRealAgreeInfoDate = value;
    }

    public byte[] getDocumentRealAgreeInfoText() {
        return documentRealAgreeInfoText;
    }

    public void setDocumentRealAgreeInfoText(byte[] value) {
        this.documentRealAgreeInfoText = value;
    }


    public DocumentRealAgreeInfo() {}

    public DocumentRealAgreeInfo(
            Integer documentRealAgreeInfoId,
            Integer documentRealAgreeId,
            Integer proguserId,
            Date documentRealAgreeInfoDate,
            byte[] documentRealAgreeInfoText) {
        this.documentRealAgreeInfoId = documentRealAgreeInfoId;
        this.documentRealAgreeId = documentRealAgreeId;
        this.proguserId = proguserId;
        this.documentRealAgreeInfoDate = documentRealAgreeInfoDate;
        this.documentRealAgreeInfoText = documentRealAgreeInfoText;
    }
}

