package biz.gelicon.core.components.erp.todo.documenttransit;

import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransit;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;

/* Объект сгенерирован 06.07.2021 23:01 */
@Schema(description = "Представление объекта \"Статус типа документа\"")
public class DocumentTransitView {

    @Schema(description = "Идентификатор \"Статус типа документа\"")
    @Column(name="documenttransit_id")
    public Integer documentTransitId;

    @Schema(description = "Тип документа ИД")
    @Column(name="document_id")
    private Integer documentId;

    @Schema(description = "Порядковый номер")
    @Column(name="documenttransit_number")
    private Integer documentTransitNumber;

    @Schema(description = "Наименование")
    @Column(name="documenttransit_name")
    private String documentTransitName;

    @Schema(description = "Уровень статуса")
    @Column(name="documenttransit_level")
    private Integer documentTransitLevel;

    @Schema(description = "Обязательность")
    @Column(name="documenttransit_required")
    private Integer documentTransitRequired;

    @Schema(description = "Возможность удаления")
    @Column(name="documenttransit_candelete")
    private Integer documentTransitCandelete;

    /*
    Картинки не будет - будет цвет
    @Schema(description = "Картинка статуса")
    @Column(name="documenttransit_bitmap")
    private byte[] documentTransitBitmap;
     */

    @Schema(description = "Необходимость статуса")
    @Column(name="documenttransit_useadmin")
    private Integer documentTransitUseadmin;

    @Schema(description = "Возможность изменения")
    @Column(name="documenttransit_canedit")
    private Integer documentTransitCanedit;

    @Schema(description = "Запрет повторения")
    @Column(name="documenttransit_twicecheck")
    private Integer documentTransitTwicecheck;

    @Schema(description = "Логирование изменений")
    @Column(name="documenttransit_flaghistory")
    private Integer documentTransitFlaghistory;

    @Schema(description = "Флаг первого статуса")
    @Column(name="documenttransit_flagone")
    private Integer documentTransitFlagone;

    @Schema(description = "Блокировка ОАУ документа")
    @Column(name="documenttransit_locksubj")
    private Integer documentTransitLocksubj;

    @Schema(description = "Возможность изменения листа согласования")
    @Column(name="documenttransit_agreeedit")
    private Integer documentTransitAgreeedit;

    @Schema(description = "Цвет")
    @Column(name="documenttransit_color")
    private Integer documentTransitColor;


    public DocumentTransitView() {}

    public DocumentTransitView(
            Integer documentTransitId,
            Integer documentId,
            Integer documentTransitNumber,
            String documentTransitName,
            Integer documentTransitLevel,
            Integer documentTransitRequired,
            Integer documentTransitCandelete,
            //byte[] documentTransitBitmap,
            Integer documentTransitUseadmin,
            Integer documentTransitCanedit,
            Integer documentTransitTwicecheck,
            Integer documentTransitFlaghistory,
            Integer documentTransitFlagone,
            Integer documentTransitLocksubj,
            Integer documentTransitAgreeedit,
            Integer documentTransitColor) {
        this.documentTransitId = documentTransitId;
        this.documentId = documentId;
        this.documentTransitNumber = documentTransitNumber;
        this.documentTransitName = documentTransitName;
        this.documentTransitLevel = documentTransitLevel;
        this.documentTransitRequired = documentTransitRequired;
        this.documentTransitCandelete = documentTransitCandelete;
        //this.documentTransitBitmap = documentTransitBitmap;
        this.documentTransitUseadmin = documentTransitUseadmin;
        this.documentTransitCanedit = documentTransitCanedit;
        this.documentTransitTwicecheck = documentTransitTwicecheck;
        this.documentTransitFlaghistory = documentTransitFlaghistory;
        this.documentTransitFlagone = documentTransitFlagone;
        this.documentTransitLocksubj = documentTransitLocksubj;
        this.documentTransitAgreeedit = documentTransitAgreeedit;
        this.documentTransitColor = documentTransitColor;
    }

    public DocumentTransitView(DocumentTransit entity) {
        this.documentTransitId = entity.getDocumentTransitId();
        this.documentId = entity.getDocumentId();
        this.documentTransitNumber = entity.getDocumentTransitNumber();
        this.documentTransitName = entity.getDocumentTransitName();
        this.documentTransitLevel = entity.getDocumentTransitLevel();
        this.documentTransitRequired = entity.getDocumentTransitRequired();
        this.documentTransitCandelete = entity.getDocumentTransitCandelete();
        //this.documentTransitBitmap = entity.getDocumentTransitBitmap();
        this.documentTransitUseadmin = entity.getDocumentTransitUseadmin();
        this.documentTransitCanedit = entity.getDocumentTransitCanedit();
        this.documentTransitTwicecheck = entity.getDocumentTransitTwicecheck();
        this.documentTransitFlaghistory = entity.getDocumentTransitFlaghistory();
        this.documentTransitFlagone = entity.getDocumentTransitFlagone();
        this.documentTransitLocksubj = entity.getDocumentTransitLocksubj();
        this.documentTransitAgreeedit = entity.getDocumentTransitAgreeedit();
        this.documentTransitColor = entity.getDocumentTransitColor();
    }

    public Integer getDocumentTransitId() {
        return documentTransitId;
    }

    public void setDocumentTransitId(Integer value) {
        this.documentTransitId = value;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer value) {
        this.documentId = value;
    }

    public Integer getDocumentTransitNumber() {
        return documentTransitNumber;
    }

    public void setDocumentTransitNumber(Integer value) {
        this.documentTransitNumber = value;
    }

    public String getDocumentTransitName() {
        return documentTransitName;
    }

    public void setDocumentTransitName(String value) {
        this.documentTransitName = value;
    }

    public Integer getDocumentTransitLevel() {
        return documentTransitLevel;
    }

    public void setDocumentTransitLevel(Integer value) {
        this.documentTransitLevel = value;
    }

    public Integer getDocumentTransitRequired() {
        return documentTransitRequired;
    }

    public void setDocumentTransitRequired(Integer value) {
        this.documentTransitRequired = value;
    }

    public Integer getDocumentTransitCandelete() {
        return documentTransitCandelete;
    }

    public void setDocumentTransitCandelete(Integer value) {
        this.documentTransitCandelete = value;
    }

    /*
    public byte[] getDocumentTransitBitmap() {
        return documentTransitBitmap;
    }

    public void setDocumentTransitBitmap(byte[] value) {
        this.documentTransitBitmap = value;
    }
     */

    public Integer getDocumentTransitUseadmin() {
        return documentTransitUseadmin;
    }

    public void setDocumentTransitUseadmin(Integer value) {
        this.documentTransitUseadmin = value;
    }

    public Integer getDocumentTransitCanedit() {
        return documentTransitCanedit;
    }

    public void setDocumentTransitCanedit(Integer value) {
        this.documentTransitCanedit = value;
    }

    public Integer getDocumentTransitTwicecheck() {
        return documentTransitTwicecheck;
    }

    public void setDocumentTransitTwicecheck(Integer value) {
        this.documentTransitTwicecheck = value;
    }

    public Integer getDocumentTransitFlaghistory() {
        return documentTransitFlaghistory;
    }

    public void setDocumentTransitFlaghistory(Integer value) {
        this.documentTransitFlaghistory = value;
    }

    public Integer getDocumentTransitFlagone() {
        return documentTransitFlagone;
    }

    public void setDocumentTransitFlagone(Integer value) {
        this.documentTransitFlagone = value;
    }

    public Integer getDocumentTransitLocksubj() {
        return documentTransitLocksubj;
    }

    public void setDocumentTransitLocksubj(Integer value) {
        this.documentTransitLocksubj = value;
    }

    public Integer getDocumentTransitAgreeedit() {
        return documentTransitAgreeedit;
    }

    public void setDocumentTransitAgreeedit(Integer value) {
        this.documentTransitAgreeedit = value;
    }

    public Integer getDocumentTransitColor() {
        return documentTransitColor;
    }

    public void setDocumentTransitColor(Integer value) {
        this.documentTransitColor = value;
    }


}

