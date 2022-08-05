package biz.gelicon.core.service;

import biz.gelicon.core.components.core.notification.NotificationService;
import biz.gelicon.core.controllers.IntergatedTest;
import biz.gelicon.core.components.core.notification.NotificationRepository;
import biz.gelicon.core.components.erp.todo.notificationappendix.NotificationAppendixRepository;
import biz.gelicon.core.tasks.ScheduledSendNotify;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.subethamail.wiser.Wiser;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.*;

import static biz.gelicon.core.components.core.capcode.CapCode.EVNOT_DEF_DOCUMENT;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class NotificationTest extends IntergatedTest {

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationAppendixRepository notificationAppendixRepository;

    @Autowired
    ScheduledSendNotify scheduledSendNotify;

    private static Wiser wiser;
    private File tmpFile1;
    private File tmpFile2;
    private File tmpFile3;



    @BeforeAll
    public static void setup() {
        // старт почтового сервиса
        wiser = new Wiser();
        wiser.setPort(2625); // 25 порт может быть занят
        wiser.setHostname("localhost");
        wiser.start();
    }

    @AfterAll
    public static void destroy() {
        wiser.stop();
    }

    @Test
    public void testSend() throws IOException, MessagingException {
        int notificationId1 = notificationService.notificationDocumentPrepare(1, EVNOT_DEF_DOCUMENT);
        int notificationId2 = notificationService.notificationDocumentPrepare(2, 1);
        assertEquals(2, notificationRepository.count());

        try {
            //файл1 сообщение1
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("mail/teplate-mail-appendix1");
            //Создание временного файла
            tmpFile1 = File.createTempFile("testMail", ".tmp");
            OutputStream os=new FileOutputStream(tmpFile1.getAbsoluteFile());
            os.write(is.readAllBytes());
            notificationService.notificationAppendixPrepare(1,notificationId1, tmpFile1.getAbsolutePath());

            //файл2 сообщение1
            is = this.getClass().getClassLoader().getResourceAsStream("mail/teplate-mail-appendix2");
            //Создание временного файла
            tmpFile2 = File.createTempFile("testMail", ".tmp");
            os=new FileOutputStream(tmpFile2.getAbsoluteFile());
            os.write(is.readAllBytes());
            notificationService.notificationAppendixPrepare(2,notificationId1, tmpFile2.getAbsolutePath());

            //файл1 сообщение2
             is = this.getClass().getClassLoader().getResourceAsStream("mail/teplate-mail-appendix1");
            //Создание временного файла
            tmpFile3 = File.createTempFile("testMail", ".tmp");
            os=new FileOutputStream(tmpFile3.getAbsoluteFile());
            os.write(is.readAllBytes());
            notificationService.notificationAppendixPrepare(3,notificationId2, tmpFile3.getAbsolutePath());

            //Количество вложений в БД
            assertEquals(3, notificationAppendixRepository.count());
            //Отправка сообщения
            scheduledSendNotify.process();
            tmpFile1.deleteOnExit();
            tmpFile2.deleteOnExit();
            tmpFile3.deleteOnExit();

            //Количество сообщений
            Assert.assertEquals(2, wiser.getMessages().size());
            MimeMessage email = wiser.getMessages().get(0).getMimeMessage();
            MimeMultipart multipart = (MimeMultipart) email.getContent();
            String content = getTextFromMimeMultipart(multipart);
            Assert.assertTrue(content.indexOf("<title>Уведомление об изменении документа</title>") >= 0);
            //Количество вложений
            assertEquals(3, multipart.getCount());


        } catch  (IOException e) {
            e.printStackTrace();
        }

    }


    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                result.append("\n").append(bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}
