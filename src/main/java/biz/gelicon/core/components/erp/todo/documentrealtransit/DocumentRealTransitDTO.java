package biz.gelicon.core.components.erp.todo.documentrealtransit;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 23.06.2021 22:49 */
@Schema(description = "Объект \"Статус документа\"")
public class DocumentRealTransitDTO {

    @Id
    @Schema(description = "Идентификатор \"Статус документа\"")
    private Integer documentRealTransitId;

    @Schema(description = "Документ ИД")
    private Integer documentrealId;

    @Schema(description = "Статус типа документа ИД")
    private Integer documenttransitId;

    @Schema(description = "Пользователь ИД")
    private Integer proguserId;

    @Schema(description = "Дата регистрации")
    private Date documentRealTransitDate;

    @Schema(description = "Примечание")
    private String documentRealTransitRemark;

    @Schema(description = "Дата установки")
    private Date documentRealTransitDateset;

    @Schema(description = "Флаг установки статуса")
    private Integer documentRealTransitFlag;


    public DocumentRealTransitDTO() {}

    public DocumentRealTransitDTO(
            Integer documentRealTransitId,
            Integer documentrealId,
            Integer documenttransitId,
            Integer proguserId,
            Date documentRealTransitDate,
            String documentRealTransitRemark,
            Date documentRealTransitDateset,
            Integer documentRealTransitFlag) {
        this.documentRealTransitId = documentRealTransitId;
        this.documentrealId = documentrealId;
        this.documenttransitId = documenttransitId;
        this.proguserId = proguserId;
        this.documentRealTransitDate = documentRealTransitDate;
        this.documentRealTransitRemark = documentRealTransitRemark;
        this.documentRealTransitDateset = documentRealTransitDateset;
        this.documentRealTransitFlag = documentRealTransitFlag;
    }

    public DocumentRealTransitDTO(DocumentRealTransit entity) {
        this.documentRealTransitId = entity.getDocumentRealTransitId();
        this.documentrealId = entity.getDocumentrealId();
        this.documenttransitId = entity.getDocumenttransitId();
        this.proguserId = entity.getProguserId();
        this.documentRealTransitDate = entity.getDocumentRealTransitDate();
        this.documentRealTransitRemark = entity.getDocumentRealTransitRemark();
        this.documentRealTransitDateset = entity.getDocumentRealTransitDateset();
        this.documentRealTransitFlag = entity.getDocumentRealTransitFlag();
    }

    public DocumentRealTransit toEntity() {
        return toEntity(new DocumentRealTransit());
    }

    public DocumentRealTransit toEntity(DocumentRealTransit entity) {
        entity.setDocumentRealTransitId(this.documentRealTransitId);
        entity.setDocumentrealId(this.documentrealId);
        entity.setDocumenttransitId(this.documenttransitId);
        entity.setProguserId(this.proguserId);
        entity.setDocumentRealTransitDate(this.documentRealTransitDate);
        entity.setDocumentRealTransitRemark(this.documentRealTransitRemark);
        entity.setDocumentRealTransitDateset(this.documentRealTransitDateset);
        entity.setDocumentRealTransitFlag(this.documentRealTransitFlag);
        return entity;
    }

    public Integer getDocumentRealTransitId() {
        return documentRealTransitId;
    }

    public void setDocumentRealTransitId(Integer value) {
        this.documentRealTransitId = value;
    }

    public Integer getDocumentrealId() {
        return documentrealId;
    }

    public void setDocumentrealId(Integer value) {
        this.documentrealId = value;
    }

    public Integer getDocumenttransitId() {
        return documenttransitId;
    }

    public void setDocumenttransitId(Integer value) {
        this.documenttransitId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Date getDocumentRealTransitDate() {
        return documentRealTransitDate;
    }

    public void setDocumentRealTransitDate(Date value) {
        this.documentRealTransitDate = value;
    }

    public String getDocumentRealTransitRemark() {
        return documentRealTransitRemark;
    }

    public void setDocumentRealTransitRemark(String value) {
        this.documentRealTransitRemark = value;
    }

    public Date getDocumentRealTransitDateset() {
        return documentRealTransitDateset;
    }

    public void setDocumentRealTransitDateset(Date value) {
        this.documentRealTransitDateset = value;
    }

    public Integer getDocumentRealTransitFlag() {
        return documentRealTransitFlag;
    }

    public void setDocumentRealTransitFlag(Integer value) {
        this.documentRealTransitFlag = value;
    }


}

