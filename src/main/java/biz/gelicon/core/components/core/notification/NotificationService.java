package biz.gelicon.core.components.core.notification;

import biz.gelicon.core.components.erp.todo.notificationappendix.NotificationAppendix;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.components.erp.todo.notificationsetting.NotificationSettingRepository;
import biz.gelicon.core.components.erp.todo.patternnotification.PatternNotificationRepository;
import biz.gelicon.core.components.erp.todo.notificationappendix.NotificationAppendixRepository;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static biz.gelicon.core.components.core.capcode.CapCode.CHANNEL_EMAIL;

@Service
public class NotificationService extends BaseService<Notification> {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationValidator notificationValidator;
    @Autowired
    private DocumentRealRepository documentrealRepository;
    @Autowired
    private PatternNotificationRepository patternNotificationRepository;
    @Autowired
    private NotificationSettingRepository notificationSettingRepository;
    @Autowired
    private NotificationAppendixRepository notificationAppendixRepository;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/notification/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(notificationRepository, notificationValidator);
    }

    public List<NotificationView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<NotificationView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("notification",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(NotificationView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(NotificationView.class)
                .execute();
    }
    public int notificationDocumentPrepare(Integer documentrealId, Integer eventnotificationId) {
        List<Map<String, Object>> list = documentrealRepository.findQueryForMap("" +
                "SELECT dr.documentreal_name, " +
                "dt.documenttransit_Name, " +
                "pu.proguser_fullname, " +
                "dr.documentreal_date " +
                "FROM   documentreal dr " +
                "INNER JOIN proguser pu ON pu.proguser_id = dr.proguser_id " +
                "LEFT OUTER JOIN documenttransit dt ON dt.documenttransit_id = dr.documentreal_status " +
                "WHERE  dr.documentreal_id=:documentrealId","documentrealId",documentrealId);
        if (list.isEmpty())
            throw new RuntimeException(String.format("Документа в системе не существует."));
        Notification nt = null;
        Map<String, Object> data = list.get(0);
        String documentrealName = (String) data.get("documentreal_name");
        String documentTransitName = (String) data.get("documenttransit_Name");
        String progUserFullName = (String) data.get("proguser_fullname");
        Date documentrealDate = (Date) data.get("documentreal_date");

        List<Map<String, Object>> cursor = notificationSettingRepository.findQueryForMap("" +
                "SELECT ns.channelnotification_id, " +
                "pc.proguserchannel_address, " +
                "pu.proguser_fullname, " +
                "pu.proguser_id " +
                "FROM   notificationsetting ns " +
                "LEFT OUTER JOIN proguserchannel pc ON pc.proguser_id = ns.proguser_id  AND pc.channelnotification_id = ns.channelnotification_id " +
                "LEFT OUTER JOIN proguser pu ON pu.proguser_id = ns.proguser_id " +
                "WHERE  ns.eventnotification_id = :eventnotificationId","eventnotificationId",eventnotificationId);
        String notificationText;
        final String DATE_FORMAT_NOW = "dd.MM.yyyy";
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);

        InputStream input = this.getClass().getClassLoader().getResourceAsStream("mail/template-notification.html");
        String template = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        for (Map<String, Object> item : cursor) {
            Integer channelnotificationId = (Integer) item.get("channelnotification_id");
            String notificationAddress = (String) item.get("proguserchannel_address");
            String recipientFullName = (String) item.get("proguser_fullname");
            Integer progUserId = (Integer) item.get("proguser_id");

            if (channelnotificationId == CHANNEL_EMAIL) {
                /* Получим информацию из шаблона */
                Map<String, Object> params = new HashMap<>();
                params.put("eventnotificationId", eventnotificationId);
                params.put("channelnotificationId", channelnotificationId);

                List<Map<String, Object>> eventListData = patternNotificationRepository.findQueryForMap("" +
                        "SELECT patternnotification_text, " +
                        "patternnotification_subj " +
                        "FROM   patternnotification " +
                        "WHERE  eventnotification_id = :eventnotificationId " +
                        "AND  channelnotification_id = :channelnotificationId", params);
                String notificationSubj;
                if (eventListData.isEmpty()) {
                    notificationText = template;
                    notificationSubj = "Уведомление об изменении документа";
                } else {
                    Map<String, Object> eventData = eventListData.get(0);
                    notificationText = (String) eventData.get("patternnotification_text");
                    notificationSubj = (String) eventData.get("patternnotification_subj");
                }
                notificationText = notificationText.replace("%TITLE%", notificationSubj);

                Date notificationDate = new Date();
                /* Подстановки */
                notificationText = notificationText.replace("%DOCUMENTREAL_NAME%", "'" + documentrealName + "'");
                if (documentTransitName == null) {
                    notificationText = notificationText.replace("%DOCUMENTREAL_STATUS%", "'Без статуса'");
                } else {
                    notificationText = notificationText.replace("%DOCUMENTREAL_STATUS%", "'" + documentTransitName + "'");
                };

                notificationText = notificationText.replace("%DOCUMENTREAL_DATE%", df.format(documentrealDate));

                notificationText = notificationText.replace("%PROGUSER_NAME%", progUserFullName);
                notificationText = notificationText.replace("%RECIPIENT_NAME%", recipientFullName);

                nt = new Notification(
                        null,
                        progUserId,
                        channelnotificationId,
                        eventnotificationId,
                        notificationAddress,
                        notificationText,
                        notificationDate,
                        0,
                        null,
                        notificationSubj,
                        null,
                        null,
                        null,
                        0);
              notificationRepository.insert(nt);

            }
        };
        if (nt == null) {return 0;}
        else {return nt.getNotificationId();}
    };

    public void notificationAppendixPrepare(Integer notificationAppendixId, Integer notificationId, String filePath) throws IOException {
        File file = new File(filePath);
        NotificationAppendix na = new NotificationAppendix(
                notificationAppendixId,
                notificationId,
                file.getName(),
                null
        );
        notificationAppendixRepository.insert(na);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> args = new HashMap<>();
        String sql = "UPDATE notificationAppendix SET notificationappendix_data = ? WHERE notificationappendix_id = ?";

        int rows = notificationAppendixRepository.executeSQL(sql,new Object[]{fis,notificationAppendixId});

        logger.info(String.format("%d rows added", rows));
    };
}

