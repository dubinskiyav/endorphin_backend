package biz.gelicon.core.components.core.town;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.components.core.country.Country;
import biz.gelicon.core.components.core.townsubordinate.TownSubordinate;
import biz.gelicon.core.components.core.towntype.TownType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 15.07.2021 11:56 */
@Table(name = "town")
@TableDescription("Город")
public class Town {

    @Id
    @Column(name = "town_id",nullable = false)
    public Integer townId;

    @ColumnDescription("Тип города")
    @ManyToOne(targetEntity = TownType.class)
    @Column(name = "towntype_id", nullable = false)
    @NotNull(message = "Поле \"Тип города\" не может быть пустым")
    private Integer towntypeId;

    @ManyToOne(targetEntity = TownSubordinate.class)
    @ColumnDescription("Идентификатор типа подчиненности города")
    @Column(name = "townsubordinate_id", nullable = false)
    @NotNull(message = "Поле \"Идентификатор типа подчиненности города\" не может быть пустым")
    private Integer townSubordinateId;

    @ManyToOne(targetEntity = Country.class)
    @ColumnDescription("Идентификатор страны")
    @Column(name = "country_id", nullable = false)
    @NotNull(message = "Поле \"Идентификатор страны\" не может быть пустым")
    private Integer countryId;

    @ColumnDescription("Имя города")
    @Column(name = "town_name", nullable = false)
    @Size(max = 50, message = "Поле \"Имя города\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Имя города\" не может быть пустым")
    private String townName;

    @ColumnDescription("Код города")
    @Column(name = "town_code", nullable = true)
    @Size(max = 20, message = "Поле \"Код города\" должно содержать не более {max} символов")
    private String townCode;

    @ColumnDescription("Телефонный код города")
    @Column(name = "town_phonecode", nullable = true)
    @Size(max = 10, message = "Поле \"Телефонный код города\" должно содержать не более {max} символов")
    private String townPhonecode;

    @ColumnDescription("Почтовый индекс")
    @Column(name = "town_index", nullable = true)
    @Size(max = 10, message = "Поле \"Почтовый индекс\" должно содержать не более {max} символов")
    private String townIndex;

    @ColumnDescription("Код ФИАС")
    @Column(name = "town_aoid", nullable = true)
    @Size(max = 36, message = "Поле \"Код ФИАС\" должно содержать не более {max} символов")
    private String townAoid;

    @ColumnDescription("Глобальный код ФИАС")
    @Column(name = "town_aoguid", nullable = true)
    @Size(max = 36, message = "Поле \"Глобальный код ФИАС\" должно содержать не более {max} символов")
    private String townAoGuid;

    @ColumnDescription("Дата модификации")
    @Column(name = "town_upddate", nullable = false)
    @NotNull(message = "Поле \"Дата модификации\" не может быть пустым")
    private Date townUpddate;

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

    public String getTownPhonecode() {
        return townPhonecode;
    }

    public void setTownPhonecode(String value) {
        this.townPhonecode = value;
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

    public void setTownAoid(String townAoid) {
        this.townAoid = townAoid;
    }

    public String getTownAoGuid() {
        return townAoGuid;
    }

    public void setTownAoGuid(String townAoGuid) {
        this.townAoGuid = townAoGuid;
    }

    public Date getTownUpddate() {
        return townUpddate;
    }

    public void setTownUpddate(Date townUpddate) {
        this.townUpddate = townUpddate;
    }

    public Integer getTownSubordinateId() {
        return townSubordinateId;
    }

    public void setTownSubordinateId(Integer townSubordinateId) {
        this.townSubordinateId = townSubordinateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Town() {}

    public Town(
            Integer townId,
            Integer towntypeId,
            Integer townsubordinateId,
            Integer countryId,
            String townName,
            String townCode,
            String townPhonecode,
            String townIndex,
            Date townUpddate) {
        this.townId = townId;
        this.towntypeId = towntypeId;
        this.townName = townName;
        this.townCode = townCode;
        this.townPhonecode = townPhonecode;
        this.townIndex = townIndex;
        this.townSubordinateId =townsubordinateId;
        this.countryId=countryId;
        this.townUpddate =townUpddate;
    }
}

