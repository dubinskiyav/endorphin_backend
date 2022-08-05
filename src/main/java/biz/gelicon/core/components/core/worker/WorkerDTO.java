package biz.gelicon.core.components.core.worker;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.Date;

/* Объект сгенерирован 23.02.2022 12:00 */
@Schema(description = "Объект \"Сотрудник\"")
public class WorkerDTO {

    @Id
    @Schema(description = "Идентификатор \"Сотрудник\"")
    private Integer workerId;

    @Schema(description = "Табельный номер")
    private String workerTabnumber;

    @Schema(description = "Фамилия")
    private String workerFamilyname;

    @Schema(description = "Имя")
    private String workerFirstname;

    @Schema(description = "Отчество")
    private String workerSurname;

    @Schema(description = "Пол")
    private Integer workerSex;

    @Schema(description = "Дата рождения")
    private Date workerBirthday;

    @Schema(description = "Электронная почта")
    private String workerEmail;

    @Schema(description = "Домашний телефон")
    private String workerHomephone;

    @Schema(description = "Рабочий телефон")
    private String workerWorkphone;

    @Schema(description = "Контактный телефон")
    private String workerContactphone;

    @Schema(description = "Примечание")
    private String workerRemark;


    public WorkerDTO() {}

    public WorkerDTO(
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

    public WorkerDTO(Worker entity) {
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

    public Worker toEntity() {
        return toEntity(new Worker());
    }

    public Worker toEntity(Worker entity) {
        entity.setWorkerId(this.workerId);
        entity.setWorkerTabnumber(this.workerTabnumber);
        entity.setWorkerFamilyname(this.workerFamilyname);
        entity.setWorkerFirstname(this.workerFirstname);
        entity.setWorkerSurname(this.workerSurname);
        entity.setWorkerSex(this.workerSex);
        entity.setWorkerBirthday(this.workerBirthday);
        entity.setWorkerEmail(this.workerEmail);
        entity.setWorkerHomephone(this.workerHomephone);
        entity.setWorkerWorkphone(this.workerWorkphone);
        entity.setWorkerContactphone(this.workerContactphone);
        entity.setWorkerRemark(this.workerRemark);
        return entity;
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

