package biz.gelicon.core.components.erp.todo.uniquetype;

import biz.gelicon.core.annotations.TableDescription;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 04.06.2021 10:39 */
@Table(
    name = "uniquetype",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"uniquetype_name"})
    }
)
@TableDescription("Тип уникальности")
public class UniqueType {
    public static final  int UNQ_NOUNIQUE = 1;
    public static final  int UNQ_THROUGH = 1;
    public static final  int UNQ_YEAR = 2;
    public static final  int UNQ_QUARTER = 3;
    public static final  int UNQ_MONTH = 4;
    public static final  int UNQ_DAY = 5;

    @Id
    @Column(name = "uniquetype_id",nullable = false)
    public Integer uniqueTypeId;

    @Column(name = "uniquetype_name", nullable = false)
    @Size(max = 20, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String uniqueTypeName;

    public Integer getUniqueTypeId() {
        return uniqueTypeId;
    }

    public void setUniqueTypeId(Integer value) {
        this.uniqueTypeId = value;
    }

    public String getUniqueTypeName() {
        return uniqueTypeName;
    }

    public void setUniqueTypeName(String value) {
        this.uniqueTypeName = value;
    }


    public UniqueType() {}

    public UniqueType(
            Integer uniqueTypeId,
            String uniqueTypeName) {
        this.uniqueTypeId = uniqueTypeId;
        this.uniqueTypeName = uniqueTypeName;
    }
}

