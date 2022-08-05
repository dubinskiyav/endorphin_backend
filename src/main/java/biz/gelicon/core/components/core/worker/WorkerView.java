package biz.gelicon.core.components.core.worker;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 23.02.2022 12:00 */
@Schema(description = "Представление объекта \"Сотрудник\"")
public class WorkerView {

    @Schema(description = "Идентификатор \"Сотрудник\"")
    @Column(name="worker_id")
    public Integer workerId;

    @Schema(description = "Табельный номер")
    @Column(name="worker_tabnumber")
    private String workerTabnumber;

    @Schema(description = "Фамилия")
    @Column(name="worker_familyname")
    private String workerFamilyname;

    @Schema(description = "Имя")
    @Column(name="worker_firstname")
    private String workerFirstname;

    @Schema(description = "Отчество")
    @Column(name="worker_surname")
    private String workerSurname;

    @Schema(description = "Пол")
    @Column(name="worker_sex")
    private Integer workerSex;

    @Schema(description = "Дата рождения")
    @Column(name="worker_birthday")
    private Date workerBirthday;

    @Schema(description = "Электронная почта")
    @Column(name="worker_email")
    private String workerEmail;

    @Schema(description = "Домашний телефон")
    @Column(name="worker_homephone")
    private String workerHomephone;

    @Schema(description = "Рабочий телефон")
    @Column(name="worker_workphone")
    private String workerWorkphone;

    @Schema(description = "Контактный телефон")
    @Column(name="worker_contactphone")
    private String workerContactphone;

    @Schema(description = "Примечание")
    @Column(name="worker_remark")
    private String workerRemark;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getFIO() {
        return workerFamilyname+" "+workerFirstname.charAt(0)+"."+(workerSurname!=null&&workerSurname.length()!=0?workerSurname.charAt(0)+".":"");
    }


    public WorkerView() {}

    public WorkerView(
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

    public WorkerView(Worker entity) {
        this.workerId = entity.getWorkerId();
        this.workerTabnumber = entity.getWorkerTabnumber();
        this.workerFamilyname = entity.getWorkerFamilyname();
        this.workerFirstname = entity.getWorkerFirstname();
        this.workerSurname = entity.getWorkerSurname();
        this.workerSex = entity.getWorkerSex();
        this.workerBirthday = entity.getWorkerBirthday();
        this.workerEmail = entity.getWorkerEmail();
        this.workerHomephone = entity.getWorkerHomephone();
        this.workerWorkphone = entity.getWorkerWorkphone();
        this.workerContactphone = entity.getWorkerContactphone();
        this.workerRemark = entity.getWorkerRemark();
    }

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


}

