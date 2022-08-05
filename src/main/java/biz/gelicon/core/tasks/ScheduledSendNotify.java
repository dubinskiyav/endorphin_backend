package biz.gelicon.core.tasks;
import biz.gelicon.core.MainApplication;
import biz.gelicon.core.components.erp.todo.notificationappendix.NotificationAppendix;
import biz.gelicon.core.components.core.proguserchannel.ProguserChannel;
import biz.gelicon.core.components.erp.todo.notificationappendix.NotificationAppendixRepository;
import biz.gelicon.core.components.core.proguser.ProguserService;
import biz.gelicon.core.utils.BlobDataSource;

import org.slf4j.Logger;
import biz.gelicon.core.components.core.notification.Notification;
import biz.gelicon.core.components.core.notification.NotificationRepository;
import biz.gelicon.core.service.MailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.transaction.Transactional;
import javax.activation.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static biz.gelicon.core.components.core.capcode.CapCode.CHANNEL_EMAIL;


@Component
public class ScheduledSendNotify {
    @Autowired
    private static ProguserService proguserService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationAppendixRepository notificationappendixRepository;
    @Autowired
    private MailService mailService;
    @Value("${gelicon.core.notification.numberOfAttempts}")
    private Integer numberAttempts;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledSendNotify.class);

    /*
     * Запускает сканирования Notification и отправляет письма.
     * Запуск, каждые 5 минут
     */
    @Scheduled(cron = "${gelicon.core.task.shedule.sendnotify}")
    @Transactional
    public void process() {
        Map<String, Object> params = new HashMap<>();
        params.put("channelnotification_id", CHANNEL_EMAIL);
        params.put("notification_status", 0);
        String where = "m0.channelnotification_id=:channelnotification_id and m0.notification_status=:notification_status";

        List<Notification> n = notificationRepository.findWhere(where, params);
        n.stream().forEach(s -> {
            String textInfo = new Date() + " Send NOTIFICATION";
            logger.info(textInfo);
            int errors = 0;
            String sError = "";
            int notificationId = s.getNotificationId();
            //MimeMultipart multipart = new MimeMultipart();

            //Сформировать вложение
            Map<String, Object> paramsa = new HashMap<>();
            paramsa.put("notification_id", notificationId);
            String wherea = "m0.notification_id=:notification_id";
            List<NotificationAppendix> na = notificationappendixRepository.findWhere(wherea, paramsa);

            List<MimeBodyPart> listAppendix = new ArrayList<>();
            na.stream().forEach(sa -> {
                try {
                    javax.sql.DataSource dataSource = MainApplication.getApplicationContext().getBean(javax.sql.DataSource.class);
                    PreparedStatement ps = dataSource.getConnection().prepareStatement("SELECT notificationappendix_data FROM notificationAppendix WHERE notificationappendix_id = ?");
                    ps.setInt(1, (Integer) sa.getNotificationappendixId());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        InputStream is = rs.getBinaryStream(1);
                        if (is != null) {
                            // Сформировать вложение
                            MimeBodyPart appendix = new MimeBodyPart();
                            appendix.setFileName(sa.getNotificationappendixName());
                            try {
                                DataSource ds = new BlobDataSource(is, sa.getNotificationappendixName());
                                appendix.setDataHandler(new DataHandler(ds));
                                listAppendix.add(appendix);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

            while (errors <= numberAttempts) {
                try {
                    mailService.sendEmail(s.getNotificationSubj(), s.getNotificationText(), s.getNotificationAddress(),listAppendix);
                    errors = 0;
                    break;
                } catch (Exception ex1) {
                    sError = ex1.getMessage();
                    logger.error(" ERROR (" + s.getNotificationAddress() + "): ", ex1);
                    errors++;
                }
            }

            if (errors > 0) {
                s.setNotificationSendcounter(errors);
                s.setNotificationSenderror(sError.substring(0, 254));
                s.setNotificationStatus(2);
                // Отправить уведомление администратору
                try {
                    sendToAdmin(sError, mailService);
                } catch (Exception ex1) {
                    logger.error("send to admin error", ex1);
                }
            } else {
                s.setNotificationStatus(1);
                s.setNotificationDatesend(new Date());
            }
            notificationRepository.update(s);
        });

    }


    private static boolean sendToAdmin(String textMessage, MailService ms) {
        //Получим почту администратора (то пользователь с ИД = 1)
        ProguserChannel email = proguserService.getlEmail(1);
        if (email != null) {
            ms.sendEmail("Ошибка отправки сообщения", "Error occured in Gelios Notification Task: \n" + textMessage, email.getProguserChannelAddress());
        }
        return false;
    }
}
