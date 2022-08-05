package biz.gelicon.core.components.core.address;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;

/* Объект сгенерирован 26.07.2021 18:19 */
@Schema(description = "Представление объекта \"Адрес\"")
public class AddressView {

    @Schema(description = "Идентификатор \"Адрес\"")
    @Column(name="address_id")
    public Integer addressId;

    @Schema(description = "Идентификатор страны")
    @Column(name="country_id",table = "country")
    private Integer countryId;

    @Schema(description = "Cтрана")
    @Column(name="country_name",table = "country")
    private String countryName;

    @Schema(description = "Идентификатор города")
    @Column(name="town_id",table = "town")
    private Integer townId;

    @Schema(description = "Город")
    @Column(name="town_name",table = "town")
    private String townName;

    @Schema(description = "Идентификатор улицы")
    @Column(name="street_id",table = "street")
    private Integer streetId;

    @Schema(description = "Улица")
    @Column(name="street_name",table = "street")
    private String streetName;

    @Schema(description = "Строение/Дом")
    @Column(name="address_building",table = "address") //table нужен чтобы отключить алиас m0 в сортировке
    private String addressBuilding;

    @Schema(description = "Корпус")
    @Column(name="address_korpus",table = "address") //table нужен чтобы отключить алиас m0 в сортировке
    private String addressKorpus;

    @Schema(description = "Квартира/офис")
    @Column(name="address_flat",table = "address") //table нужен чтобы отключить алиас m0 в сортировке
    private String addressFlat;

    @Schema(description = "Полный адрес")
    @Column(name="address_text")
    private String fullAddress;


    public AddressView() {}

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAddressBuilding() {
        return addressBuilding;
    }

    public void setAddressBuilding(String addressBuilding) {
        this.addressBuilding = addressBuilding;
    }

    public String getAddressKorpus() {
        return addressKorpus;
    }

    public void setAddressKorpus(String addressKorpus) {
        this.addressKorpus = addressKorpus;
    }

    public String getAddressFlat() {
        return addressFlat;
    }

    public void setAddressFlat(String addressFlat) {
        this.addressFlat = addressFlat;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Override
    public String toString() {
        return "AddressView{" +
                "addressId=" + addressId +
                ", countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", townId=" + townId +
                ", townName='" + townName + '\'' +
                ", streetId=" + streetId +
                ", streetName='" + streetName + '\'' +
                ", addressBuilding='" + addressBuilding + '\'' +
                ", addressKorpus='" + addressKorpus + '\'' +
                ", addressFlat='" + addressFlat + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}

