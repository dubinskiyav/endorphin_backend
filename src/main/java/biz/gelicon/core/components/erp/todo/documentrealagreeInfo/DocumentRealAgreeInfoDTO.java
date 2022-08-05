package biz.gelicon.core.components.erp.todo.documentrealagreeInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 08.07.2021 10:35 */
@Schema(description = "Объект \"Комментарии к позиции листа согласования\"")
public class DocumentRealAgreeInfoDTO {

    @Id
    @Schema(description = "Идентификатор \"Комментарии к позиции листа согласования\"")
    private Integer documentRealAgreeInfoId;

    @Schema(description = "Согласование документа ИД")
    private Integer documentRealAgreeId;

    @Schema(description = "Пользователь ИД")
    private Integer proguserId;

    @Schema(description = "Дата")
    private Date documentRealAgreeInfoDate;

    @Schema(description = "Содержание")
    private byte[] documentRealAgreeInfoText;


    public DocumentRealAgreeInfoDTO() {}

    public DocumentRealAgreeInfoDTO(
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

    public DocumentRealAgreeInfoDTO(DocumentRealAgreeInfo entity) {
        this.documentRealAgreeInfoId = entity.getDocumentRealAgreeInfoId();
        this.documentRealAgreeId = entity.getDocumentRealAgreeId();
        this.proguserId = entity.getProguserId();
        this.documentRealAgreeInfoDate = entity.getDocumentRealAgreeInfoDate();
        this.documentRealAgreeInfoText = entity.getDocumentRealAgreeInfoText();
    }

    public DocumentRealAgreeInfo toEntity() {
        return toEntity(new DocumentRealAgreeInfo());
    }

    public DocumentRealAgreeInfo toEntity(DocumentRealAgreeInfo entity) {
        entity.setDocumentRealAgreeInfoId(this.documentRealAgreeInfoId);
        entity.setDocumentRealAgreeId(this.documentRealAgreeId);
        entity.setProguserId(this.proguserId);
        entity.setDocumentRealAgreeInfoDate(this.documentRealAgreeInfoDate);
        entity.setDocumentRealAgreeInfoText(this.documentRealAgreeInfoText);
        return entity;
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

