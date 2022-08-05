package biz.gelicon.core.components.core.company;

import biz.gelicon.core.annotations.OnlyEdition;
import biz.gelicon.core.config.EditionTag;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 19.07.2021 17:46 */
@Schema(description = "Представление объекта \"Контрагент\"")
public class CompanyView {

    @Schema(description = "Идентификатор \"Контрагент\"")
    @Column(name="company_id")
    public Integer companyId;

    @Schema(description = "Идентификатор Населенный пункт")
    @Column(name="town_id")
    private Integer townId;

    @Schema(description = "Населенный пункт")
    @Column(name="town_name",table = "town")
    private String townName;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @Schema(description = "Краткое имя")
    @Column(name = "company_notation")
    private String companyNotation;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Уникальный ключ из системы Биллинг")
    @Column(name="company_billingcode")
    private String companyBillingcode;

    @Schema(description = "Наименование")
    @Column(name="company_name")
    private String companyName;

    @Schema(description = "Полное наименование")
    @Column(name="company_fullname")
    private String companyFullname;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Идентификатор Адрес")
    @Column(name="address_id")
    private Integer addressId;

    @Schema(description = "Адрес")
    @Column(name="company_address")
    private String companyAddress;

    @Schema(description = "Телефон")
    @Column(name="company_telefon")
    private String companyTelefon;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Электронная почта")
    @Column(name="company_email")
    private String companyEmail;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Сайт")
    @Column(name="company_web")
    private String companyWeb;

    @Schema(description = "ИНН")
    @Column(name = "company_inn", nullable = true)
    private String companyInn;

    @Schema(description = "КПП")
    @Column(name = "company_kpp", nullable = true)
    private String companyKpp;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @Schema(description = "Описание")
    @Column(name = "company_note")
    private String companyNote;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @Schema(description = "Примечание")
    @Column(name="company_remark")
    private String companyRemark;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @Schema(description = "Флаг контрагента")
    @Column(name = "company_flagclient")
    private Integer companyFlagClient;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @Schema(description = "Флаг НеРезидента")
    @Column(name = "company_flagnotresident")
    private Integer companyFlagNotResident;

    @Schema(description = "Флаг согласования раскопки")
    @Column(name = "company_excavation", nullable = false)
    private Integer companyExcavation;

    @Schema(description = "Дата модификации")
    @Column(name = "company_upddate", nullable = false)
    private Date companyUpdDate;

    @Schema(description = "Является дупликатом")
    @Column(name = "company_duplicate", nullable = false)
    private Integer companyDuplicate;

    @Schema(description = "Флаг блокировки")
    @Column(name = "company_blockflag", nullable = false)
    private Integer companyBlockFlag;

    @Schema(description = "Физическое лицо")
    @Column(name = "company_physicalperson", nullable = false)
    private Integer companyPhysicalPerson;

    @Schema(description = "Банк")
    @Column(name = "company_bank", nullable = false)
    private Integer companyBank;

    @Schema(description = "Адрес фактический")
    @Column(name = "company_addressfact", nullable = false)
    private String companyAddressFact;

    @Schema(description = "Населенный пункт факт ИД")
    @Column(name = "townfact_id", nullable = false)
    private Integer townFactId;


    public CompanyView() {}

    public CompanyView(Integer companyId, Integer townId, String townName,String companyNotation, String companyBillingcode,
                       String companyName, String companyFullname, Integer addressId, String companyAddress,
                       String companyTelefon, String companyEmail, String companyWeb, String companyInn, String companyNote,
                       String companyRemark, Integer companyFlagClient, Integer companyFlagNotResident,
                       Integer companyExcavation, Date companyUpdDate, Integer companyDuplicate,
                       Integer companyBlockFlag, Integer companyPhysicalPerson, Integer companyBank,
                       String companyAddressFact, Integer townFactId) {
        this.companyId = companyId;
        this.townId = townId;
        this.townName = townName;
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
        this.companyDuplicate =  companyDuplicate;
        this.companyBlockFlag = companyBlockFlag;
        this.companyPhysicalPerson = companyPhysicalPerson;
        this.companyBank = companyBank;
        this.companyAddressFact = companyAddressFact;
        this.townFactId = townFactId;
    }

    public CompanyView(Company entity) {
        this.companyId = entity.getCompanyId();
        this.townId = entity.getTownId();
        this.companyNotation = entity.getCompanyNotation();
        this.companyBillingcode = entity.getCompanyBillingcode();
        this.companyName = entity.getCompanyName();
        this.companyFullname = entity.getCompanyFullname();
        this.addressId = entity.getAddressId();
        this.companyAddress = entity.getCompanyAddress();
        this.companyTelefon = entity.getCompanyTelefon();
        this.companyEmail = entity.getCompanyEmail();
        this.companyWeb = entity.getCompanyWeb();
        this.companyInn = entity.getCompanyInn();
        this.companyNote = entity.getCompanyNote();
        this.companyRemark = entity.getCompanyRemark();
        this.companyFlagClient = entity.getCompanyFlagclient();
        this.companyFlagNotResident = entity.getCompanyFlagnotresident();
        this.companyExcavation = entity.getCompanyExcavation();
        this.companyUpdDate = entity.getCompanyUpdDate();
        this.companyDuplicate = entity.getCompanyDuplicate();
        this.companyBlockFlag = entity.getCompanyBlockFlag();
        this.companyPhysicalPerson = entity.getCompanyPhysicalPerson();
        this.companyBank = entity.getCompanyBank();
        this.companyAddressFact = entity.getCompanyAddressFact();
        this.townFactId = entity.getTownFactId();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
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

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getCompanyKpp() {
        return companyKpp;
    }

    public void setCompanyKpp(String companyKpp) {
        this.companyKpp = companyKpp;
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

    public void setCompanyPhysicalPerson(Integer companyPhisicalPerson) {
        this.companyPhysicalPerson = companyPhisicalPerson;
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

