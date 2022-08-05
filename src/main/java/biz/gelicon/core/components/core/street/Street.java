package biz.gelicon.core.components.core.street;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.components.core.streettype.StreetType;
import biz.gelicon.core.components.core.town.Town;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 15.07.2021 12:33 */
@Table(
    name = "street",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"town_id", "streettype_id", "street_name", "street_code"})
    }
)
@TableDescription("Улица")
public class Street {

    @Id
    @Column(name = "street_id",nullable = false)
    public Integer streetId;

    @ColumnDescription("Идентификатор города")
    @ManyToOne(targetEntity = Town.class)
    @Column(name = "town_id", nullable = false)
    @NotNull(message = "Поле \"Идентификатор города\" не может быть пустым")
    private Integer townId;

    @ColumnDescription("Тип улицы")
    @ManyToOne(targetEntity = StreetType.class)
    @Column(name = "streettype_id", nullable = false)
    @NotNull(message = "Поле \"Тип улицы\" не может быть пустым")
    private Integer streetTypeId;

    @ColumnDescription("Имя")
    @Column(name = "street_name", nullable = false)
    @Size(max = 50, message = "Поле \"Имя\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    private String streetName;

    @ColumnDescription("Код")
    @Column(name = "street_code", nullable = true)
    @Size(max = 20, message = "Поле \"Код\" должно содержать не более {max} символов")
    private String streetCode;

    @ColumnDescription("Код ГУИД")
    @Column(name = "street_aoid", nullable = true)
    @Size(max = 36, message = "Поле \"Код ГУИД\" должно содержать не более {max} символов")
    private String streetAoid;

    @ColumnDescription("Глобальный код ГУИД")
    @Column(name = "street_aoguid", nullable = true)
    @Size(max = 36, message = "Поле \"Глобальный код ГУИД\" должно содержать не более {max} символов")
    private String streetAoGuid;

    @ColumnDescription("Дата модификации")
    @Column(name = "street_upddate", nullable = false)
    @NotNull(message = "Поле \"Дата модификации\" не может быть пустым")
    private Date streetUpddate;

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer value) {
        this.streetId = value;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer value) {
        this.townId = value;
    }

    public Integer getStreetTypeId() {
        return streetTypeId;
    }

    public void setStreetTypeId(Integer value) {
        this.streetTypeId = value;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String value) {
        this.streetName = value;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String value) {
        this.streetCode = value;
    }

    public String getStreetAoid() {
        return streetAoid;
    }

    public void setStreetAoid(String value) {
        this.streetAoid = value;
    }

    public String getStreetAoGuid() {
        return streetAoGuid;
    }

    public void setStreetAoGuid(String value) {
        this.streetAoGuid = value;
    }

    public Date getStreetUpddate() {
        return streetUpddate;
    }

    public void setStreetUpddate(Date value) {
        this.streetUpddate = value;
    }


    public Street() {}

    public Street(Integer streetId, Integer townId, Integer streetTypeId, String streetName,
            String streetCode, String streetAoid, String streetAoGuid,
            Date streetUpddate) {
        this.streetId = streetId;
        this.townId = townId;
        this.streetTypeId = streetTypeId;
        this.streetName = streetName;
        this.streetCode = streetCode;
        this.streetAoid = streetAoid;
        this.streetAoGuid = streetAoGuid;
        this.streetUpddate = streetUpddate;
    }


}

