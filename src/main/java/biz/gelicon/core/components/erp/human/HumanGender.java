package biz.gelicon.core.components.erp.human;

import biz.gelicon.core.annotations.TableDescription;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "humangender")
@TableDescription("Пол человека")
@Schema(description = "Пол человека 1 - муж, 2 - жен, 3 - нет")
public class HumanGender {
    //    humangender_id : INTEGER
    //  --
    //  * humangender_name : VARCHAR(20)
    //  * humangender_abbr

    @Id
    @Column(name = "humangender_id",nullable = false)
    public Integer humanGenderId;

    @Column(name = "humangender_name", nullable = false)
    @Size(max = 255, message = "Поле 'Наименование' должно содержать не более {max} символов")
    @NotBlank(message = "Поле 'Обозначение' не может быть пустым")
    private String humanGenderName;

    @Column(name = "humangender_abbr", nullable = false)
    @Size(max = 255, message = "Поле 'Обозначение' должно содержать не более {max} символов")
    private String humanGenderAbbr;

    public HumanGender() {}

    public HumanGender(Integer humanGenderId, String humanGenderName, String humanGenderAbbr) {
        this.humanGenderId = humanGenderId;
        this.humanGenderName = humanGenderName;
        this.humanGenderAbbr = humanGenderAbbr;
    }

    public void setHumanGenderId(Integer humanGenderId) {
        this.humanGenderId = humanGenderId;
    }

    public void setHumanGenderName(String humanGenderName) {
        this.humanGenderName = humanGenderName;
    }

    public void setHumanGenderAbbr(String humanGenderAbbr) {
        this.humanGenderAbbr = humanGenderAbbr;
    }

    public Integer getHumanGenderId() {
        return humanGenderId;
    }

    public String getHumanGenderName() {
        return humanGenderName;
    }

    public String getHumanGenderAbbr() {
        return humanGenderAbbr;
    }
}

