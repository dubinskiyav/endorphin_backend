package biz.gelicon.core.components.core.company;

import biz.gelicon.core.annotations.OnlyEdition;
import biz.gelicon.core.config.EditionTag;
import biz.gelicon.core.components.core.subject.Subject;
import biz.gelicon.core.components.core.subjecttype.SubjectType;
import biz.gelicon.core.dto.SelectDisplayData;
import biz.gelicon.core.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class CompanyDTO {

    @Schema(description = "Идентификатор \"Контрагент\"")
    public Integer companyId;

    @Schema(description = "Идентификатор Населенный пункт")
    private SelectDisplayData<Integer> town;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @Schema(description = "Краткое имя")
    private String companyNotation;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Уникальный ключ из системы Биллинг")
    private String companyBillingcode;

    @Schema(description = "Наименование")
    private String companyName;

    @Schema(description = "Полное наименование")
    private String companyFullname;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Идентификатор Адрес")
    private Integer addressId;

    @Schema(description = "Адрес")
    private String companyAddress;

    @Schema(description = "Телефон")
    private String companyTelefon;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Электронная почта")
    private String companyEmail;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Сайт")
    private String companyWeb;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @Schema(description = "ИНН")
    private String companyInn;

    @Schema(description = "КПП")
    private String companyKpp;

    @Schema(description = "Описание")
    private String companyNote;

    @Schema(description = "Примечание")
    private String companyRemark;

    @Schema(description = "Флаг контрагента")
    private Integer companyFlagClient;

    @Schema(description = "Флаг НеРезидента")
    private Integer companyFlagNotResident;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Флаг согласования раскопки")
    private Integer companyExcavation;

    @Schema(description = "Дата модификации")
    private Date companyUpdDate;

    @Schema(description = "Является дупликатом")
    private Integer companyDuplicate;

    @Schema(description = "Флаг блокировки")
    private Integer companyBlockFlag;

    @Schema(description = "Физическое лицо")
    private Integer companyPhysicalPerson;

    @Schema(description = "Банк")
    private Integer companyBank;

    @Schema(description = "Адрес фактический")
    private String companyAddressFact;

    @Schema(description = "Населенный пункт факт ИД")
    private Integer townFactId;

    public CompanyDTO() {
    }

    public CompanyDTO(Integer companyId, SelectDisplayData<Integer> town,String companyNotation, String companyBillingcode,
                       String companyName, String companyFullname, Integer addressId, String companyAddress,
                       String companyTelefon, String companyEmail, String companyWeb, String companyInn, String companyNote,
                       String companyRemark, Integer companyFlagClient, Integer companyFlagNotResident, Integer companyExcavation,
                      Date companyUpdDate, Integer companyDuplicate,
                      Integer companyBlockFlag, Integer companyPhysicalPerson, Integer companyBank,
                      String companyAddressFact, Integer townFactId) {
        this.companyId = companyId;
        this.town = town;
        this.companyNotation = companyNotation;
        this.companyBillingcode = companyBillingcode;
        this.companyName = companyName;
        this.companyFullname = companyFullname;
        this.addressId = addressId;
        this.companyAddress = companyAddress;
        this.companyTelefon = companyTelefon;
        this.companyEmail = companyEmail;
        this.companyWeb = companyWeb;
        this.companyInn = companyInn;
        this.companyNote = companyNote;
        this.companyRemark = companyRemark;
        this.companyFlagClient = companyFlagClient;
        this.companyFlagNotResident = companyFlagNotResident;
        this.companyExcavation = companyExcavation;
        this.companyUpdDate = companyUpdDate;
        this.companyDuplicate = companyDuplicate;
        this.companyBlockFlag = companyBlockFlag;
        this.companyPhysicalPerson = companyPhysicalPerson;
        this.companyBank = companyBank;
        this.companyAddressFact = companyAddressFact;
        this.townFactId = townFactId;
    }

    public Company toEntity(){
        return toEntity(new Company());
    }

    public Company toEntity(Company entity){
        entity.setCompanyId(this.companyId);
        entity.setTownId(this.town.getValue());
        entity.setCompanyNotation(this.companyNotation);
        entity.setCompanyBillingcode(this.companyBillingcode);
        entity.setCompanyName(this.companyName);
        entity.setCompanyFullname(this.companyFullname);
        entity.setAddressId(this.addressId);
        entity.setCompanyAddress(this.companyAddress);
        entity.setCompanyTelefon(this.companyTelefon);
        entity.setCompanyEmail(this.companyEmail);
        entity.setCompanyWeb(this.companyWeb);
        entity.setCompanyInn(this.companyInn);
        entity.setCompanyNote(this.companyNote);
        entity.setCompanyRemark(this.companyRemark);
        entity.setCompanyFlagclient(this.companyFlagClient);
        entity.setCompanyFlagnotresident(this.companyFlagNotResident);
        entity.setCompanyExcavation(this.companyExcavation);
        entity.setCompanyUpdDate(this.companyUpdDate);
        entity.setCompanyDuplicate(this.companyDuplicate);
        entity.setCompanyBlockFlag(this.companyBlockFlag);
        entity.setCompanyPhysicalPerson(this.companyPhysicalPerson);
        entity.setCompanyBank(this.companyBank);
        entity.setCompanyAddressFact(this.companyAddressFact);
        entity.setTownFactId(this.townFactId);
        return entity;
    }

    public Subject toSubject(){
        return toSubject(new Subject());
    }

    public Subject toSubject(Subject subject){
        subject.setSubjectId(this.companyId);
        subject.setClusterId(28);
        subject.setSubjectTypeId(SubjectType.ITEM_SUBJECTTYPE_ID);
        subject.setSubjectName(this.companyName);
        subject.setSubjectDatebeg(DateUtils.getMinDate());
        subject.setSubjectDateend(DateUtils.getMaxDate());
        subject.setSubjectCode("");
        subject.setSubjectStatus(0);
        subject.setSubjectDatemodify(new Date());
        subject.setRootSubjectId(Company.ROOT_SUBJECT_ID);
        subject.setParentId(Company.PARENT_ID);
        return subject;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public SelectDisplayData<Integer> getTown() {
        return town;
    }

    public void setTown(SelectDisplayData<Integer> town) {
        this.town = town;
    }

    public String getCompanyNotation() {
        return companyNotation;
    }

    public void setCompanyNotation(String companyNotation) {
        this.companyNotation = companyNotation;
    }

    public String getCompanyBillingcode() {
        return companyBillingcode;
    }

    public void setCompanyBillingcode(String companyBillingcode) {
        this.companyBillingcode = companyBillingcode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyFullname() {
        return companyFullname;
    }

    public void setCompanyFullname(String companyFullname) {
        this.companyFullname = companyFullname;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyTelefon() {
        return companyTelefon;
    }

    public void setCompanyTelefon(String companyTelefon) {
        this.companyTelefon = companyTelefon;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyWeb() {
        return companyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        this.companyWeb = companyWeb;
    }

    public String getCompanyInn() {
        return companyInn;
    }

    public void setCompanyInn(String companyInn) {
        this.companyInn = companyInn;
    }

    public String getCompanyKpp() {
        return companyKpp;
    }

    public void setCompanyKpp(String companyKpp) {
        this.companyKpp = companyKpp;
    }

    public String getCompanyNote() {
        return companyNote;
    }

    public void setCompanyNote(String companyNote) {
        this.companyNote = companyNote;
    }

    public String getCompanyRemark() {
        return companyRemark;
    }

    public void setCompanyRemark(String companyRemark) {
        this.companyRemark = companyRemark;
    }

    public Integer getCompanyFlagClient() {
        return companyFlagClient;
    }

    public void setCompanyFlagClient(Integer companyFlagClient) {
        this.companyFlagClient = companyFlagClient;
    }

    public Integer getCompanyFlagNotResident() {
        return companyFlagNotResident;
    }

    public void setCompanyFlagNotResident(Integer companyFlagNotResident) {
        this.companyFlagNotResident = companyFlagNotResident;
    }

    public Integer getCompanyExcavation() {
        return companyExcavation;
    }

    public void setCompanyExcavation(Integer companyExcavation) {
        this.companyExcavation = companyExcavation;
    }

    public Date getCompanyUpdDate() {
        return companyUpdDate;
    }

    public void setCompanyUpdDate(Date companyUpdDate) {
        this.companyUpdDate = companyUpdDate;
    }

    public Integer getCompanyDuplicate() {
        return companyDuplicate;
    }

    public void setCompanyDuplicate(Integer companyDuplicate) {
        this.companyDuplicate = companyDuplicate;
    }

    public Integer getCompanyBlockFlag() {
        return companyBlockFlag;
    }

    public void setCompanyBlockFlag(Integer companyBlockFlag) {
        this.companyBlockFlag = companyBlockFlag;
    }

    public Integer getCompanyPhysicalPerson() {
        return companyPhysicalPerson;
    }

    public void setCompanyPhysicalPerson(Integer companyPhysicalPerson) {
        this.companyPhysicalPerson = companyPhysicalPerson;
    }

    public Integer getCompanyBank() {
        return companyBank;
    }

    public void setCompanyBank(Integer companyBank) {
        this.companyBank = companyBank;
    }

    public String getCompanyAddressFact() {
        return companyAddressFact;
    }

    public void setCompanyAddressFact(String companyAddressFact) {
        this.companyAddressFact = companyAddressFact;
    }

    public Integer getTownFactId() {
        return townFactId;
    }

    public void setTownFactId(Integer townFactId) {
        this.townFactId = townFactId;
    }
}
