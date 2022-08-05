package biz.gelicon.core.components.erp.todo.documentrealtransit;

import biz.gelicon.core.components.erp.todo.documentrealtransit.DocumentRealTransit;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 23.06.2021 22:49 */
@Schema(description = "Представление объекта \"Статус документа\"")
public class DocumentRealTransitView {

    @Schema(description = "Идентификатор \"Статус документа\"")
    @Column(name="documentrealtransit_id")
    public Integer documentRealTransitId;

    @Schema(description = "Документ ИД")
    @Column(name="documentreal_id")
    private Integer documentrealId;

    @Schema(description = "Статус типа документа ИД")
    @Column(name="documenttransit_id")
    private Integer documenttransitId;

    @Schema(description = "Пользователь ИД")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Дата регистрации")
    @Column(name="documentrealtransit_date")
    private Date documentRealTransitDate;

    @Schema(description = "Примечание")
    @Column(name="documentrealtransit_remark")
    private String documentRealTransitRemark;

    @Schema(description = "Дата установки")
    @Column(name="documentrealtransit_dateset")
    private Date documentRealTransitDateset;

    @Schema(description = "Флаг установки статуса")
    @Column(name="documentrealtransit_flag")
    private Integer documentRealTransitFlag;

    /**
     * Это из соединяемой таблицы proguser
     */
    @Schema(description = "Пользователь статуса")
    @Column(name="proguser_name")
    private String progUserName;

    /**
     * Это из соединяемой таблицы documenttransit
     */
    @Schema(description = "Наименование")
    @Column(name="documenttransit_name")
    private String documentTransitName;

    @Schema(description = "Цвет")
    @Column(name="documenttransit_color")
    private Integer documentTransitColor;

    public DocumentRealTransitView() {}

    public DocumentRealTransitView(Integer documentRealTransitId, Integer documentrealId,
            Integer documenttransitId, Integer proguserId, Date documentRealTransitDate,
            String documentRealTransitRemark, Date documentRealTransitDateset,
            Integer documentRealTransitFlag, String progUserName, String documentTransitName,
            Integer documentTransitColor) {
        this.documentRealTransitId = documentRealTransitId;
        this.documentrealId = documentrealId;
        this.documenttransitId = documenttransitId;
        this.proguserId = proguserId;
        this.documentRealTransitDate = documentRealTransitDate;
        this.documentRealTransitRemark = documentRealTransitRemark;
        this.documentRealTransitDateset = documentRealTransitDateset;
        this.documentRealTransitFlag = documentRealTransitFlag;
        this.progUserName = progUserName;
        this.documentTransitName = documentTransitName;
        this.documentTransitColor = documentTransitColor;
    }

    public DocumentRealTransitView(DocumentRealTransit entity) {
        this.documentRealTransitId = entity.getDocumentRealTransitId();
        this.documentrealId = entity.getDocumentrealId();
        this.documenttransitId = entity.getDocumenttransitId();
        this.proguserId = entity.getProguserId();
        this.documentRealTransitDate = entity.getDocumentRealTransitDate();
        this.documentRealTransitRemark = entity.getDocumentRealTransitRemark();
        this.documentRealTransitDateset = entity.getDocumentRealTransitDateset();
        this.documentRealTransitFlag = entity.getDocumentRealTransitFlag();
        // this.progUserName = ""; // todo как тут быть?
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

    public String getProgUserName() {
        return progUserName;
    }

    public void setProgUserName(String progUserName) {
        this.progUserName = progUserName;
    }

    public String getDocumentTransitName() {
        return documentTransitName;
    }

    public Integer getDocumentTransitColor() {
        return documentTransitColor;
    }

    public void setDocumentTransitName(String documentTransitName) {
        this.documentTransitName = documentTransitName;
    }

    public void setDocumentTransitColor(Integer documentTransitColor) {
        this.documentTransitColor = documentTransitColor;
    }
}

