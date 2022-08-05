package biz.gelicon.core.components.erp.todo.documentrealtransit;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransit;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 23.06.2021 22:49 */
@Table(name = "documentrealtransit")
@TableDescription("Статус документа")
public class DocumentRealTransit {

    @Id
    @Column(name = "documentrealtransit_id",nullable = false)
    public Integer documentRealTransitId;

    @ManyToOne(targetEntity = DocumentReal.class)
    @Column(name = "documentreal_id", nullable = false)
    @NotNull(message = "Поле \"Документ ИД\" не может быть пустым")
    private Integer documentrealId;

    @ManyToOne(targetEntity = DocumentTransit.class)
    @Column(name = "documenttransit_id", nullable = false)
    @NotNull(message = "Поле \"Статус типа документа ИД\" не может быть пустым")
    private Integer documenttransitId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    @NotNull(message = "Поле \"Пользователь ИД\" не может быть пустым")
    private Integer proguserId;

    @Column(name = "documentrealtransit_date", nullable = false)
    @NotNull(message = "Поле \"Дата регистрации\" не может быть пустым")
    private Date documentRealTransitDate;

    @Column(name = "documentrealtransit_remark", nullable = true)
    @Size(max = 128, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String documentRealTransitRemark;

    @Column(name = "documentrealtransit_dateset", nullable = false)
    @NotNull(message = "Поле \"Дата установки\" не может быть пустым")
    private Date documentRealTransitDateset;

    @Column(name = "documentrealtransit_flag", nullable = false)
    @NotNull(message = "Поле \"Флаг установки статуса\" не может быть пустым")
    private Integer documentRealTransitFlag;

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


    public DocumentRealTransit() {}

    public DocumentRealTransit(
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
}

