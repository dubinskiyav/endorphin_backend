package biz.gelicon.core.components.erp.todo.documentrealtransit;

import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealService;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransit;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitRepository;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.security.Permission;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class DocumentRealTransitService extends BaseService<DocumentRealTransit> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentRealTransitService.class);
    public static final String ALIAS_MAIN = "DRT";
    private final boolean logFlag = true;

    @Autowired
    private DocumentRealTransitRepository documentRealTransitRepository;
    @Autowired
    private DocumentRealTransitValidator documentRealTransitValidator;
    @Autowired
    DocumentRealRepository documentrealRepository;
    @Autowired
    DocumentTransitRepository documentTransitRepository;
    @Autowired
    private AuthenticationBean authenticationBean;
    @Autowired
    DocumentRealService documentRealService;

    /**
     * главный запрос. используется в главной таблице в контроллере используется в getlist
     */
    // Хотим вернуть еще proguser_name
    // Хотим вернуть еще documenttransit_color и documenttransit_name
    // Показывать только установленные в данный момент статусы
    @Value("classpath:/query/documentrealtransit/mainSQL.sql")
    Resource mainSQL;

    /**
     * Упрощенный запрос для подсчета записей. В самом примитивном случае просто убираем лишние
     * соединения, если это позволяет логика запроса и убираем сортировку
     */
    // Показывать только установленные в данный момент статусы
    @Value("classpath:/query/documentrealtransit/mainCountSQL.sql")
    Resource mainCountSQL;

    /**
     * главный запрос. используется в главной таблице в контроллере используется в save
     */
    @Value("classpath:/query/documentrealtransit/oneSQL.sql")
    Resource oneSQL;


    @PostConstruct
    public void init() {
        init(documentRealTransitRepository, documentRealTransitValidator);
    }

    public List<DocumentRealTransitView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<DocumentRealTransitView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("documentrealtransit", ALIAS_MAIN))
                .setPredicate(gridDataOption
                        .buildPredicate(DocumentRealTransitView.class, ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(DocumentRealTransitView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<DocumentRealTransitView>(mainCountSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("documentrealtransit", ALIAS_MAIN))
                .setPredicate(gridDataOption
                        .buildPredicate(DocumentRealTransitView.class, ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(DocumentRealTransitView.class)
                .count();
    }


    public DocumentRealTransitView getOne(Integer id) {
        return new Query.QueryBuilder<DocumentRealTransitView>(oneSQL)
                .setPredicate(ALIAS_MAIN + ".documentrealtransit_id=:documentrealtransitId")
                .build(DocumentRealTransitView.class)
                .executeOne("documentrealtransitId", id);
    }

    @Override
    protected void beforeSave(DocumentRealTransit entity) {
        if (entity.getDocumentRealTransitId() != null) {
            // Изменение - запрещено
            throw new RuntimeException("Изменение статуса документа запрещено");
        }
    }


    /*
{
  "documentRealTransitId": null,
  "documentrealId": 231,
  "documenttransitId": 6,
  "proguserId": 1,
  "documentRealTransitDate": 1612137600000,
  "documentRealTransitRemark": "string",
  "documentRealTransitDateset": 1627776000000,
  "documentRealTransitFlag": 0
}
     */

    /**
     * Установка статуса документа
     *
     * @param documentRealId
     * @param documentTransitId
     * @param date
     * @param flag
     * @param remark
     * @return
     */
    public DocumentRealTransit setOne(
            Integer documentRealId,
            Integer documentTransitId,
            Integer proguserId,
            Date date,
            Date dateset,
            int flag,
            String remark
    ) {
        DocumentRealTransit entity = new DocumentRealTransit();
        // из параметров
        entity.setDocumentrealId(documentRealId);
        entity.setDocumenttransitId(documentTransitId);
        entity.setProguserId(proguserId);
        entity.setDocumentRealTransitDate(date);
        entity.setDocumentRealTransitDateset(dateset);
        entity.setDocumentRealTransitRemark(remark);
        entity.setDocumentRealTransitFlag(flag);
        if (logFlag) {logger.info(entity.toString());}
        // Проверки
        DocumentReal documentreal = documentrealRepository.findById(documentRealId);
        DocumentTransit documentTransit = documentTransitRepository
                .findById(entity.getDocumenttransitId());

        // Проверка принадлежности статуса типу документу
        if (!checkPossible(documentreal, documentTransit)) {
            throw new RuntimeException("Тип документа статуса не соответствует возможному");
        }

        // Проверка возможности модификации реал. документа
        // todo - решить что вызывать
        //  kav сказал 09.07.2021 вызывать checkStatusUpdate
        // или то, что сказал 12.07.20201 :
        // Правило простое: там где +100 делается вызывать checkUpdate,
        // там где не делается checkUpdate и checkStatusUpdate
        // Попробуем вызывать второе, так как первое не работает
        //documentrealRepository.checkStatusUpdate(documentreal, Permission.INSERT);
        documentrealRepository.checkUpdate(documentreal, Permission.INSERT);

        // Проверка повторной установки статуса
        if (!checkTwice(documentreal, documentTransit)) {
            throw new RuntimeException(
                    "Повторная установка статуса для данного типа документа запрещена");
        }

        Integer documenttransitId = entity.getDocumenttransitId();

        // Проверка доступа пользователю на установку статуса
        if (!checkUserAccess(documentreal, documentTransit)) {
            throw new RuntimeException("У пользователь нет прав устанавливать статус документа.");
        }

        // Выполняем добавление, так как в таблице documentrealtransit нет AK - никак не проверить
        add(entity);
        if (entity.getDocumentRealTransitFlag() != 0) {
            // Обновим флаг
            statusSet1(entity.getDocumentrealId());
        }
        // Установка текущего состояния документа
        documentrealStatusSet(entity.getDocumentrealId());
        return entity;
    }

    /**
     * Снимает статус с документа
     *
     * @param documentRealId
     * @param documentTransitId
     * @return добавленную запись или пусто, если удалили
     */
    public DocumentRealTransit unSetOne(
            Integer documentRealId,
            Integer documentTransitId
    ) {
        DocumentReal documentReal = documentrealRepository.findById(documentRealId);
        DocumentTransit documentTransit = documentTransitRepository.findById(documentTransitId);
        // Проверка возможности снятия статуса
        if (!checkUnsetStatus(documentReal, documentTransit)) {
            throw new RuntimeException("Снять возможно только текущий статус.");
        }
        // Текущий статус
        int documentRealTransitIdLast = getLastDocumentRealTransitId(documentRealId);
        int documentreal_status = documentReal.getDocumentRealStatus();
        // Логирование или удаление
        int documenttransit_flaghistory = documentTransit.getDocumentTransitFlaghistory();
        // Текущий прогюзер_ид
        Integer proguserId = authenticationBean.getCurrentUser().getProguserId();
        // История
        if (documentTransit.getDocumentTransitFlaghistory() == 1) {
            // Надо добавить запись
            Date now = new Date();
            DocumentRealTransit documentRealTransit = new DocumentRealTransit(
                    null,
                    documentRealId,
                    documentTransitId,
                    proguserId,
                    now,
                    null,
                    now,
                    1
            );
            documentRealTransitRepository.insert(documentRealTransit);
            // надо у предыдущего статуса установить flag в 2
            updateFlag(documentRealTransitIdLast, 2);
            // Надо у документа установить предыдущий статус
            documentrealStatusSet(documentRealId);
            // Вернем добавленную запись
            // return documentRealTransit;
            // Вернем новый текущий статус, если он есть
            documentRealTransitIdLast = getLastDocumentRealTransitId(documentRealId);
            if (documentRealTransitIdLast != 0) {
                return documentRealTransitRepository.getOne(documentRealTransitIdLast);
            } else {
                // Вернем пустой
                return new DocumentRealTransit(
                        null,
                        documentRealId,
                        0,
                        proguserId,
                        null,
                        null,
                        null,
                        0
                );
            }
        } else {
            // Проверка возможности модификации статуса реал. документа
            // todo - решить что вызывать
            //documentrealRepository.checkStatusUpdate(documentRealId, Permission.DELETE);
            documentrealRepository.checkUpdate(documentRealId, Permission.DELETE);
            // Надо удалить последний подходящий статус
            // Получим id последнего статуса
            Integer documentrealtransit_id = getLastDocumentRealTransitId(documentRealId);
            if (documentrealtransit_id == 0) {
                documentrealtransit_id = -123; // Чтобы не заморачиваться. Так как такого быть не должно
            }
            // Проверка необходимости администрирования установки/снятия статуса
            if (!checkUseAdmin(documentrealtransit_id)) {
                throw new RuntimeException("У пользователя нет прав снимать статус документа.");
            }
            ;
            // Проверка правильности порядка статусов
            // todo что это?  процедура documentrealtransitdel
            if (!checkLevel(documentrealtransit_id, documentRealId)) {
                throw new RuntimeException("Неверный порядок статусов.");
            }
            // Удаляем его
            // befireDelete и afterDelete отсутствуют - все здесь
            documentRealTransitRepository.delete(documentrealtransit_id);
            // Установим текущий статус и получим его
            documentrealtransit_id = documentrealStatusSet(documentRealId);
            if (documentrealtransit_id == 0) {
                // Вернем пустой
                return new DocumentRealTransit(
                        null,
                        documentRealId,
                        0,
                        proguserId,
                        null,
                        null,
                        null,
                        0
                );
            } else {
                // Вернем новый текущий статус
                return documentRealTransitRepository.getOne(
                        getLastDocumentRealTransitId(documentRealId)
                );
            }
        }
    }

    // Проверка необходимости администрирования установки/снятия статуса
    private boolean checkUseAdmin(
            Integer documentRealTransitId
    ) {
        // todo сделать из процедуры documentrealtransitdel
        return true;
    }

    // Проверка правильности порядка статусов
    private boolean checkLevel(
            Integer documentRealTransitId,
            Integer documentRealId
    ) {
        // todo сделать из процедуры documentrealtransitdel
        return true;
    }

    // Проверка возможности снятия статуса
    private boolean checkUnsetStatus(
            DocumentReal documentReal,
            DocumentTransit documentTransit
    ) {
        // Снимаемый статус должен быть текущим у документа
        return documentReal.getDocumentRealStatus().equals(documentTransit.getDocumentTransitId());
    }

    /**
     * Проверка принадлежности статуса документу Статус можно установить лишь из возможных для для
     * этого типа документа таблица documenttransit
     */
    protected boolean checkPossible(
            DocumentReal documentreal,
            DocumentTransit documentTransit
    ) {
        // document_id из документа
        Integer docomentIdfromReal = documentreal.getDocumentId();
        // document_id из статуса
        Integer docomentIdfromTransit = documentTransit.getDocumentId();
        // Они должны совпадать
        return (docomentIdfromReal != null && docomentIdfromReal.equals(docomentIdfromTransit));
    }

    /**
     * Проверка повторной установки статуса
     *
     * @param documentreal
     * @param documentTransit
     * @return
     */
    protected boolean checkTwice(
            DocumentReal documentreal,
            DocumentTransit documentTransit
    ) {
        // Проверка повторной установки статуса
        if (documentTransit.getDocumentTransitTwicecheck() != 1) {
            // У статуса не требуется проверка на повторность
            return true;
        }
        // Попробуем получить такой же статус этого же документа
        // для документов с установленным статусом (documentrealtransit_flag = 0)
        return !documentTransitRepository.findQueryExists(" "
                        + " SELECT * "
                        + " FROM   documentrealtransit DTR "
                        + " WHERE  DTR.documentreal_id = :documentreal_id "
                        + "   AND  DTR.documenttransit_id = :documenttransit_id "
                        + "   AND  DTR.documentrealtransit_flag = 0 ",
                new HashMap<String, Object>() {{
                    put("documentreal_id", documentreal.getDocumentRealId());
                    put("documenttransit_id", documentTransit.getDocumentTransitId());
                }});
    }

    /**
     * Проверка доступа пользователю на установку статуса
     *
     * @param documentreal
     * @param documentTransit
     * @return
     */
    protected boolean checkUserAccess(
            DocumentReal documentreal,
            DocumentTransit documentTransit
    ) {
        if (documentTransit.getDocumentTransitUseadmin() != 1) {
            // Не надо администрировать пользователей
            return true;
        }
        // Проверим, если ли пользователь в доступах
        return documentTransitRepository.findQueryExists(" "
                        + " SELECT DISTINCT 1 "
                        + " FROM   documenttransituser "
                        + " WHERE  proguser_id = :proguser_id "
                        + "   AND  documenttransit_id = :documenttransit_id ",
                new HashMap<String, Object>() {{
                    put("proguser_id", documentreal.getProguserId());
                    put("documenttransit_id", documentTransit.getDocumentTransitId());
                }});
    }

    /**
     * Возвращает последний статус документа
     *
     * @param documentrealId
     * @return
     */
    public Integer getLastDocumentTransitId(
            Integer documentrealId
    ) {
        // documentrealtransit_flag = 0 означает статус установлен
        // поэтому ищем только среди установленных
        Integer documentTransitId = documentTransitRepository
                .findQueryForObject(Integer.class, ""
                                + " SELECT DRT.documenttransit_id "
                                + " FROM   documentrealtransit DRT "
                                + " WHERE  DRT.documentreal_id = " + documentrealId
                                + "   AND  DRT.documentrealtransit_flag = 0 "
                                + "   AND  DRT.documentrealtransit_id = (SELECT MAX(DRT1.documentrealtransit_id) "
                                + "                                      FROM documentrealtransit DRT1 "
                                + "                                      WHERE DRT1.documentreal_id = DRT.documentreal_id "
                                + "                                        AND DRT1.documentrealtransit_flag = DRT.documentrealtransit_flag) ",
                        null
                );
        if (documentTransitId == null) {// Ни одного статуса - значит 0
            documentTransitId = 0;
        }
        return documentTransitId;
    }

    /**
     * Возвращает id последнего статуса
     *
     * @param documentrealId
     * @return
     */
    public Integer getLastDocumentRealTransitId(
            Integer documentrealId
    ) {
        // documentrealtransit_flag = 0 означает статус установлен
        // поэтому ищем только среди установленных
        Integer documentRealTransitId = documentTransitRepository
                .findQueryForObject(Integer.class, ""
                                + " SELECT DRT.documentrealtransit_id "
                                + " FROM   documentrealtransit DRT "
                                + " WHERE  DRT.documentreal_id = " + documentrealId
                                + "   AND  DRT.documentrealtransit_flag = 0 "
                                + "   AND  DRT.documentrealtransit_id = (SELECT MAX(DRT1.documentrealtransit_id) "
                                + "                                      FROM documentrealtransit DRT1 "
                                + "                                      WHERE DRT1.documentreal_id = DRT.documentreal_id "
                                + "                                        AND DRT1.documentrealtransit_flag = DRT.documentrealtransit_flag) ",
                        null
                );
        if (documentRealTransitId == null) {// Ни одного статуса - значит 0
            documentRealTransitId = 0;
        }
        return documentRealTransitId;
    }

    /**
     * Устанавливает у докуента поле status в значение последнего актуального статуса из
     * documentrealtransit Аналог процедуры DOCUMENTREALSTATUSSET
     *
     * @param documentrealId
     */
    public Integer documentrealStatusSet(Integer documentrealId) {
        if (documentrealId == null || documentrealId == 0) {
            return 0;
        }
        // Получим последний актуальный статус
        Integer documentTransitId = getLastDocumentTransitId(documentrealId);
        // Установим этот статус
        documentRealService.updateStatus(documentrealId, documentTransitId);
        return documentTransitId;
    }

    /**
     * Аналог процедуры DOCUMENTREALSTATUSSET1
     *
     * @param documentrealId
     */
    public void statusSet1(Integer documentrealId) {
        // Определяем текущий статус
        Integer documentrealStatus = documentRealService.getStatus(documentrealId);
        // нет статусов
        if (documentrealStatus == null || documentrealStatus == 0) {
            return;
        }
        // Определяем текущий статус
        Integer documentrealtransitId = documentRealService.getStatus(documentrealId);
        // Обновим флаг статуса
        updateFlag(documentrealtransitId, 2);
    }

    /**
     * Изменяет поле флаг (documentrealtransit_flag) без проверок
     *
     * @param documentRealTransitId
     * @param documentRealTransitFlag
     */
    private void updateFlag(
            Integer documentRealTransitId,
            Integer documentRealTransitFlag
    ) {
        documentTransitRepository.executeSQL(" "
                        + " UPDATE DocumentRealTransit SET "
                        + "   documentrealtransit_flag = :documentrealtransit_flag "
                        + " WHERE  documentrealtransit_id = :documentrealtransit_id ",
                new HashMap<String, Object>() {{
                    put("documentrealtransit_flag", documentRealTransitFlag);
                    put("documentrealtransit_id", documentRealTransitId);
                }}
        );
    }

}

