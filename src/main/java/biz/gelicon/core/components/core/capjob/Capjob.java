package biz.gelicon.core.components.core.capjob;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.components.core.capclass.CapClass;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.core.capresource.Artifact;
import biz.gelicon.core.components.core.daycal.Daycal;
import biz.gelicon.core.components.core.dayofweek.Dayofweek;
import biz.gelicon.core.components.core.monthcal.Monthcal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 19.07.2021 10:39 */
@Table(
    name = "capjob",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"capjob_name"})
    }
)
@TableDescription("Задание")
public class Capjob {

    @Id
    @Column(name = "capjob_id",nullable = false)
    public Integer capjobId;

    @ManyToOne(targetEntity = Artifact.class)
    @ColumnDescription("Ресурс ИД")
    @Column(name = "capresource_id", nullable = false)
    @NotNull(message = "Поле \"Ресурс ИД\" не может быть пустым")
    private Integer capresourceId;

    @ManyToOne(targetEntity = CapClass.class)
    @ColumnDescription("Тип ИД")
    @Column(name = "capjobtype_id", nullable = false)
    @NotNull(message = "Поле \"Тип ИД\" не может быть пустым")
    private Integer capjobtypeId;

    @ColumnDescription("Наименование")
    @Column(name = "capjob_name", nullable = false)
    @Size(max = 255, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование\" не может быть пустым")
    private String capjobName;

    @ColumnDescription("Активность")
    @Column(name = "capjob_flagactive", nullable = false)
    @NotNull(message = "Поле \"Активность\" не может быть пустым")
    private Integer capjobFlagactive;

    @ColumnDescription("Количество повторов при аварийном завершении")
    @Column(name = "capjob_failiterationcount", nullable = false)
    @NotNull(message = "Поле \"Количество повторов при аварийном завершении\" не может быть пустым")
    private Integer capjobFailiterationcount;

    @ColumnDescription("Интервал повтора при аварийном завершении")
    @Column(name = "capjob_failiterationinterval", nullable = true)
    private Integer capjobFailiterationinterval;

    @ColumnDescription("Примечание")
    @Column(name = "capjob_remark", nullable = true)
    @Size(max = 512, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String capjobRemark;

    @ManyToOne(targetEntity = CapCode.class)
    @ColumnDescription("Периодичность ИД")
    @Column(name = "period_id", nullable = false)
    @NotNull(message = "Поле \"Периодичность ИД\" не может быть пустым")
    private Integer periodId;

    @ColumnDescription("Периодичность")
    @Column(name = "capjob_interval", nullable = false)
    @NotNull(message = "Поле \"Периодичность\" не может быть пустым")
    private Integer capjobInterval;

    @ColumnDescription("Дата начала")
    @Column(name = "capjob_datebeg", nullable = false)
    @NotNull(message = "Поле \"Дата начала\" не может быть пустым")
    private Date capjobDatebeg;

    @ColumnDescription("Дата окончания")
    @Column(name = "capjob_dateend", nullable = true)
    private Date capjobDateend;

    @ColumnDescription("Количество повторений")
    @Column(name = "capjob_iterationcount", nullable = true)
    private Integer capjobIterationcount;

    @ColumnDescription("Дни Месяцы")
    @Column(name = "capjob_days", nullable = true)
    @Size(max = 128, message = "Поле \"Дни Месяцы\" должно содержать не более {max} символов")
    private String capjobDays;

    @ManyToOne(targetEntity = Daycal.class)
    @ColumnDescription("День календаря_ИД")
    @Column(name = "daycal_id", nullable = true)
    private Integer daycalId;

    @ColumnDescription("Неделя")
    @Column(name = "capjob_weeknumber", nullable = true)
    private Integer capjobWeeknumber;

    @ManyToOne(targetEntity = Dayofweek.class)
    @ColumnDescription("День недели календаря ИД")
    @Column(name = "dayofweek_id", nullable = true)
    private Integer dayofweekId;

    @ManyToOne(targetEntity = Monthcal.class)
    @ColumnDescription("Месяц календаря_ИД")
    @Column(name = "monthcal_id", nullable = true)
    private Integer monthcalId;

    public Integer getCapjobId() {
        return capjobId;
    }

    public void setCapjobId(Integer value) {
        this.capjobId = value;
    }

    public Integer getCapresourceId() {
        return capresourceId;
    }

    public void setCapresourceId(Integer value) {
        this.capresourceId = value;
    }

    public Integer getCapjobtypeId() {
        return capjobtypeId;
    }

    public void setCapjobtypeId(Integer value) {
        this.capjobtypeId = value;
    }

    public String getCapjobName() {
        return capjobName;
    }

    public void setCapjobName(String value) {
        this.capjobName = value;
    }

    public Integer getCapjobFlagactive() {
        return capjobFlagactive;
    }

    public void setCapjobFlagactive(Integer value) {
        this.capjobFlagactive = value;
    }

    public Integer getCapjobFailiterationcount() {
        return capjobFailiterationcount;
    }

    public void setCapjobFailiterationcount(Integer value) {
        this.capjobFailiterationcount = value;
    }

    public Integer getCapjobFailiterationinterval() {
        return capjobFailiterationinterval;
    }

    public void setCapjobFailiterationinterval(Integer value) {
        this.capjobFailiterationinterval = value;
    }

    public String getCapjobRemark() {
        return capjobRemark;
    }

    public void setCapjobRemark(String value) {
        this.capjobRemark = value;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer value) {
        this.periodId = value;
    }

    public Integer getCapjobInterval() {
        return capjobInterval;
    }

    public void setCapjobInterval(Integer value) {
        this.capjobInterval = value;
    }

    public Date getCapjobDatebeg() {
        return capjobDatebeg;
    }

    public void setCapjobDatebeg(Date value) {
        this.capjobDatebeg = value;
    }

    public Date getCapjobDateend() {
        return capjobDateend;
    }

    public void setCapjobDateend(Date value) {
        this.capjobDateend = value;
    }

    public Integer getCapjobIterationcount() {
        return capjobIterationcount;
    }

    public void setCapjobIterationcount(Integer value) {
        this.capjobIterationcount = value;
    }

    public String getCapjobDays() {
        return capjobDays;
    }

    public void setCapjobDays(String value) {
        this.capjobDays = value;
    }

    public Integer getDaycalId() {
        return daycalId;
    }

    public void setDaycalId(Integer value) {
        this.daycalId = value;
    }

    public Integer getCapjobWeeknumber() {
        return capjobWeeknumber;
    }

    public void setCapjobWeeknumber(Integer value) {
        this.capjobWeeknumber = value;
    }

    public Integer getDayofweekId() {
        return dayofweekId;
    }

    public void setDayofweekId(Integer value) {
        this.dayofweekId = value;
    }

    public Integer getMonthcalId() {
        return monthcalId;
    }

    public void setMonthcalId(Integer value) {
        this.monthcalId = value;
    }


    public Capjob() {}

    public Capjob(
            Integer capjobId,
            Integer capresourceId,
            Integer capjobtypeId,
            String capjobName,
            Integer capjobFlagactive,
            Integer capjobFailiterationcount,
            Integer capjobFailiterationinterval,
            String capjobRemark,
            Integer periodId,
            Integer capjobInterval,
            Date capjobDatebeg,
            Date capjobDateend,
            Integer capjobIterationcount,
            String capjobDays,
            Integer daycalId,
            Integer capjobWeeknumber,
            Integer dayofweekId,
            Integer monthcalId) {
        this.capjobId = capjobId;
        this.capresourceId = capresourceId;
        this.capjobtypeId = capjobtypeId;
        this.capjobName = capjobName;
        this.capjobFlagactive = capjobFlagactive;
        this.capjobFailiterationcount = capjobFailiterationcount;
        this.capjobFailiterationinterval = capjobFailiterationinterval;
        this.capjobRemark = capjobRemark;
        this.periodId = periodId;
        this.capjobInterval = capjobInterval;
        this.capjobDatebeg = capjobDatebeg;
        this.capjobDateend = capjobDateend;
        this.capjobIterationcount = capjobIterationcount;
        this.capjobDays = capjobDays;
        this.daycalId = daycalId;
        this.capjobWeeknumber = capjobWeeknumber;
        this.dayofweekId = dayofweekId;
        this.monthcalId = monthcalId;
    }
}

