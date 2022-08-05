package biz.gelicon.core.components.erp.todo.appendix;

import biz.gelicon.core.components.erp.todo.appendix.Appendix;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 11.05.2022 12:01 */
@Schema(description = "Представление объекта \"Электронный материал\"")
public class AppendixView {

    @Schema(description = "Идентификатор \"Электронный материал\"")
    @Column(name="appendix_id")
    public Integer appendixId;

    @Schema(description = "Классификатор ИД")
    @Column(name="capclass_id")
    private Integer capclassId;

    @Schema(description = "Наименование классификатора")
    @Column(name = "capclass_name")
    private String capclassName;

    @Schema(description = "Пользователь ИД")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Имя пользователя")
    @Column(name = "proguser_name")
    private String proguserName;

    @Schema(description = "Наименование")
    @Column(name="appendix_name")
    private String appendixName;

    @Schema(description = "Номер")
    @Column(name="appendix_number")
    private String appendixNumber;

    @Schema(description = "Номер в СЭД")
    @Column(name="appendix_sednumber")
    private String appendixSednumber;

    @Schema(description = "Дата добавления")
    @Column(name="appendix_date")
    private Date appendixDate;

    @Schema(description = "Примечание")
    @Column(name="appendix_remark")
    private String appendixRemark;

    @Schema(description = "Широта")
    @Column(name="appendix_latitude")
    private Double appendixLatitude;

    @Schema(description = "Долгота")
    @Column(name="appendix_longitude")
    private Double appendixLongitude;

    @Schema(description = "Расширение файла")
    @Column(name="appendix_ext")
    private String appendixExt;

    @Schema(description = "Тип файла")
    @Column(name="appendix_mime")
    private String appendixMime;

    @Schema(description = "Дата модификации")
    @Column(name="appendix_datemodify")
    private Date appendixDatemodify;

    @Schema(description = "Основной документ ИД")
    @Column(name="documentreal_id")
    private Integer documentRealId;


    public AppendixView() {}

    public AppendixView(
            Integer appendixId,
            Integer capclassId,
            Integer proguserId,
            String appendixName,
            String appendixNumber,
            String appendixSednumber,
            Date appendixDate,
            String appendixRemark,
            Double appendixLatitude,
            Double appendixLongitude,
            String appendixExt,
            String appendixMime,
            Date appendixDatemodify,
            Integer documentRealId) {
        this.appendixId = appendixId;
        this.capclassId = capclassId;
        this.proguserId = proguserId;
        this.appendixName = appendixName;
        this.appendixNumber = appendixNumber;
        this.appendixSednumber = appendixSednumber;
        this.appendixDate = appendixDate;
        this.appendixRemark = appendixRemark;
        this.appendixLatitude = appendixLatitude;
        this.appendixLongitude = appendixLongitude;
        this.appendixExt = appendixExt;
        this.appendixMime = appendixMime;
        this.appendixDatemodify = appendixDatemodify;
        this.documentRealId = documentRealId;
    }

    public AppendixView(Appendix entity) {
        this.appendixId = entity.getAppendixId();
        this.capclassId = entity.getCapclassId();
        this.proguserId = entity.getProguserId();
        this.appendixName = entity.getAppendixName();
        this.appendixNumber = entity.getAppendixNumber();
        this.appendixSednumber = entity.getAppendixSednumber();
        this.appendixDate = entity.getAppendixDate();
        this.appendixRemark = entity.getAppendixRemark();
        this.appendixLatitude = entity.getAppendixLatitude();
        this.appendixLongitude = entity.getAppendixLongitude();
        this.appendixExt = entity.getAppendixExt();
        this.appendixMime = entity.getAppendixMime();
        this.appendixDatemodify = entity.getAppendixDatemodify();
        this.documentRealId = entity.getDocumentRealId();
    }

    public Integer getAppendixId() {
        return appendixId;
    }

    public void setAppendixId(Integer value) {
        this.appendixId = value;
    }

    public Integer getCapclassId() {
        return capclassId;
    }

    public void setCapclassId(Integer value) {
        this.capclassId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public String getAppendixName() {
        return appendixName;
    }

    public void setAppendixName(String value) {
        this.appendixName = value;
    }

    public String getAppendixNumber() {
        return appendixNumber;
    }

    public void setAppendixNumber(String value) {
        this.appendixNumber = value;
    }

    public String getAppendixSednumber() {
        return appendixSednumber;
    }

    public void setAppendixSednumber(String value) {
        this.appendixSednumber = value;
    }

    public Date getAppendixDate() {
        return appendixDate;
    }

    public void setAppendixDate(Date value) {
        this.appendixDate = value;
    }

    public String getAppendixRemark() {
        return appendixRemark;
    }

    public void setAppendixRemark(String value) {
        this.appendixRemark = value;
    }

    public Double getAppendixLatitude() {
        return appendixLatitude;
    }

    public void setAppendixLatitude(Double value) {
        this.appendixLatitude = value;
    }

    public Double getAppendixLongitude() {
        return appendixLongitude;
    }

    public void setAppendixLongitude(Double value) {
        this.appendixLongitude = value;
    }

    public String getAppendixExt() {
        return appendixExt;
    }

    public void setAppendixExt(String value) {
        this.appendixExt = value;
    }

    public String getAppendixMime() {
        return appendixMime;
    }

    public void setAppendixMime(String value) {
        this.appendixMime = value;
    }

    public Date getAppendixDatemodify() {
        return appendixDatemodify;
    }

    public void setAppendixDatemodify(Date value) {
        this.appendixDatemodify = value;
    }

    public Integer getDocumentRealId() {
        return documentRealId;
    }

    public void setDocumentRealId(Integer value) {
        this.documentRealId = value;
    }

    public String getCapclassName() {
        return capclassName;
    }

    public void setCapclassName(String capclassName) {
        this.capclassName = capclassName;
    }

    public String getProguserName() {
        return proguserName;
    }

    public void setProguserName(String proguserName) {
        this.proguserName = proguserName;
    }
}

