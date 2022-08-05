package biz.gelicon.core.components.core.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import java.util.Date;

/* Объект сгенерирован 23.06.2021 10:32 */
@Schema(description = "Представление объекта \"Оповещение\"")
public class NotificationView {

    @Schema(description = "Идентификатор \"Оповещение\"")
    @Column(name="notification_id")
    public Integer notificationId;

    @Schema(description = "Пользователь ИД")
    @Column(name="proguser_id")
    private Integer proguserId;

    @Schema(description = "Канал оповещения ИД")
    @Column(name="channelnotification_id")
    private Integer channelNotificationId;

    @Schema(description = "Событие оповещения ИД")
    @Column(name="eventnotification_id")
    private Integer eventnotificationId;

    @Schema(description = "Адрес отправки")
    @Column(name="notification_address")
    private String notificationAddress;

    @Schema(description = "Текст сообщения")
    @Column(name="notification_text")
    private String notificationText;

    @Schema(description = "Дата оповещения")
    @Column(name="notification_date")
    private Date notificationDate;

    @Schema(description = "Статус")
    @Column(name="notification_status")
    private Integer notificationStatus;

    @Schema(description = "Дата отправки")
    @Column(name="notification_datesend")
    private Date notificationDatesend;

    @Schema(description = "Тема письма")
    @Column(name="notification_subj")
    private String notificationSubj;

    @Schema(description = "Ссылка на связанную сущность")
    @Column(name="notification_linkobj_id")
    private Integer notificationLinkobjId;

    @Schema(description = "Дата прочтения")
    @Column(name="notification_dateread")
    private Date notificationDateread;

    @Schema(description = "Ошибка отправки")
    @Column(name="notification_senderror")
    private String notificationSenderror;

    @Schema(description = "Счетчик отправлений")
    @Column(name="notification_sendcounter")
    private Integer notificationSendcounter;


    public NotificationView() {}

    public NotificationView(
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

    public NotificationView(Notification entity) {
        this.notificationId = entity.getNotificationId();
        this.proguserId = entity.getProguserId();
        this.channelNotificationId = entity.getChannelNotificationId();
        this.eventnotificationId = entity.getEventnotificationId();
        this.notificationAddress = entity.getNotificationAddress();
        this.notificationText = entity.getNotificationText();
        this.notificationDate = entity.getNotificationDate();
        this.notificationStatus = entity.getNotificationStatus();
        this.notificationDatesend = entity.getNotificationDatesend();
        this.notificationSubj = entity.getNotificationSubj();
        this.notificationLinkobjId = entity.getNotificationLinkobjId();
        this.notificationDateread = entity.getNotificationDateread();
        this.notificationSenderror = entity.getNotificationSenderror();
        this.notificationSendcounter = entity.getNotificationSendcounter();
    }

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


}

