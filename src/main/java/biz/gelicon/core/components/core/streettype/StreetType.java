package biz.gelicon.core.components.core.streettype;

import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table(name = "streettype")
@TableDescription("Тип улицы")
public class StreetType {

    @Id
    @Column(name = "streettype_id",nullable = false)
    public Integer streetTypeId;

    @ColumnDescription("Имя")
    @Column(name = "streettype_name", nullable = false)
    @Size(max = 50, message = "Поле \"Имя\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    private String streetTypeName;

    @ColumnDescription("Код")
    @Column(name = "streettype_code", nullable = true)
    @Size(max = 10, message = "Поле \"Код\" должно содержать не более {max} символов")
    private String streetTypeCode;

    public Integer getStreetTypeId() {
        return streetTypeId;
    }

    public void setStreetTypeId(Integer value) {
        this.streetTypeId = value;
    }

    public String getStreetTypeName() {
        return streetTypeName;
    }

    public void setStreetTypeName(String value) {
        this.streetTypeName = value;
    }

    public String getStreetTypeCode() {
        return streetTypeCode;
    }

    public void setStreetTypeCode(String value) {
        this.streetTypeCode = value;
    }


    public StreetType() {}

}

