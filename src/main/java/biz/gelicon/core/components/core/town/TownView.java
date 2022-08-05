package biz.gelicon.core.components.core.town;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 04.07.2022 11:06 */
@Schema(description = "Представление объекта \"Населенный пункт\"")
public class TownView {

    @Schema(description = "Идентификатор \"Населенный пункт\"")
    @Column(name="town_id")
    public Integer townId;

    @Schema(description = "Тип населенного пункта ИД")
    @Column(name="towntype_id")
    private Integer towntypeId;

    @Schema(description = "Наименование типа населенного пункта")
    @Column(name="towntype_name", table = "towntype")
    private String towntypeName;

    @Schema(description = "Код типа населенного пункта")
    @Column(name="towntype_code", table = "towntype")
    private String towntypeCode;

    @Schema(description = "Наименование")
    @Column(name="town_name")
    private String townName;

    @Schema(description = "Код")
    @Column(name="town_code")
    private String townCode;

    @Schema(description = "Индекс")
    @Column(name="town_index")
    private String townIndex;

    @Schema(description = "Код ФИАС")
    @Column(name="town_aoid")
    private String townAoid;

    @Schema(description = "Глобальный код ФИАС")
    @Column(name="town_aoguid")
    private String townAoguid;

    @Schema(description = "Дата модификации")
    @Column(name="town_upddate")
    private Date townUpddate;

    @Schema(description = "Идентификатор страны")
    @Column(name="country_id")
    private Integer countryId;

    @Schema(description = "Наименование страны")
    @Column(name="country_name", table = "country")
    private String countryName;

    @Schema(description = "Идентификатор типа подчиненности города")
    @Column(name="townsubordinate_id")
    private Integer townsubordinateId;

    @Schema(description = "Наименование типа подчиненности города")
    @Column(name="townsubordinate_name", table = "townsubordinate")
    private String townsubordinateName;

    @Schema(description = "Телефонный код")
    @Column(name="town_phonecode")
    private String townPhonecode;


    public TownView() {}

    public TownView(
            Integer townId,
            Integer towntypeId,
            String townName,
            String townCode,
            String townIndex,
            String townAoid,
            String townAoguid,
            Date townUpddate,
            Integer countryId,
            Integer townsubordinateId,
            String townPhonecode) {
        this.townId = townId;
        this.towntypeId = towntypeId;
        this.townName = townName;
        this.townCode = townCode;
        this.townIndex = townIndex;
        this.townAoid = townAoid;
        this.townAoguid = townAoguid;
        this.townUpddate = townUpddate;
        this.countryId = countryId;
        this.townsubordinateId = townsubordinateId;
        this.townPhonecode = townPhonecode;
    }

    public TownView(Town entity) {
        this.townId = entity.getTownId();
        this.towntypeId = entity.getTowntypeId();
        this.townName = entity.getTownName();
        this.townCode = entity.getTownCode();
        this.townIndex = entity.getTownIndex();
        this.townAoid = entity.getTownAoid();
        this.townAoguid = entity.getTownAoGuid();
        this.townUpddate = entity.getTownUpddate();
        this.countryId = entity.getCountryId();
        this.townsubordinateId = entity.getTownSubordinateId();
        this.townPhonecode = entity.getTownPhonecode();
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer value) {
        this.townId = value;
    }

    public Integer getTowntypeId() {
        return towntypeId;
    }

    public void setTowntypeId(Integer value) {
        this.towntypeId = value;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String value) {
        this.townName = value;
    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String value) {
        this.townCode = value;
    }

    public String getTownIndex() {
        return townIndex;
    }

    public void setTownIndex(String value) {
        this.townIndex = value;
    }

    public String getTownAoid() {
        return townAoid;
    }

    public void setTownAoid(String value) {
        this.townAoid = value;
    }

    public String getTownAoguid() {
        return townAoguid;
    }

    public void setTownAoguid(String value) {
        this.townAoguid = value;
    }

    public Date getTownUpddate() {
        return townUpddate;
    }

    public void setTownUpddate(Date value) {
        this.townUpddate = value;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer value) {
        this.countryId = value;
    }

    public Integer getTownsubordinateId() {
        return townsubordinateId;
    }

    public void setTownsubordinateId(Integer value) {
        this.townsubordinateId = value;
    }

    public String getTownPhonecode() {
        return townPhonecode;
    }

    public void setTownPhonecode(String value) {
        this.townPhonecode = value;
    }

    public String getTowntypeName() {
        return towntypeName;
    }

    public void setTowntypeName(String towntypeName) {
        this.towntypeName = towntypeName;
    }

    public String getTowntypeCode() {
        return towntypeCode;
    }

    public void setTowntypeCode(String towntypeCode) {
        this.towntypeCode = towntypeCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTownsubordinateName() {
        return townsubordinateName;
    }

    public void setTownsubordinateName(String townsubordinateName) {
        this.townsubordinateName = townsubordinateName;
    }
}

