package biz.gelicon.core.components.erp.human;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.security.ACL;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Сущность Человек
 * @author dav
 * @version 11.08.2022
 */
@Table(name = "human")
@TableDescription("Человек")
public class Human {

    @Id
    @Column(name = "human_id",nullable = false)
    public Integer humanId;

    @Column(name = "human_abbr", nullable = false)
    @Size(max = 255, message = "Поле 'Обозначение' должно содержать не более {max} символов")
    @NotBlank(message = "Поле 'Обозначение' не может быть пустым")
    private String humanAbbr;

    @Column(name = "human_surname", nullable = true)
    @Size(max = 255, message = "Поле 'Фамилия' должно содержать не более {max} символов")
    private String humanSurname;

    @Column(name = "human_name", nullable = true)
    @Size(max = 255, message = "Поле 'Имя' должно содержать не более {max} символов")
    private String humanName;

    @Column(name = "human_patronymic", nullable = true)
    @Size(max = 255, message = "Поле 'Отчество' должно содержать не более {max} символов")
    private String humanPatronymic;

    @OneToMany(targetEntity = HumanGender.class, cascade = {CascadeType.REMOVE})
    @Column(name = "humangender_id", nullable = false)
    @NotNull(message = "Поле 'Пол' не может быть пустым")
    private Integer humangGnderId;

    @Column(name = "human_birthday", nullable = false)
    @NotBlank(message = "Поле 'Дата рождения' не может быть пустым")
    private Date humanBirthday;

    @Column(name = "human_inn", nullable = true)
    @Size(max = 255, message = "Поле 'ИНН' должно содержать не более {max} символов")
    private String humanInn;

    @Column(name = "human_note", nullable = true)
    @Size(max = 255, message = "Поле 'Примечание' должно содержать не более {max} символов")
    private String humanNote;

    public Human() {}

    public Human(Integer humanId, String humanAbbr, String humanSurname, String humanName,
            String humanPatronymic, Integer humangGnderId, Date humanBirthday, String humanInn,
            String humanNote) {
        this.humanId = humanId;
        this.humanAbbr = humanAbbr;
        this.humanSurname = humanSurname;
        this.humanName = humanName;
        this.humanPatronymic = humanPatronymic;
        this.humangGnderId = humangGnderId;
        this.humanBirthday = humanBirthday;
        this.humanInn = humanInn;
        this.humanNote = humanNote;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    public void setHumanAbbr(String humanAbbr) {
        this.humanAbbr = humanAbbr;
    }

    public void setHumanSurname(String humanSurname) {
        this.humanSurname = humanSurname;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public void setHumanPatronymic(String humanPatronymic) {
        this.humanPatronymic = humanPatronymic;
    }

    public void setHumangGnderId(Integer humangGnderId) {
        this.humangGnderId = humangGnderId;
    }

    public void setHumanBirthday(Date humanBirthday) {
        this.humanBirthday = humanBirthday;
    }

    public void setHumanInn(String humanInn) {
        this.humanInn = humanInn;
    }

    public void setHumanNote(String humanNote) {
        this.humanNote = humanNote;
    }

    public Integer getHumanId() {
        return humanId;
    }

    public String getHumanAbbr() {
        return humanAbbr;
    }

    public String getHumanSurname() {
        return humanSurname;
    }

    public String getHumanName() {
        return humanName;
    }

    public String getHumanPatronymic() {
        return humanPatronymic;
    }

    public Integer getHumangGnderId() {
        return humangGnderId;
    }

    public Date getHumanBirthday() {
        return humanBirthday;
    }

    public String getHumanInn() {
        return humanInn;
    }

    public String getHumanNote() {
        return humanNote;
    }
}

