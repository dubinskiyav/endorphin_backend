package biz.gelicon.core.components.core.capjobhistory;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.annotations.ColumnDescription;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.core.capjob.Capjob;
import biz.gelicon.core.components.core.capresource.Artifact;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 19.07.2021 14:46 */
@Table(name = "capjobhistory")
@TableDescription("История выполнения заданий")
public class Capjobhistory {

    @Id
    @Column(name = "capjobhistory_id",nullable = false)
    public Integer capjobhistoryId;

    @ManyToOne(targetEntity = Capjob.class)
    @ColumnDescription("Задание ИД")
    @Column(name = "capjob_id", nullable = true)
    private Integer capjobId;

    @ManyToOne(targetEntity = CapCode.class)
    @ColumnDescription("Состояние задания ИД")
    @Column(name = "capjobstate_id", nullable = false)
    @NotNull(message = "Поле \"Состояние задания ИД\" не может быть пустым")
    private Integer capjobstateId;

    @ColumnDescription("Дата начала")
    @Column(name = "capjobhistory_datebeg", nullable = false)
    @NotNull(message = "Поле \"Дата начала\" не может быть пустым")
    private Date capjobhistoryDatebeg;

    @ColumnDescription("Дата окончания")
    @Column(name = "capjobhistory_dateend", nullable = true)
    private Date capjobhistoryDateend;

    @ColumnDescription("Текст ошибки")
    @Column(name = "capjobhistory_errmsg", nullable = true)
    @Size(max = 2000, message = "Поле \"Текст ошибки\" должно содержать не более {max} символов")
    private String capjobhistoryErrmsg;

    @ColumnDescription("Наименование")
    @Column(name = "capjob_name", nullable = true)
    @Size(max = 255, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    private String capjobhistoryCapjobName;

    @ManyToOne(targetEntity = Artifact.class)
    @ColumnDescription("Ресурс ИД")
    @Column(name = "capresource_id", nullable = true)
    private Integer capresourceId;

    public Integer getCapjobhistoryId() {
        return capjobhistoryId;
    }

    public void setCapjobhistoryId(Integer value) {
        this.capjobhistoryId = value;
    }

    public Integer getCapjobId() {
        return capjobId;
    }

    public void setCapjobId(Integer value) {
        this.capjobId = value;
    }

    public Integer getCapjobstateId() {
        return capjobstateId;
    }

    public void setCapjobstateId(Integer value) {
        this.capjobstateId = value;
    }

    public Date getCapjobhistoryDatebeg() {
        return capjobhistoryDatebeg;
    }

    public void setCapjobhistoryDatebeg(Date value) {
        this.capjobhistoryDatebeg = value;
    }

    public Date getCapjobhistoryDateend() {
        return capjobhistoryDateend;
    }

    public void setCapjobhistoryDateend(Date value) {
        this.capjobhistoryDateend = value;
    }

    public String getCapjobhistoryErrmsg() {
        return capjobhistoryErrmsg;
    }

    public void setCapjobhistoryErrmsg(String value) {
        this.capjobhistoryErrmsg = value;
    }

    public String getCapjobhistoryCapjobName() {
        return capjobhistoryCapjobName;
    }

    public void setCapjobhistoryCapjobName(String value) {
        this.capjobhistoryCapjobName = value;
    }

    public Integer getCapresourceId() {
        return capresourceId;
    }

    public void setCapresourceId(Integer value) {
        this.capresourceId = value;
    }


    public Capjobhistory() {}

    public Capjobhistory(
            Integer capjobhistoryId,
            Integer capjobId,
            Integer capjobstateId,
            Date capjobhistoryDatebeg,
            Date capjobhistoryDateend,
            String capjobhistoryErrmsg,
            String capjobhistoryCapjobName,
            Integer capresourceId) {
        this.capjobhistoryId = capjobhistoryId;
        this.capjobId = capjobId;
        this.capjobstateId = capjobstateId;
        this.capjobhistoryDatebeg = capjobhistoryDatebeg;
        this.capjobhistoryDateend = capjobhistoryDateend;
        this.capjobhistoryErrmsg = capjobhistoryErrmsg;
        this.capjobhistoryCapjobName = capjobhistoryCapjobName;
        this.capresourceId = capresourceId;
    }
}

