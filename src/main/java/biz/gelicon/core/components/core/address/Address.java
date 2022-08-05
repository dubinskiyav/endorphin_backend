package biz.gelicon.core.components.core.address;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.components.core.street.Street;
import biz.gelicon.core.components.core.capcode.CapCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 15.07.2021 12:33 */
@Table(name = "address")
@TableDescription("Адрес")
public class Address {

    @Id
    @Column(name = "addresss_id",nullable = false)
    public Integer addressId;

    @ColumnDescription("Идентификатор улицы")
    @ManyToOne(targetEntity = Street.class)
    @Column(name = "street_id", nullable = false)
    @NotNull(message = "Поле \"sИдентификатор улицы\" не может быть пустым")
    private Integer streetId;

    @ColumnDescription("Строение")
    @Column(name = "addresss_building", nullable = true)
    @Size(max = 15, message = "Поле \"Строение\" должно содержать не более {max} символов")
    private String addressBuilding;

    @ColumnDescription("Корпус")
    @Column(name = "addresss_korpus", nullable = true)
    @Size(max = 15, message = "Поле \"Корпус\" должно содержать не более {max} символов")
    private String addressKorpus;

    @ColumnDescription("Номер квартиры/офиса")
    @Column(name = "addresss_flat", nullable = true)
    @Size(max = 20, message = "Поле \"Номер квартиры/офиса\" должно содержать не более {max} символов")
    private String addressFlat;

    @ColumnDescription("Почтовый индекс")
    @Column(name = "addresss_index", nullable = true)
    @Size(max = 6, message = "Поле \"Почтовый индекс\" должно содержать не более {max} символов")
    private String addressIndex;

    @ColumnDescription("Код")
    @Column(name = "addresss_code", nullable = true)
    @Size(max = 19, message = "Поле \"код\" должно содержать не более {max} символов")
    private String addressCode;

    @ColumnDescription("Тип строения")
    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "buildingtype_id", nullable = true)
    private Integer buildingTypeId;

    @ColumnDescription("Тип корпуса")
    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "korpustype_id", nullable = true)
    private Integer korpusTypeId;

    @ColumnDescription("Тип квартиры/офиса")
    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "flattype_id", nullable = true)
    private Integer flatTypeId;

    @ColumnDescription("Код ФИАС")
    @Column(name = "address_aoid", nullable = true)
    @Size(max = 36, message = "Поле \"Код ФИАС\" должно содержать не более {max} символов")
    private String addressAoid;

    @ColumnDescription("Глобальный код ФИАС")
    @Column(name = "address_aoguid", nullable = true)
    @Size(max = 36, message = "Поле \"Глобальный код ФИАС\" должно содержать не более {max} символов")
    private String addressAoGuid;

    @ColumnDescription("Дата модификации")
    @Column(name = "address_upddate", nullable = false)
    @NotNull(message = "Поле \"Дата модификации\" не может быть пустым")
    private Date addressUpddate;

    public Address() {}

    public Address(Integer addressId, Integer streetId, String addressBuilding,
            String addressKorpus,
            String addressFlat, String addressIndex, String addressCode, Integer buildingTypeId,
            Integer korpusTypeId, Integer flatTypeId, String addressAoid, String addressAoGuid,
            Date addressUpddate) {
        this.addressId = addressId;
        this.streetId = streetId;
        this.addressBuilding = addressBuilding;
        this.addressKorpus = addressKorpus;
        this.addressFlat = addressFlat;
        this.addressIndex = addressIndex;
        this.addressCode = addressCode;
        this.buildingTypeId = buildingTypeId;
        this.korpusTypeId = korpusTypeId;
        this.flatTypeId = flatTypeId;
        this.addressAoid = addressAoid;
        this.addressAoGuid = addressAoGuid;
        this.addressUpddate = addressUpddate;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public void setAddressBuilding(String addressBuilding) {
        this.addressBuilding = addressBuilding;
    }

    public void setAddressKorpus(String addressKorpus) {
        this.addressKorpus = addressKorpus;
    }

    public void setAddressFlat(String addressFlat) {
        this.addressFlat = addressFlat;
    }

    public void setAddressIndex(String addressIndex) {
        this.addressIndex = addressIndex;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public void setBuildingTypeId(Integer buildingTypeId) {
        this.buildingTypeId = buildingTypeId;
    }

    public void setKorpusTypeId(Integer korpusTypeId) {
        this.korpusTypeId = korpusTypeId;
    }

    public void setFlatTypeId(Integer flatTypeId) {
        this.flatTypeId = flatTypeId;
    }

    public void setAddressAoid(String addressAoid) {
        this.addressAoid = addressAoid;
    }

    public void setAddressAoGuid(String addressAoGuid) {
        this.addressAoGuid = addressAoGuid;
    }

    public void setAddressUpddate(Date addressUpddate) {
        this.addressUpddate = addressUpddate;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public Integer getStreetId() {
        return streetId;
    }

    public String getAddressBuilding() {
        return addressBuilding;
    }

    public String getAddressKorpus() {
        return addressKorpus;
    }

    public String getAddressFlat() {
        return addressFlat;
    }

    public String getAddressIndex() {
        return addressIndex;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public Integer getBuildingTypeId() {
        return buildingTypeId;
    }

    public Integer getKorpusTypeId() {
        return korpusTypeId;
    }

    public Integer getFlatTypeId() {
        return flatTypeId;
    }

    public String getAddressAoid() {
        return addressAoid;
    }

    public String getAddressAoGuid() {
        return addressAoGuid;
    }

    public Date getAddressUpddate() {
        return addressUpddate;
    }
}

