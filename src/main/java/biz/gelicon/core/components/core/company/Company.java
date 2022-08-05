package biz.gelicon.core.components.core.company;

import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.annotations.OnlyEdition;
import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.address.Address;
import biz.gelicon.core.components.core.town.Town;
import biz.gelicon.core.config.EditionTag;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 15.07.2021 11:56 */
@Table(
        name = "company"
)
@TableDescription("Организация")
public class Company {

    public static final int CAPCODE_INN = 79;
    public static final int CAPCODE_KPP = 84;
    public static final int ROOT_SUBJECT_ID = 79;
    public static final int PARENT_ID = 101;

    @Id
    @Column(name = "company_id",nullable = false)
    public Integer companyId;

    @ColumnDescription("Идентификатор города")
    @ManyToOne(targetEntity = Town.class)
    @Column(name = "town_id", nullable = false)
    @NotNull(message = "Поле \"Идентификатор города\" не может быть пустым")
    private Integer townId;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @ColumnDescription("Краткое имя контрагента")
    @Column(name = "company_notation", nullable = false)
    @Size(max = 255, message = "Поле \"Краткое имя контрагента\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Краткое имя контрагента\" не может быть пустым")
    private String companyNotation;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Уникальный ключ из системы Биллинг")
    @Column(name = "company_billingcode", nullable = true)
    @Size(max = 50, message = "Поле \"Уникальный ключ из системы Биллинг\" должно содержать не более {max} символов")
    private String companyBillingcode;

    @ColumnDescription("Имя контрагента")
    @Column(name = "company_name", nullable = false)
    @Size(max = 310, message = "Поле \"Имя контрагента\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Имя контрагента\" не может быть пустым")
    private String companyName;

    @ColumnDescription("Полное наименование")
    @Column(name = "company_fullname", nullable = false)
    @Size(max = 512, message = "Поле \"Полное наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Полное наименование\" не может быть пустым")
    private String companyFullname;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ManyToOne(targetEntity = Address.class)
    @ColumnDescription("Идентификатор адресс")
    @Column(name = "address_id", nullable = true)
    private Integer addressId;

    @ColumnDescription("Адрес")
    @Column(name = "company_address", nullable = true)
    @Size(max = 255, message = "Поле \"Адрес\" должно содержать не более {max} символов")
    private String companyAddress;

    @ColumnDescription("Номер телефона")
    @Column(name = "company_telefon", nullable = true)
    @Size(max = 50, message = "Поле \"Номер телефона\" должно содержать не более {max} символов")
    private String companyTelefon;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Электронная почта")
    @Column(name = "company_email", nullable = true)
    @Size(max = 100, message = "Поле \"Электронная почта\" должно содержать не более {max} символов")
    private String companyEmail;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Сайт")
    @Column(name = "company_web", nullable = true)
    @Size(max = 100, message = "Поле \"Сайт\" должно содержать не более {max} символов")
    private String companyWeb;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @ColumnDescription("ИНН")
    @Column(name = "company_inn", nullable = true)
    @Size(max = 20, message = "Поле \"ИНН\" должно содержать не более {max} символов")
    private String companyInn;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @ColumnDescription("Описание")
    @Column(name = "company_note", nullable = true)
    @Size(max = 255, message = "Поле \"Описание\" должно содержать не более {max} символов")
    private String companyNote;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Примечание")
    @Column(name = "company_remark", nullable = true)
    @Size(max = 255, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String companyRemark;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @ColumnDescription("Имя контрагента (доп)")
    @Column(name = "company_name1", nullable = true)
    @Size(max = 50, message = "Поле \"Имя контрагента (доп)\" должно содержать не более {max} символов")
    private String companyName1;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @ColumnDescription("Флаг клиента")
    @Column(name = "company_flagclient", nullable = false)
    @NotNull(message = "Поле \"Флаг клиента\" не может быть пустым")
    private Integer companyFlagclient;

    @OnlyEdition(tags = {EditionTag.GELICON_ERP})
    @ColumnDescription("Флаг не резидента")
    @Column(name = "company_flagnotresident", nullable = true)
    private Integer companyFlagnotresident;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Флаг согласования раскопки")
    @Column(name = "company_excavation", nullable = false)
    @NotNull(message = "Поле \"Флаг согласования раскопки\" не может быть пустым")
    private Integer companyExcavation;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Дата модификации")
    @Column(name = "company_upddate", nullable = false)
    @NotNull(message = "Поле \"Дата модификации\" не может быть пустым")
    private Date companyUpdDate;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Является дупликатом")
    @Column(name = "company_duplicate", nullable = false)
    @NotNull(message = "Поле \"Является дупликатом\" не может быть пустым")
    private Integer companyDuplicate;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Флаг блокировки")
    @Column(name = "company_blockflag", nullable = false)
    @NotNull(message = "Поле \"Флаг блокировки\" не может быть пустым")
    private Integer companyBlockFlag;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Физическое лицо")
    @Column(name = "company_physicalperson", nullable = false)
    @NotNull(message = "Поле \"Физическое лицо\" не может быть пустым")
    private Integer companyPhysicalPerson;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Банк")
    @Column(name = "company_bank", nullable = false)
    @NotNull(message = "Поле \"Флаг блокировки\" не может быть пустым")
    private Integer companyBank;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Адрес фактический")
    @Column(name = "company_addressfact", nullable = false)
    @NotNull(message = "Поле \"Адрес фактический\" не может быть пустым")
    private String companyAddressFact;

    @OnlyEdition(tags = {EditionTag.GELICON_UTILITIES})
    @ColumnDescription("Населенный пункт факт ИД")
    @Column(name = "townfact_id", nullable = false)
    @NotNull(message = "Поле \"Населенный пункт факт ИД\" не может быть пустым")
    private Integer townFactId;

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

    public String getCompanyName1() {
        return companyName1;
    }

    public void setCompanyName1(String companyName1) {
        this.companyName1 = companyName1;
    }

    public Integer getCompanyFlagclient() {
        return companyFlagclient;
    }

    public void setCompanyFlagclient(Integer companyFlagclient) {
        this.companyFlagclient = companyFlagclient;
    }

    public Integer getCompanyFlagnotresident() {
        return companyFlagnotresident;
    }

    public void setCompanyFlagnotresident(Integer companyFlagnotresident) {
        this.companyFlagnotresident = companyFlagnotresident;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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

    // Для GU
    public Company(Integer companyId, Integer townId, String companyBillingcode, String companyName,
                   String companyFullname, Integer addressId,
                   String companyAddress, String companyTelefon, String companyEmail,String companyWeb,
                   String companyRemark, Integer companyExcavation, Date companyUpdDate, Integer companyDuplicate,
                   Integer companyBlockFlag, Integer companyPhysicalPerson, Integer companyBank,
                   String companyAddressFact, Integer townFactId) {
        this.companyId = companyId;
        this.townId = townId;
        this.companyBillingcode = companyBillingcode;
        this.companyName = companyName;
        this.companyFullname = companyFullname;
        this.addressId = addressId;
        this.companyAddress = companyAddress;
        this.companyTelefon = companyTelefon;
        this.companyEmail = companyEmail;
        this.companyWeb = companyWeb;
        this.companyRemark = companyRemark;
        this.companyExcavation = companyExcavation;
        this.companyUpdDate = companyUpdDate;
        this.companyDuplicate =  companyDuplicate;
        this.companyBlockFlag = companyBlockFlag;
        this.companyPhysicalPerson = companyPhysicalPerson;
        this.companyBank = companyBank;
        this.companyAddressFact = companyAddressFact;
        this.townFactId = townFactId;
    }

    public Company() {}

    // Для ERP
    public Company(Integer companyId, Integer townId, String companyNotation, String companyName,
                   String companyFullname, String companyAddress,
                   String companyTelefon, String companyInn, String companyNote,
                   String companyName1, Integer companyFlagclient, Integer companyFlagnotresident,
                   Date companyUpdDate, Integer companyDuplicate, Integer companyBlockFlag,
                   Integer companyPhysicalPerson, Integer companyBank, String companyAddressFact,
                   Integer townFactId) {
        this.companyId = companyId;
        this.townId = townId;
        this.companyNotation = companyNotation;
        this.companyName = companyName;
        this.companyFullname = companyFullname;
        this.companyAddress = companyAddress;
        this.companyTelefon = companyTelefon;
        this.companyInn = companyInn;
        this.companyNote = companyNote;
        this.companyName1 = companyName1;
        this.companyFlagclient = companyFlagclient;
        this.companyFlagnotresident = companyFlagnotresident;
        this.companyUpdDate = companyUpdDate;
        this.companyDuplicate =  companyDuplicate;
        this.companyBlockFlag = companyBlockFlag;
        this.companyPhysicalPerson = companyPhysicalPerson;
        this.companyBank = companyBank;
        this.companyAddressFact = companyAddressFact;
        this.townFactId = townFactId;
    }
}

