package biz.gelicon.core.components.erp.todo.patternnotification;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.capcode.CapCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/* Сущность сгенерирована 07.06.2021 18:33 */
@Table(
    name = "patternnotification",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"channelnotification_id", "eventnotification_id"})
    }
)
@TableDescription("Шаблон оповещения")
public class PatternNotification {

    @Id
    @Column(name = "patternnotification_id",nullable = false)
    public Integer patternnotificationId;

    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "channelnotification_id", nullable = false)
    private Integer channelNotificationId;

    @ManyToOne(targetEntity = CapCode.class)
    @Column(name = "eventnotification_id", nullable = false)
    private Integer eventnotificationId;

    @Column(name = "patternnotification_text", nullable = false)
    @Size(max = 2000, message = "Поле \"Текст шаблона\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Текст шаблона\" не может быть пустым")
    private String patternnotificationText;

    @Column(name = "patternnotification_subj", nullable = true)
    @Size(max = 255, message = "Поле \"Текст темы письма\" должно содержать не более {max} символов")
    private String patternnotificationSubj;

    public Integer getPatternnotificationId() {
        return patternnotificationId;
    }

    public void setPatternnotificationId(Integer value) {
        this.patternnotificationId = value;
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

    public String getPatternnotificationText() {
        return patternnotificationText;
    }

    public void setPatternnotificationText(String value) {
        this.patternnotificationText = value;
    }

    public String getPatternnotificationSubj() {
        return patternnotificationSubj;
    }

    public void setPatternnotificationSubj(String value) {
        this.patternnotificationSubj = value;
    }


    public PatternNotification() {}

    public PatternNotification(
            Integer patternnotificationId,
            Integer channelNotificationId,
            Integer eventnotificationId,
            String patternnotificationText,
            String patternnotificationSubj) {
        this.patternnotificationId = patternnotificationId;
        this.channelNotificationId = channelNotificationId;
        this.eventnotificationId = eventnotificationId;
        this.patternnotificationText = patternnotificationText;
        this.patternnotificationSubj = patternnotificationSubj;
    }
}

