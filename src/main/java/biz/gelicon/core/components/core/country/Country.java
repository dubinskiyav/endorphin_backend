package biz.gelicon.core.components.core.country;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.annotations.ColumnDescription;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 26.07.2021 14:15 */
@Table(name = "country")
@TableDescription("Страна")
public class Country {
    public static final int RUSSIA = 192;
    @Id
    @Column(name = "country_id",nullable = false)
    public Integer countryId;

    @ColumnDescription("Наименование")
    @Column(name = "country_name", nullable = false)
    @Size(max = 100, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String countryName;

    @ColumnDescription("Код")
    @Column(name = "country_code", nullable = true)
    @Size(max = 5, message = "Поле \"Код\" должно содержать не более {max} символов")
    private String countryCode;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer value) {
        this.countryId = value;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String value) {
        this.countryName = value;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String value) {
        this.countryCode = value;
    }


    public Country() {}

    public Country(
            Integer countryId,
            String countryName,
            String countryCode) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }
}

