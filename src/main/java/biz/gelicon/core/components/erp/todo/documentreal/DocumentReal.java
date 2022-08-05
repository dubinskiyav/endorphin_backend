package biz.gelicon.core.components.erp.todo.documentreal;

import biz.gelicon.core.annotations.OnlyEdition;
import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.document.Document;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.config.EditionTag;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 29.06.2021 09:01 */
@Table(name = "documentreal")
@TableDescription("Документ")
public class DocumentReal {

    public enum DocumentLevel{
        SIMPLE_DOCUMENT,
        COMPOSITE_DOCUMENT,
        FOLDER_DOCUMENT
    };

    public enum DocumentType{
        NOT_DEFINED,
        IN,
        OUT,
        INTERNAL;
        public int getDocumentTypeId() {return ordinal()+1;}
    };

    @Id
    @Column(name = "documentreal_id",nullable = false)
    public Integer documentRealId;

    @ManyToOne(targetEntity = Document.class)
    @Column(name = "document_id", nullable = false)
    @NotNull(message = "Поле \"Тип документа ИД\" не может быть пустым")
    private Integer documentId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    @NotNull(message = "Поле \"Пользователь ИД\" не может быть пустым")
    private Integer proguserId;

    @Column(name = "clusterdocumentreal_id", nullable = false)
    @NotNull(message = "Поле \"Уровень документов ИД\" не может быть пустым")
    private Integer clusterDocumentRealId;

    @OnlyEdition(tags = EditionTag.GELICON_ERP)
    @Column(name = "documenttype_id", nullable = false)
    @NotNull(message = "Поле \"Тип документа\" не может быть пустым")
    private Integer documentTypeId = DocumentType.NOT_DEFINED.getDocumentTypeId();

    @Column(name = "documentreal_name", nullable = false)
    @Size(max = 128, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String documentRealName;

    @Column(name = "documentreal_code", nullable = true)
    @Size(max = 10, message = "Поле \"Код\" должно содержать не более {max} символов")
    private String documentRealCode;

    @Column(name = "documentreal_number", nullable = true)
    @Size(max = 50, message = "Поле \"Номер\" должно содержать не более {max} символов")
    private String documentRealNumber;

    @Column(name = "documentreal_date", nullable = false)
    @NotNull(message = "Поле \"Дата\" не может быть пустым")
    private Date documentRealDate;

    @Column(name = "documentreal_remark", nullable = true)
    @Size(max = 2000, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String documentRealRemark;

    @Column(name = "documentreal_level", nullable = false)
    @NotNull(message = "Поле \"Уровень\" не может быть пустым")
    private Integer documentRealLevel;

    @Column(name = "documentreal_datebeg", nullable = true)
    private Date documentRealDatebeg;

    @Column(name = "documentreal_dateend", nullable = true)
    private Date documentRealDateend;

    @Column(name = "documentreal_repkey", nullable = true)
    @Size(max = 40, message = "Поле \"Ключ репликации\" должно содержать не более {max} символов")
    private String documentRealRepkey;

    @Column(name = "documentreal_status", nullable = false)
    @NotNull(message = "Поле \"Текущее состояние\" не может быть пустым")
    private Integer documentRealStatus = 0;

    @Column(name = "lastproguser_id", nullable = true)
    private Integer lastProguserId;

    @Column(name = "documentreal_datecreate", nullable = true)
    private Date documentRealDatecreate;

    @Column(name = "documentreal_datemodify", nullable = true)
    private Date documentRealDatemodify;

    @OnlyEdition(tags = EditionTag.GELICON_UTILITIES)
    @Column(name = "documentreal_archive", nullable = false)
    @NotNull(message = "Поле \"Перенесено в архив\" не может быть пустым")
    private Integer documentRealArchive = 0;

    @ManyToOne(targetEntity = DocumentReal.class)
    @Column(name = "parent_id", nullable = false)
    @NotNull(message = "Поле \"Идентификатор родительского документа\" не может быть пустым")
    private Integer parentId;

    public Integer getDocumentRealId() {
        return documentRealId;
    }

    public void setDocumentRealId(Integer value) {
        this.documentRealId = value;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer value) {
        this.documentId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Integer getClusterDocumentRealId() {
        return clusterDocumentRealId;
    }

    public void setClusterDocumentRealId(Integer value) {
        this.clusterDocumentRealId = value;
    }

    public String getDocumentRealName() {
        return documentRealName;
    }

    public void setDocumentRealName(String value) {
        this.documentRealName = value;
    }

    public String getDocumentRealCode() {
        return documentRealCode;
    }

    public void setDocumentRealCode(String value) {
        this.documentRealCode = value;
    }

    public String getDocumentRealNumber() {
        return documentRealNumber;
    }

    public void setDocumentRealNumber(String value) {
        this.documentRealNumber = value;
    }

    public Date getDocumentRealDate() {
        return documentRealDate;
    }

    public void setDocumentRealDate(Date value) {
        this.documentRealDate = value;
    }

    public String getDocumentRealRemark() {
        return documentRealRemark;
    }

    public void setDocumentRealRemark(String value) {
        this.documentRealRemark = value;
    }

    public Integer getDocumentRealLevel() {
        return documentRealLevel;
    }

    public void setDocumentRealLevel(Integer value) {
        this.documentRealLevel = value;
    }

    public Date getDocumentRealDatebeg() {
        return documentRealDatebeg;
    }

    public void setDocumentRealDatebeg(Date value) {
        this.documentRealDatebeg = value;
    }

    public Date getDocumentRealDateend() {
        return documentRealDateend;
    }

    public void setDocumentRealDateend(Date value) {
        this.documentRealDateend = value;
    }

    public String getDocumentRealRepkey() {
        return documentRealRepkey;
    }

    public void setDocumentRealRepkey(String value) {
        this.documentRealRepkey = value;
    }

    public Integer getDocumentRealStatus() {
        return documentRealStatus;
    }

    public void setDocumentRealStatus(Integer value) {
        this.documentRealStatus = value;
    }

    public Integer getLastProguserId() {
        return lastProguserId;
    }

    public void setLastProguserId(Integer value) {
        this.lastProguserId = value;
    }

    public Date getDocumentRealDatecreate() {
        return documentRealDatecreate;
    }

    public void setDocumentRealDatecreate(Date value) {
        this.documentRealDatecreate = value;
    }

    public Date getDocumentRealDatemodify() {
        return documentRealDatemodify;
    }

    public void setDocumentRealDatemodify(Date value) {
        this.documentRealDatemodify = value;
    }

    public Integer getDocumentRealArchive() {
        return documentRealArchive;
    }

    public void setDocumentRealArchive(Integer value) {
        this.documentRealArchive = value;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer value) {
        this.parentId = value;
    }

    public Integer getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public DocumentReal() {}

    public DocumentReal(
            Integer documentRealId,
            Integer documentId,
            Integer proguserId,
            Integer clusterDocumentRealId,
            String documentRealName,
            String documentRealCode,
            String documentRealNumber,
            Date documentRealDate,
            String documentRealRemark,
            Integer documentRealLevel,
            Date documentRealDatebeg,
            Date documentRealDateend,
            String documentRealRepkey,
            Integer documentRealStatus,
            Integer lastProguserId,
            Date documentRealDatecreate,
            Date documentRealDatemodify,
            Integer documentRealArchive,
            Integer parentId) {
        this.documentRealId = documentRealId;
        this.documentId = documentId;
        this.proguserId = proguserId;
        this.clusterDocumentRealId = clusterDocumentRealId;
        this.documentRealName = documentRealName;
        this.documentRealCode = documentRealCode;
        this.documentRealNumber = documentRealNumber;
        this.documentRealDate = documentRealDate;
        this.documentRealRemark = documentRealRemark;
        this.documentRealLevel = documentRealLevel;
        this.documentRealDatebeg = documentRealDatebeg;
        this.documentRealDateend = documentRealDateend;
        this.documentRealRepkey = documentRealRepkey;
        this.documentRealStatus = documentRealStatus;
        this.lastProguserId = lastProguserId;
        this.documentRealDatecreate = documentRealDatecreate;
        this.documentRealDatemodify = documentRealDatemodify;
        this.documentRealArchive = documentRealArchive;
        this.parentId = parentId;
    }
}

