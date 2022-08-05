package biz.gelicon.core.components.erp.todo.notificationsetting;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.core.proguser.Proguser;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/* Сущность сгенерирована 07.06.2021 18:33 */
@Table(
    name = "notificationsetting",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"proguser_id", "eventnotification_id", "channelnotification_id"})
    }
)
@TableDescription("Настройка оповещения")
public class NotificationSetting {

    @Id
    @Column(name = "notificationsetting_id",nullable = false)
    public Integer notificationsettingId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    private Integer proguserId;

    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "eventnotification_id", nullable = false)
    private Integer eventnotificationId;

    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "channelnotification_id", nullable = false)
    private Integer channelNotificationId;

    public Integer getNotificationsettingId() {
        return notificationsettingId;
    }

    public void setNotificationsettingId(Integer value) {
        this.notificationsettingId = value;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public void setProguserId(Integer value) {
        this.proguserId = value;
    }

    public Integer getEventnotificationId() {
        return eventnotificationId;
    }

    public void setEventnotificationId(Integer value) {
        this.eventnotificationId = value;
    }

    public Integer getChannelNotificationId() {
        return channelNotificationId;
    }

    public void setChannelNotificationId(Integer value) {
        this.channelNotificationId = value;
    }


    public NotificationSetting() {}

    public NotificationSetting(
            Integer notificationsettingId,
            Integer proguserId,
            Integer eventnotificationId,
            Integer channelNotificationId) {
        this.notificationsettingId = notificationsettingId;
        this.proguserId = proguserId;
        this.eventnotificationId = eventnotificationId;
        this.channelNotificationId = channelNotificationId;
    }
}

