package biz.gelicon.core.components.core.towntype;

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

/* Сущность сгенерирована 15.07.2021 12:14 */
@Table(
    name = "towntype",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"towntype_name"}),
        @UniqueConstraint(columnNames = {"towntype_code"})
    }
)
@TableDescription("Тип населенного пункта")
public class TownType {
    public static final int CITY = 1;
    public static final int VILLAGE = 2;

    @Id
    @Column(name = "towntype_id",nullable = false)
    public Integer townTypeId;

    @ColumnDescription("Наименование")
    @Column(name = "towntype_name", nullable = false)
    @Size(max = 50, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String townTypeName;

    @ColumnDescription("Код")
    @Column(name = "towntype_code", nullable = false)
    @Size(max = 20, message = "Поле \"Код\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Код\" не может быть пустым")
    private String townTypeCode;

    public Integer getTownTypeId() {
        return townTypeId;
    }

    public void setTownTypeId(Integer value) {
        this.townTypeId = value;
    }

    public String getTownTypeName() {
        return townTypeName;
    }

    public void setTownTypeName(String value) {
        this.townTypeName = value;
    }

    public String getTownTypeCode() {
        return townTypeCode;
    }

    public void setTownTypeCode(String value) {
        this.townTypeCode = value;
    }


    public TownType() {}

    public TownType(
            Integer townTypeId,
            String townTypeName,
            String townTypeCode) {
        this.townTypeId = townTypeId;
        this.townTypeName = townTypeName;
        this.townTypeCode = townTypeCode;
    }
}

