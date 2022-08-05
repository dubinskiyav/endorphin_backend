package biz.gelicon.core.components.erp.todo.sgoodtype;

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

/* Сущность сгенерирована 10.06.2021 19:29 */
@Table(
    name = "sgoodtype",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sgoodtype_name"})
    }
)
@TableDescription("Тип ТМЦ")
public class Sgoodtype {
    public static final int FOLDER_SGOODTYPE_ID = 1;
    public static final int COMMODITY_SGOODTYPE_ID = 2;
    public static final int SERVICE_SGOODTYPE_ID = 3;

    @Id
    @Column(name = "sgoodtype_id",nullable = false)
    public Integer sgoodtypeId;

    @Column(name = "sgoodtype_name", nullable = false)
    @Size(max = 30, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String sgoodtypeName;

    @Column(name = "sgoodtype_sortcode", nullable = true)
    @Size(max = 10, message = "Поле \"Код сортировки\" должно содержать не более {max} символов")
    private String sgoodtypeSortcode;

    public Integer getSgoodtypeId() {
        return sgoodtypeId;
    }

    public void setSgoodtypeId(Integer value) {
        this.sgoodtypeId = value;
    }

    public String getSgoodtypeName() {
        return sgoodtypeName;
    }

    public void setSgoodtypeName(String value) {
        this.sgoodtypeName = value;
    }

    public String getSgoodtypeSortcode() {
        return sgoodtypeSortcode;
    }

    public void setSgoodtypeSortcode(String value) {
        this.sgoodtypeSortcode = value;
    }


    public Sgoodtype() {}

    public Sgoodtype(
            Integer sgoodtypeId,
            String sgoodtypeName,
            String sgoodtypeSortcode) {
        this.sgoodtypeId = sgoodtypeId;
        this.sgoodtypeName = sgoodtypeName;
        this.sgoodtypeSortcode = sgoodtypeSortcode;
    }
}

