package biz.gelicon.core.components.erp.todo.notificationappendix;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.notification.Notification;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/* Сущность сгенерирована 01.07.2021 14:38 */
@Table(name = "notificationappendix")
@TableDescription("Приложение к оповещению")
public class NotificationAppendix {

    @Id
    @Column(name = "notificationappendix_id",nullable = false)
    public Integer notificationappendixId;

    @ManyToOne(targetEntity = Notification.class)
    @Column(name = "notification_id", nullable = false)
    @NotNull(message = "Поле \"Опопвещение ИД\" не может быть пустым")
    private Integer notificationId;

    @Column(name = "notificationappendix_name", nullable = false)
    @Size(max = 255, message = "Поле \"Наименование файла\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Наименование файла\" не может быть пустым")
    private String notificationappendixName;

    @Column(name = "notificationappendix_data", nullable = true)
    private byte[] notificationappendixData;

    public Integer getNotificationappendixId() {
        return notificationappendixId;
    }

    public void setNotificationappendixId(Integer value) {
        this.notificationappendixId = value;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer value) {
        this.notificationId = value;
    }

    public String getNotificationappendixName() {
        return notificationappendixName;
    }

    public void setNotificationappendixName(String value) {
        this.notificationappendixName = value;
    }

    public byte[] getNotificationappendixData() {
        return notificationappendixData;
    }

    public void setNotificationappendixData(byte[] value) {
        this.notificationappendixData = value;
    }


    public NotificationAppendix() {}

    public NotificationAppendix(
            Integer notificationappendixId,
            Integer notificationId,
            String notificationappendixName,
            byte[] notificationappendixData) {
        this.notificationappendixId = notificationappendixId;
        this.notificationId = notificationId;
        this.notificationappendixName = notificationappendixName;
        this.notificationappendixData = notificationappendixData;
    }
}

