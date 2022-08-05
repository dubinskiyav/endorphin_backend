package biz.gelicon.core.components.core.townsubordinate;

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

/* Сущность сгенерирована 26.07.2021 14:11 */
@Table(
    name = "townsubordinate",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"townsubordinate_name"})
    }
)
@TableDescription("Тип подчиненности города")
public class TownSubordinate {
    public static final int WITHOUT_SUBORDINATE = 1;
    public static final int CAPITAL_OF_REGION = 2;
    public static final int REGION_SUBORDINATE = 3;


    @Id
    @Column(name = "townsubordinate_id",nullable = false)
    public Integer townSubordinateId;

    @ColumnDescription("Наименование")
    @Column(name = "townsubordinate_name", nullable = false)
    @Size(max = 50, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String townSubordinateName;

    public Integer getTownSubordinateId() {
        return townSubordinateId;
    }

    public void setTownSubordinateId(Integer value) {
        this.townSubordinateId = value;
    }

    public String getTownSubordinateName() {
        return townSubordinateName;
    }

    public void setTownSubordinateName(String value) {
        this.townSubordinateName = value;
    }


    public TownSubordinate() {}

    public TownSubordinate(
            Integer townSubordinateId,
            String townSubordinateName) {
        this.townSubordinateId = townSubordinateId;
        this.townSubordinateName = townSubordinateName;
    }
}

