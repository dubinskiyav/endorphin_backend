package biz.gelicon.core.components.erp.todo.appendix;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.components.core.capclass.CapClass;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 11.05.2022 12:01 */
@Table(name = "appendix")
@TableDescription("Электронный материал")
public class Appendix {
    public static final int APPENDIX_TYPE_FILE = 756;
    public static final int APPENDIX_TYPE_IMAGE = 435;
    public static final int APPENDIX_TYPE_DOCUMENT = 94;
    public static final int APPENDIX_TYPE_OTHER = 917;

    @Id
    @Column(name = "appendix_id",nullable = false)
    public Integer appendixId;

    /**
     * Тип ЭМ в capclass с capclass_type = 76
     */
    @ManyToOne(targetEntity = CapClass.class)
    @ColumnDescription("Тип ЭМ")
    @Column(name = "capclass_id", nullable = false)
    @NotNull(message = "Поле \"Тип ЭМ\" не может быть пустым")
    private Integer capclassId;

    @ManyToOne(targetEntity = Proguser.class)
    @ColumnDescription("Пользователь ИД")
    @Column(name = "proguser_id", nullable = false)
    @NotNull(message = "Поле \"Пользователь ИД\" не может быть пустым")
    private Integer proguserId;

    @ColumnDescription("Наименование")
    @Column(name = "appendix_name", nullable = false)
    @Size(max = 100, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String appendixName;

    @ColumnDescription("Номер")
    @Column(name = "appendix_number", nullable = true)
    @Size(max = 50, message = "Поле \"Номер\" должно содержать не более {max} символов")
    private String appendixNumber;

    @ColumnDescription("Номер в СЭД")
    @Column(name = "appendix_sednumber", nullable = true)
    @Size(max = 50, message = "Поле \"Номер в СЭД\" должно содержать не более {max} символов")
    private String appendixSednumber;

    @ColumnDescription("Дата добавления")
    @Column(name = "appendix_date", nullable = true)
    private Date appendixDate;

    @ColumnDescription("Примечание")
    @Column(name = "appendix_remark", nullable = true)
    @Size(max = 255, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String appendixRemark;

    @ColumnDescription("Широта")
    @Column(name = "appendix_latitude", nullable = true)
    private Double appendixLatitude;

    @ColumnDescription("Долгота")
    @Column(name = "appendix_longitude", nullable = true)
    private Double appendixLongitude;

    @ColumnDescription("Расширение файла")
    @Column(name = "appendix_ext", nullable = true)
    @Size(max = 10, message = "Поле \"Расширение файла\" должно содержать не более {max} символов")
    private String appendixExt;

    @ColumnDescription("Тип файла")
    @Column(name = "appendix_mime", nullable = true)
    @Size(max = 255, message = "Поле \"Тип файла\" должно содержать не более {max} символов")
    private String appendixMime;

    @ColumnDescription("Идентификатор в хранилице")
    @Column(name = "appendix_repositoryid", nullable = true)
    @Size(max = 255, message = "Поле \"Идентификатор в хранилице\" должно содержать не более {max} символов")
    private String appendixRepositoryid;

    @ColumnDescription("Дата модификации")
    @Column(name = "appendix_datemodify", nullable = false)
    @NotNull(message = "Поле \"Дата модификации\" не может быть пустым")
    private Date appendixDatemodify;

    @ManyToOne(targetEntity = DocumentReal.class)
    @ColumnDescription("Основной документ ИД")
    @Column(name = "documentreal_id", nullable = true)
    private Integer documentRealId;

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


    public Appendix() {}

    public Appendix(
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
}

