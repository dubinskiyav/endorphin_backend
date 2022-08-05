package biz.gelicon.core.components.core.notification;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.core.proguser.Proguser;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 23.06.2021 10:32 */
@Table(name = "notification")
@TableDescription("Оповещение")
public class Notification {

    @Id
    @Column(name = "notification_id",nullable = false)
    public Integer notificationId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    private Integer proguserId;

    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "channelnotification_id", nullable = false)
    private Integer channelNotificationId;

    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "eventnotification_id", nullable = false)
    private Integer eventnotificationId;

    @Column(name = "notification_address", nullable = true)
    @Size(max = 125, message = "Поле \"Адрес отправки\" должно содержать не более {max} символов")
    private String notificationAddress;

    @Column(name = "notification_text", nullable = false)
    @Size(max = 2000, message = "Поле \"Текст сообщения\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Текст сообщения\" не может быть пустым")
    private String notificationText;

    @Column(name = "notification_date", nullable = false)
    @NotNull(message = "Поле \"Дата оповещения\" не может быть пустым")
    private Date notificationDate;

    @Column(name = "notification_status", nullable = false)
    @NotNull(message = "Поле \"Статус\" не может быть пустым")
    private Integer notificationStatus;

    @Column(name = "notification_datesend", nullable = true)
    private Date notificationDatesend;

    @Column(name = "notification_subj", nullable = true)
    @Size(max = 255, message = "Поле \"Тема письма\" должно содержать не более {max} символов")
    private String notificationSubj;

    @Column(name = "notification_linkobj_id", nullable = true)
    private Integer notificationLinkobjId;

    @Column(name = "notification_dateread", nullable = true)
    private Date notificationDateread;

    @Column(name = "notification_senderror", nullable = true)
    @Size(max = 255, message = "Поле \"Ошибка отправки\" должно содержать не более {max} символов")
    private String notificationSenderror;

    @Column(name = "notification_sendcounter", nullable = false)
    @NotNull(message = "Поле \"Счетчик отправлений\" не может быть пустым")
    private Integer notificationSendcounter;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer value) {
        this.notificationId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Integer getChannelNotificationId() {
        return channelNotificationId;
    }

    public void setChannelNotificationId(Integer value) {
        this.channelNotificationId = value;
    }

    public Integer getEventnotificationId() {
        return eventnotificationId;
    }

    public void setEventnotificationId(Integer value) {
        this.eventnotificationId = value;
    }

    public String getNotificationAddress() {
        return notificationAddress;
    }

    public void setNotificationAddress(String value) {
        this.notificationAddress = value;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String value) {
        this.notificationText = value;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date value) {
        this.notificationDate = value;
    }

    public Integer getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Integer value) {
        this.notificationStatus = value;
    }

    public Date getNotificationDatesend() {
        return notificationDatesend;
    }

    public void setNotificationDatesend(Date value) {
        this.notificationDatesend = value;
    }

    public String getNotificationSubj() {
        return notificationSubj;
    }

    public void setNotificationSubj(String value) {
        this.notificationSubj = value;
    }

    public Integer getNotificationLinkobjId() {
        return notificationLinkobjId;
    }

    public void setNotificationLinkobjId(Integer value) {
        this.notificationLinkobjId = value;
    }

    public Date getNotificationDateread() {
        return notificationDateread;
    }

    public void setNotificationDateread(Date value) {
        this.notificationDateread = value;
    }

    public String getNotificationSenderror() {
        return notificationSenderror;
    }

    public void setNotificationSenderror(String value) {
        this.notificationSenderror = value;
    }

    public Integer getNotificationSendcounter() {
        return notificationSendcounter;
    }

    public void setNotificationSendcounter(Integer value) {
        this.notificationSendcounter = value;
    }


    public Notification() {}

    public Notification(
            Integer notificationId,
            Integer proguserId,
            Integer channelNotificationId,
            Integer eventnotificationId,
            String notificationAddress,
            String notificationText,
            Date notificationDate,
            Integer notificationStatus,
            Date notificationDatesend,
            String notificationSubj,
            Integer notificationLinkobjId,
            Date notificationDateread,
            String notificationSenderror,
            Integer notificationSendcounter) {
        this.notificationId = notificationId;
        this.proguserId = proguserId;
        this.channelNotificationId = channelNotificationId;
        this.eventnotificationId = eventnotificationId;
        this.notificationAddress = notificationAddress;
        this.notificationText = notificationText;
        this.notificationDate = notificationDate;
        this.notificationStatus = notificationStatus;
        this.notificationDatesend = notificationDatesend;
        this.notificationSubj = notificationSubj;
        this.notificationLinkobjId = notificationLinkobjId;
        this.notificationDateread = notificationDateread;
        this.notificationSenderror = notificationSenderror;
        this.notificationSendcounter = notificationSendcounter;
    }
}

