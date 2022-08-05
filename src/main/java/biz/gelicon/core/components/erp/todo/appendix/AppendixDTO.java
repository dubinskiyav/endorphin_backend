package biz.gelicon.core.components.erp.todo.appendix;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/* Объект сгенерирован 11.05.2022 12:01 */
@Schema(description = "Объект \"Электронный материал\"")
public class AppendixDTO {

    @Id
    @Schema(description = "Идентификатор \"Электронный материал\"")
    private Integer appendixId;

    @Schema(description = "Классификатор ИД")
    private Integer capclassId;

    @Schema(description = "Наименование классификатора")
    private String capclassName;

    @Schema(description = "Пользователь ИД")
    private Integer proguserId;

    @Schema(description = "Имя пользователя")
    private String proguserName;

    @Schema(description = "Наименование")
    private String appendixName;

    @Schema(description = "Номер")
    private String appendixNumber;

    @Schema(description = "Номер в СЭД")
    private String appendixSednumber;

    @Schema(description = "Дата добавления")
    private Date appendixDate;

    @Schema(description = "Примечание")
    private String appendixRemark;

    @Schema(description = "Широта")
    private Double appendixLatitude;

    @Schema(description = "Долгота")
    private Double appendixLongitude;

    @Schema(description = "Расширение файла")
    private String appendixExt;

    @Schema(description = "Тип файла")
    private String appendixMime;

    @Schema(description = "Идентификатор в хранилице")
    private String appendixRepositoryid;

    @Schema(description = "Дата модификации")
    private Date appendixDatemodify;

    @Schema(description = "Основной документ ИД")
    private Integer documentRealId;

    @Schema(description = "Перечень ИД связанных документов")
    private List<Integer> documentRealIds;

    public AppendixDTO() {}

    public AppendixDTO(
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
            String appendixRepositoryid,
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
        this.appendixRepositoryid = appendixRepositoryid;
        this.appendixDatemodify = appendixDatemodify;
        this.documentRealId = documentRealId;
    }

    public AppendixDTO(Appendix entity) {
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
        this.appendixRepositoryid = entity.getAppendixRepositoryid();
        this.appendixDatemodify = entity.getAppendixDatemodify();
        this.documentRealId = entity.getDocumentRealId();
    }

    public Appendix toEntity() {
        return toEntity(new Appendix());
    }

    public Appendix toEntity(Appendix entity) {
        entity.setAppendixId(this.appendixId);
        entity.setCapclassId(this.capclassId);
        entity.setProguserId(this.proguserId);
        entity.setAppendixName(this.appendixName);
        entity.setAppendixNumber(this.appendixNumber);
        entity.setAppendixSednumber(this.appendixSednumber);
        entity.setAppendixDate(this.appendixDate);
        entity.setAppendixRemark(this.appendixRemark);
        entity.setAppendixLatitude(this.appendixLatitude);
        entity.setAppendixLongitude(this.appendixLongitude);
        entity.setAppendixExt(this.appendixExt);
        entity.setAppendixMime(this.appendixMime);
        entity.setAppendixRepositoryid(this.appendixRepositoryid);
        entity.setAppendixDatemodify(this.appendixDatemodify);
        entity.setDocumentRealId(this.documentRealId);
        return entity;
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

    public String getAppendixRepositoryid() {
        return appendixRepositoryid;
    }

    public void setAppendixRepositoryid(String value) {
        this.appendixRepositoryid = value;
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

    public List<Integer> getDocumentRealIds() {
        return documentRealIds;
    }

    public void setDocumentRealIds(List<Integer> documentRealIds) {
        this.documentRealIds = documentRealIds;
    }
}

