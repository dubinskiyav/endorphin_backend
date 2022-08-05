package biz.gelicon.core.components.erp.todo.documentrealagreeInfo;

import biz.gelicon.core.components.erp.todo.documentrealagreeInfo.DocumentRealAgreeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 10:35 */
@Schema(description = "Представление объекта \"Комментарии к позиции листа согласования\"")
public class DocumentRealAgreeInfoView {

    @Schema(description = "Идентификатор \"Комментарии к позиции листа согласования\"")
    @Column(name="documentrealagreeinfo_id")
    public Integer documentRealAgreeInfoId;

    @Schema(description = "Согласование документа ИД")
    @Column(name="documentrealagree_id")
    private Integer documentRealAgreeId;

    @Schema(description = "Пользователь ИД")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Дата")
    @Column(name="documentrealagreeinfo_date")
    private Date documentRealAgreeInfoDate;

    @Schema(description = "Содержание")
    @Column(name="documentrealagreeinfo_text")
    private byte[] documentRealAgreeInfoText;


    public DocumentRealAgreeInfoView() {}

    public DocumentRealAgreeInfoView(
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

    public DocumentRealAgreeInfoView(DocumentRealAgreeInfo entity) {
        this.documentRealAgreeInfoId = entity.getDocumentRealAgreeInfoId();
        this.documentRealAgreeId = entity.getDocumentRealAgreeId();
        this.proguserId = entity.getProguserId();
        this.documentRealAgreeInfoDate = entity.getDocumentRealAgreeInfoDate();
        this.documentRealAgreeInfoText = entity.getDocumentRealAgreeInfoText();
    }

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


}

