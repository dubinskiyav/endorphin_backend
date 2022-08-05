package biz.gelicon.core.components.core.worker;

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

/* Сущность сгенерирована 23.02.2022 12:00 */
@Table(
    name = "worker"
)
@TableDescription("Сотрудник")
public class Worker {

    @Id
    @Column(name = "worker_id",nullable = false)
    public Integer workerId;

    @ColumnDescription("Табельный номер")
    @Column(name = "worker_tabnumber", nullable = false)
    @Size(max = 15, message = "Поле \"Табельный номер\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Табельный номер\" не может быть пустым")
    private String workerTabnumber;

    @ColumnDescription("Фамилия")
    @Column(name = "worker_familyname", nullable = false)
    @Size(max = 30, message = "Поле \"Фамилия\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Фамилия\" не может быть пустым")
    private String workerFamilyname;

    @ColumnDescription("Имя")
    @Column(name = "worker_firstname", nullable = false)
    @Size(max = 30, message = "Поле \"Имя\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    private String workerFirstname;

    @ColumnDescription("Отчество")
    @Column(name = "worker_surname", nullable = true)
    @Size(max = 30, message = "Поле \"Отчество\" должно содержать не более {max} символов")
    private String workerSurname;

    @ColumnDescription("Пол")
    @Column(name = "worker_sex", nullable = false)
    @NotNull(message = "Поле \"Пол\" не может быть пустым")
    private Integer workerSex;

    @ColumnDescription("Дата рождения")
    @Column(name = "worker_birthday", nullable = true)
    private Date workerBirthday;

    @ColumnDescription("Электронная почта")
    @Column(name = "worker_email", nullable = true)
    @Size(max = 50, message = "Поле \"Электронная почта\" должно содержать не более {max} символов")
    private String workerEmail;

    @ColumnDescription("Домашний телефон")
    @Column(name = "worker_homephone", nullable = true)
    @Size(max = 50, message = "Поле \"Домашний телефон\" должно содержать не более {max} символов")
    private String workerHomephone;

    @ColumnDescription("Рабочий телефон")
    @Column(name = "worker_workphone", nullable = true)
    @Size(max = 50, message = "Поле \"Рабочий телефон\" должно содержать не более {max} символов")
    private String workerWorkphone;

    @ColumnDescription("Контактный телефон")
    @Column(name = "worker_contactphone", nullable = true)
    @Size(max = 50, message = "Поле \"Контактный телефон\" должно содержать не более {max} символов")
    private String workerContactphone;

    @ColumnDescription("Примечание")
    @Column(name = "worker_remark", nullable = true)
    @Size(max = 255, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String workerRemark;

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer value) {
        this.workerId = value;
    }

    public String getWorkerTabnumber() {
        return workerTabnumber;
    }

    public void setWorkerTabnumber(String value) {
        this.workerTabnumber = value;
    }

    public String getWorkerFamilyname() {
        return workerFamilyname;
    }

    public void setWorkerFamilyname(String value) {
        this.workerFamilyname = value;
    }

    public String getWorkerFirstname() {
        return workerFirstname;
    }

    public void setWorkerFirstname(String value) {
        this.workerFirstname = value;
    }

    public String getWorkerSurname() {
        return workerSurname;
    }

    public void setWorkerSurname(String value) {
        this.workerSurname = value;
    }

    public Integer getWorkerSex() {
        return workerSex;
    }

    public void setWorkerSex(Integer value) {
        this.workerSex = value;
    }

    public Date getWorkerBirthday() {
        return workerBirthday;
    }

    public void setWorkerBirthday(Date value) {
        this.workerBirthday = value;
    }

    public String getWorkerEmail() {
        return workerEmail;
    }

    public void setWorkerEmail(String value) {
        this.workerEmail = value;
    }

    public String getWorkerHomephone() {
        return workerHomephone;
    }

    public void setWorkerHomephone(String value) {
        this.workerHomephone = value;
    }

    public String getWorkerWorkphone() {
        return workerWorkphone;
    }

    public void setWorkerWorkphone(String value) {
        this.workerWorkphone = value;
    }

    public String getWorkerContactphone() {
        return workerContactphone;
    }

    public void setWorkerContactphone(String value) {
        this.workerContactphone = value;
    }

    public String getWorkerRemark() {
        return workerRemark;
    }

    public void setWorkerRemark(String value) {
        this.workerRemark = value;
    }

    public String getFIO() {
        return workerFamilyname+" "+workerFirstname.charAt(0)+"."+(workerSurname!=null?workerSurname.charAt(0)+".":"");
    }

    public Worker() {}

    public Worker(
            Integer workerId,
            String workerTabnumber,
            String workerFamilyname,
            String workerFirstname,
            String workerSurname,
            Integer workerSex,
            Date workerBirthday,
            String workerEmail,
            String workerHomephone,
            String workerWorkphone,
            String workerContactphone,
            String workerRemark) {
        this.workerId = workerId;
        this.workerTabnumber = workerTabnumber;
        this.workerFamilyname = workerFamilyname;
        this.workerFirstname = workerFirstname;
        this.workerSurname = workerSurname;
        this.workerSex = workerSex;
        this.workerBirthday = workerBirthday;
        this.workerEmail = workerEmail;
        this.workerHomephone = workerHomephone;
        this.workerWorkphone = workerWorkphone;
        this.workerContactphone = workerContactphone;
        this.workerRemark = workerRemark;
    }

}

