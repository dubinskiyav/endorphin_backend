package biz.gelicon.core.components.erp.todo.documentrealtransit;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransit;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitRepository;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.StandardResponse;
import biz.gelicon.core.response.exceptions.NotFoundException;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.components.core.proguser.ProguserService;
import biz.gelicon.core.utils.GridDataOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestController
@Tag(name = "Статус документа",
        description = "Контроллер для объектов 'Статус документа'")
@RequestMapping(value = "/v" + Config.CURRENT_VERSION + "/apps/documents/documentreal",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class DocumentRealTransitController {

    private static final Logger logger = LoggerFactory
            .getLogger(DocumentRealTransitController.class);

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionDocumentRealTransit extends GridDataOption {

        @Schema(description = "Фильтры для Статус документа:"
                + "<ul>"
                + "<li> documentrealId        - документ (обязательно)</li>"
                + "<li> documenttransitId     - тип статуса документа</li>"
                + "<li> proguserId            - пользователь</li>"
                + "<li> documentrealtransitFlag  - флаг</li>"
                + "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private DocumentRealTransitService documentRealTransitService;
    @Autowired
    private AuthenticationBean authenticationBean;
    @Autowired
    private DocumentTransitRepository documentTransitRepository;
    @Autowired
    private ProguserService proguserService;

    /**
     * Вспомогательный класс для возврата списка
     */
    public static class DocumentRealTransitGetListDTO {

        Integer documentRealTransitId;
        Integer documentTransitColor;
        String documentRealTransitFlag; // ("Статус установлен" или "Статус снят" текстом в зависимости от flag)
        Date documentRealTransitDate;
        Date documentRealTransitDateset;
        String documentTransitName;
        String progUserName;
        String documentRealTransitRemark;

        public DocumentRealTransitGetListDTO(Integer documentRealTransitId,
                Integer documentTransitColor, String documentRealTransitFlag,
                Date documentRealTransitDate, Date documentRealTransitDateset,
                String documentTransitName, String progUserName, String documentRealTransitRemark) {
            this.documentRealTransitId = documentRealTransitId;
            this.documentTransitColor = documentTransitColor;
            this.documentRealTransitFlag = documentRealTransitFlag;
            this.documentRealTransitDate = documentRealTransitDate;
            this.documentRealTransitDateset = documentRealTransitDateset;
            this.documentTransitName = documentTransitName;
            this.progUserName = progUserName;
            this.documentRealTransitRemark = documentRealTransitRemark;
        }

        public Integer getDocumentRealTransitId() {
            return documentRealTransitId;
        }

        public Integer getDocumentTransitColor() {
            return documentTransitColor;
        }

        public String getDocumentRealTransitFlag() {
            return documentRealTransitFlag;
        }

        public Date getDocumentRealTransitDate() {
            return documentRealTransitDate;
        }

        public Date getDocumentRealTransitDateset() {
            return documentRealTransitDateset;
        }

        public String getDocumentTransitName() {
            return documentTransitName;
        }

        public String getProgUserName() {
            return progUserName;
        }

        public String getDocumentRealTransitRemark() {
            return documentRealTransitRemark;
        }

        public void setDocumentRealTransitId(Integer documentRealTransitId) {
            this.documentRealTransitId = documentRealTransitId;
        }

        public void setDocumentTransitColor(Integer documentTransitColor) {
            this.documentTransitColor = documentTransitColor;
        }

        public void setDocumentRealTransitFlag(String documentRealTransitFlag) {
            this.documentRealTransitFlag = documentRealTransitFlag;
        }

        public void setDocumentRealTransitDate(Date documentRealTransitDate) {
            this.documentRealTransitDate = documentRealTransitDate;
        }

        public void setDocumentRealTransitDateset(Date documentRealTransitDateset) {
            this.documentRealTransitDateset = documentRealTransitDateset;
        }

        public void setDocumentTransitName(String documentTransitName) {
            this.documentTransitName = documentTransitName;
        }

        public void setProgUserName(String progUserName) {
            this.progUserName = progUserName;
        }

        public void setDocumentRealTransitRemark(String documentRealTransitRemark) {
            this.documentRealTransitRemark = documentRealTransitRemark;
        }
    }

    @Operation(summary = "Список статутов документа",
            description = "Возвращает постраничный список объектов 'Статус документа' "
                    + " для documentrealId установленного в фильтре"
                    + "<br> Пример вызова"
                    + "<br>{"
                    + "<br>  \"pagination\": {\"current\": 1, \"pageSize\": 10},"
                    + "<br>  \"search\": \"ес\","
                    + "<br>  \"sort\": ["
                    + "<br>    {\"field\": \"documentrealId\", \"order\": \"ascend\" }"
                    + "<br>  ],"
                    + "<br>  \"filters\": {"
                    + "<br>    \"documentrealId\": 267,"
                    + "<br>    \"documenttransitId\": 2,"
                    + "<br>    \"proguserId\": 1,"
                    + "<br>    \"documentrealtransitFlag\": 0,"
                    + "<br>    \"showOnlySetStatus\": 0"
                    + "<br>  },"
                    + "<br>  \"fullTextSearch\": true"
                    + "<br>}"
                    + "")
    //@PreAuthorize("isAuthenticated()")
    @CheckPermission
    @RequestMapping(value = "documentrealtransit/getlist", method = RequestMethod.POST)
    public DataResponse<DocumentRealTransitGetListDTO> getlist(
            @RequestBody GridDataOptionDocumentRealTransit gridDataOption
    ) {
        Map<String, Object> gridDataOptionFilters = gridDataOption.getFilters();
        // Проверим обязательные фильтры
        if (gridDataOptionFilters.get("documentrealId") == null) {
            throw new RuntimeException("Требуется обязательный фильтр documentrealId");
        }
        /**
         * Все фильтры, используемые в запросе {@link DocumentRealTransitService#mainSQL}
         * должны быть, а если их нет - добавлены
         */
        if (gridDataOptionFilters.get("documenttransitId") == null) {// надо добавить
            gridDataOption.getNamedFilters().add(new GridDataOption
                    .NamedFilter("documenttransitId", 0)
            );
        }
        if (gridDataOptionFilters.get("proguserId") == null) {
            gridDataOption.getNamedFilters().add(new GridDataOption
                    .NamedFilter("proguserId", 0)
            );
        }
        if (gridDataOptionFilters.get("documentrealtransitFlag") == null) {
            gridDataOption.getNamedFilters().add(new GridDataOption
                    .NamedFilter("documentrealtransitFlag", -123)
            );
        }
        // Показывать только установленные в данный момент статусы
        if (gridDataOptionFilters.get("showOnlySetStatus") == null) {
            gridDataOption.getNamedFilters().add(new GridDataOption
                    .NamedFilter("showOnlySetStatus", 0) // Фильтр не установлен
            );
        }

        // Получим список
        List<DocumentRealTransitView> result = documentRealTransitService
                .getMainList(gridDataOption);

        // Подсчитаем количество записей всего в запросе
        int total = 0;
        if (gridDataOption.getPagination().getPageSize() > 0) {
            total = documentRealTransitService.getMainCount(gridDataOption);
        }
        // Возвращать будем свой список
        List<DocumentRealTransitGetListDTO> dl = result.stream()
                .map(s -> {
                    //DocumentTransit documentTransit = documentTransitRepository.findById(s.getDocumenttransitId());
                    return new DocumentRealTransitGetListDTO(
                            s.getDocumentRealTransitId(),
                            s.getDocumentTransitColor(),
                            /* Убрал по просьбе Шулепова 23.08.2021
                            // сделал так: когда статус снят - шарика нет вообще
                            (Arrays.asList(0, 2).contains(s.getDocumentRealTransitFlag())) ?
                                    s.getDocumentTransitColor() : null,
                             */
                            s.getDocumentRealTransitFlag() == 0
                                    || s.getDocumentRealTransitFlag() == 2 ? "Статус установлен"
                                    : "Статус снят",
                            s.getDocumentRealTransitDate(),
                            s.getDocumentRealTransitDateset(),
                            s.getDocumentTransitName(),
                            s.getProgUserName(),
                            s.getDocumentRealTransitRemark()
                    );
                })
                .collect(Collectors.toList());

        return documentRealTransitService.buildResponse(dl, gridDataOption, total);
    }

    @Operation(summary = "Получение одного статуса документа по идентификатору",
            description = "Возвращает один объект 'Статус документа' по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует - возвращается объект "
                    + "с пустым идентификатором и полями, заполненными по умолчанию")
    //@PreAuthorize("isAuthenticated()")
    @CheckPermission
    @RequestMapping(value = "documentrealtransit/get", method = RequestMethod.POST)
    @Audit(kinds = {AuditKind.CALL_FOR_EDIT, AuditKind.CALL_FOR_ADD})
    public DocumentRealTransitDTO get(
            @RequestBody(required = false) Integer id,
            Authentication authentication) {
        // для добавления
        if (id == null) {
            Proguser pu = ((UserDetailsImpl) authentication.getPrincipal()).getProgUser();
            return new DocumentRealTransitDTO(
                    null,
                    null,
                    null,
                    pu.getProguserId(), // proguserId
                    new Date(),
                    null,
                    new Date(),
                    0);
        } else {
            DocumentRealTransit entity = documentRealTransitService.findById(id);
            if (entity == null) {
                throw new NotFoundException(
                        String.format("Запись с идентификатором %s не найдена", id));
            }
            return new DocumentRealTransitDTO(entity);
        }
    }

    /**
     * Вспомогательный класс для передачи параметров
     */
    public static class DocumentRealTransitSetDTO {

        int[] documentRealIds; // Список документов, у которых надо установить статус
        Integer documentTransitId; // Устанавливаемый статус
        Date documentRealTransitDate; // Дата статуса
        String documentRealTransitRemark; // Примечание

        public int[] getDocumentRealIds() {
            return documentRealIds;
        }

        public Integer getDocumentTransitId() {
            return documentTransitId;
        }

        public Date getDocumentRealTransitDate() {
            return documentRealTransitDate;
        }

        public String getDocumentRealTransitRemark() {
            return documentRealTransitRemark;
        }

        public void setDocumentRealIds(int[] documentRealIds) {
            this.documentRealIds = documentRealIds;
        }

        public void setDocumentTransitId(Integer documentTransitId) {
            this.documentTransitId = documentTransitId;
        }

        public void setDocumentRealTransitDate(Date documentRealTransitDate) {
            this.documentRealTransitDate = documentRealTransitDate;
        }

        public void setDocumentRealTransitRemark(String documentRealTransitRemark) {
            this.documentRealTransitRemark = documentRealTransitRemark;
        }
    }

    /**
     * Вспомогательный класс для возврата из set
     */
    public static class DocumentRealTransitSetRet {

        Integer documentRealTransitId;
        Integer documentRealId;
        Integer documentTransitId;
        Integer documentTransitColor;
        String documentTransitName;

        public DocumentRealTransitSetRet(Integer documentRealTransitId, Integer documentRealId,
                Integer documentTransitId, Integer documentTransitColor,
                String documentTransitName) {
            this.documentRealTransitId = documentRealTransitId;
            this.documentRealId = documentRealId;
            this.documentTransitId = documentTransitId;
            this.documentTransitColor = documentTransitColor;
            this.documentTransitName = documentTransitName;
        }

        public Integer getDocumentRealTransitId() {
            return documentRealTransitId;
        }

        public Integer getDocumentRealId() {
            return documentRealId;
        }

        public Integer getDocumentTransitId() {
            return documentTransitId;
        }

        public Integer getDocumentTransitColor() {
            return documentTransitColor;
        }

        public String getDocumentTransitName() {
            return documentTransitName;
        }

        public void setDocumentRealTransitId(Integer documentRealTransitId) {
            this.documentRealTransitId = documentRealTransitId;
        }

        public void setDocumentRealId(Integer documentRealId) {
            this.documentRealId = documentRealId;
        }

        public void setDocumentTransitId(Integer documentTransitId) {
            this.documentTransitId = documentTransitId;
        }

        public void setDocumentTransitColor(Integer documentTransitColor) {
            this.documentTransitColor = documentTransitColor;
        }

        public void setDocumentTransitName(String documentTransitName) {
            this.documentTransitName = documentTransitName;
        }
    }

    /*
{
  "documentRealIds": [
    231
  ],
  "documentTransitId": 6,
  "documentRealTransitDate": 1612137600000,
  "documentRealTransitRemark": "Проба"
}

     */

    @Operation(summary = "Установка статуса документов",
            description = "Установка статуса для списка документовЮ переданных в качестве параметра")
    //@PreAuthorize("isAuthenticated()")
    @CheckPermission
    @RequestMapping(value = "documentrealtransit/set", method = RequestMethod.POST)
    @Audit(kinds = {AuditKind.CALL_FOR_SAVE_INSERT, AuditKind.CALL_FOR_SAVE_UPDATE})
    public List<DocumentRealTransitSetRet> set(
            @RequestBody DocumentRealTransitSetDTO documentRealTransitSetDTO,
            Authentication authentication
    ) {
        // Возвращать будем список
        List<DocumentRealTransitSetRet> result = new ArrayList<>();
        if (documentRealTransitSetDTO.documentRealIds == null
                || documentRealTransitSetDTO.documentRealIds.length < 1) {
            // kav : Убрать. Маскировка ошибки
            // return result;
            throw new RuntimeException("Не задан список документов для установки статуса.");
        }
        // Пользователя получим сами
        Integer proguserId = ((UserDetailsImpl) authentication.getPrincipal()).getProgUser()
                .getProguserId();
        // Дата - текущая
        Date dateset = new Date();
        // todo - разобраться что это за флаг
        int flag = 0;
        // Цикл по документам
        for (int documentRealId : documentRealTransitSetDTO.documentRealIds) {
            // Установка
            DocumentRealTransit entity = documentRealTransitService.setOne(
                    documentRealId,
                    documentRealTransitSetDTO.documentTransitId,
                    proguserId,
                    documentRealTransitSetDTO.getDocumentRealTransitDate(),
                    dateset,
                    flag,
                    documentRealTransitSetDTO.getDocumentRealTransitRemark()
            );
            // Данные по статусу для возврата
            DocumentTransit documentTransit =
                    documentTransitRepository.findById(entity.getDocumenttransitId());
            // добавим в результат
            result.add(new DocumentRealTransitSetRet(
                    entity.getDocumentRealTransitId(),
                    entity.getDocumentrealId(),
                    entity.getDocumenttransitId(),
                    documentTransit.getDocumentTransitColor(),
                    documentTransit.getDocumentTransitName()
            ));
        }
        return result;
    }

    @Operation(summary = "Сохранение объекта в базе данных",
            description = "Сохранение одного объекта 'Статус документа. Изменение запрещено.")
    //@PreAuthorize("isAuthenticated()")
    @CheckPermission
    //   @RequestMapping(value = "documentrealtransit/save", method = RequestMethod.POST)
    @Audit(kinds = {AuditKind.CALL_FOR_SAVE_INSERT, AuditKind.CALL_FOR_SAVE_UPDATE})
    public DocumentRealTransitView save(
            @RequestBody DocumentRealTransitDTO documentRealTransitDTO,
            Authentication authentication) {
        DocumentRealTransit entity = documentRealTransitDTO.toEntity();
        // Пользователь
        entity.setProguserId(
                ((UserDetailsImpl) authentication.getPrincipal()).getProgUser().getProguserId());
        // Дата установки
        entity.setDocumentRealTransitDateset(new Date());
        DocumentRealTransit result;
        if (entity.getDocumentRealTransitId() == null) {
            result = documentRealTransitService.add(entity);
            //result = documentRealTransitService.addAll(entity);
        } else {
            // Из постановки:
            // В методе save при id!=null возбудить exception
            throw new RuntimeException("Изменять статус документа запрещено!");
            //result = documentRealTransitService.edit(entity);
        }

        // выбираем представление для одной записи
        return documentRealTransitService.getOne(result.getDocumentRealTransitId());
    }

    @Operation(summary = "Удаление одного или нескольких объектов 'Статус документа' из базы данных",
            description = "Удаление одного или нескольких объектов по массиву идентификаторов, "
                    + "указанных через запятую в теле запроса.")
    //@PreAuthorize("isAuthenticated()")
    @CheckPermission
//    @RequestMapping(value = "documentrealtransit/delete", method = RequestMethod.POST)
    @Audit(kinds = AuditKind.CALL_FOR_DELETE)
    public String delete(@RequestBody int[] ids) {
        documentRealTransitService.deleteByIds(ids);
        return StandardResponse.SUCCESS;
    }

    // Вспомогательный класс
    public static class DocumentRealTransitUnSetDTO {

        int[] documentRealIds; // Список документов, у которых надо снять статус
        Integer documentTransitId; // Снимаемый статус

        public int[] getDocumentRealIds() {
            return documentRealIds;
        }

        public Integer getDocumentTransitId() {
            return documentTransitId;
        }

        public void setDocumentRealIds(int[] documentRealIds) {
            this.documentRealIds = documentRealIds;
        }

        public void setDocumentTransitId(Integer documentTransitId) {
            this.documentTransitId = documentTransitId;
        }
    }

    @Operation(summary = "Снятие статуса документов",
            description = "Снятие статуса для списка документовЮ переданных в качестве параметра")
    //@PreAuthorize("isAuthenticated()")
    @CheckPermission
    @RequestMapping(value = "documentrealtransit/unset", method = RequestMethod.POST)
    @Audit(kinds = {AuditKind.CALL_FOR_SAVE_INSERT, AuditKind.CALL_FOR_SAVE_UPDATE})
    public List<DocumentRealTransitSetRet> unset(
            @RequestBody DocumentRealTransitUnSetDTO documentRealTransitUnSetDTO,
            Authentication authentication
    ) {
        // Возвращать будем список
        List<DocumentRealTransitSetRet> result = new ArrayList<>();
        if (documentRealTransitUnSetDTO.documentRealIds == null
                || documentRealTransitUnSetDTO.documentRealIds.length < 1) {
            throw new RuntimeException("Не задан список документов для снятия статуса.");
        }
        // Цикл по документам
        for (int documentRealId : documentRealTransitUnSetDTO.documentRealIds) {
            // Снимем статус
            DocumentRealTransit documentRealTransit = documentRealTransitService.unSetOne(
                    documentRealId,
                    documentRealTransitUnSetDTO.documentTransitId
            );
            // Данные по статусу для возврата
            if (documentRealTransit == null) {
                // Запись удалена и нечего добавлять в результат
                // Такого уже быть не должно
            } else {
                if (documentRealTransit.getDocumenttransitId() != null
                        && documentRealTransit.getDocumenttransitId() != 0) {
                    // Есть статус
                    DocumentTransit documentTransit =
                            documentTransitRepository
                                    .findById(documentRealTransit.getDocumenttransitId());
                    // добавим в результат
                    result.add(new DocumentRealTransitSetRet(
                            documentRealTransit.getDocumentRealTransitId(),
                            documentRealTransit.getDocumentrealId(),
                            documentRealTransit.getDocumenttransitId(),
                            documentTransit.getDocumentTransitColor(),
                            documentTransit.getDocumentTransitName()
                    ));
                } else {
                    // Статус у документа стал равен 0, поэтому вернулась пустая запись
                    result.add(new DocumentRealTransitSetRet(
                            null,
                            documentRealTransit.getDocumentrealId(),
                            documentRealTransit.getDocumenttransitId(), // здесь будет 0
                            null,
                            null
                    ));
                }
            }
        }
        return result;
    }


}
