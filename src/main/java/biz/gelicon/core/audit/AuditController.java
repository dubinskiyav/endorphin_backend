package biz.gelicon.core.audit;

import biz.gelicon.core.annotations.CheckAdminPermission;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.audit.AuditRecord;
import biz.gelicon.core.audit.AuditService;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.core.proguser.ProgUserRepository;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.TwoTuple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@Tag(name = "Аудит", description = "Контроллер для объектов \"Аудит\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/admin/audit",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuditController {

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionAudit extends GridDataOption {
        @Schema(description = "Фильтры для Роль:" +
                "<ul>" +
                "<li>datebeg - начало интервала просмотра" +
                "<li>dateend - окончание интервала просмотра" +
                "<li>proguserId - (опционно) идентификатор пользователя" +
                "<li>kind - (опционно) тип события" +
                "<li>entity - (опционно) сущность" +
                "<li>idValue - (опционно) идентификатор объекта" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }
    }

    @Schema(description = "Запрос одной записи лога аудита")
    public static class AuditRequest {
        @Schema(description = "Идентификатор лога аудита")
        String logId;
        @Schema(description = "Дата/вреям записи")
        Long datetime;

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public Long getDatetime() {
            return datetime;
        }

        public void setDatetime(Long datetime) {
            this.datetime = datetime;
        }
    }

    @Autowired
    private AuditService auditService;
    @Autowired
    private ProgUserRepository progUserRepository;

    @Operation(summary = "Список объектов \"Аудит\"",
            description = "Возвращает постраничный список объектов \"Аудит\"")
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "log/getlist", method = RequestMethod.POST)
    public DataResponse<AuditRecord> getlist(@RequestBody GridDataOptionAudit gridDataOption) {
        Map<String, Object> filters = gridDataOption.getFilters();
        if(filters.get("dateRange")==null)
            throw new RuntimeException("Требуется интервал просмотра");
        List<Long> range = (List<Long>) filters.get("dateRange");

        Date dtBeg = new Date(range.get(0));
        Date dtEnd = new Date(range.get(1));

        AuditRecord pattern = new AuditRecord();

        if(filters.get("proguserId")!=null) {
            pattern.setProguser(
                    new AuditRecord.ProguserInfo(
                            progUserRepository.findById((Integer) filters.get("proguserId"))));
        }

        List<Integer> kinds = null;
        if(filters.get("kinds")!=null) {
            kinds = (List<Integer>) filters.get("kinds");
        }

        if(filters.get("entity")!=null) {
            pattern.setEntity((String) filters.get("entity"));
        }
        if(filters.get("idValue")!=null) {
            pattern.setIdValue(Arrays.asList(new Integer[]{(Integer) filters.get("idValue")}));
        }
        if(filters.get("duration")!=null) {
            int duration = ((Integer) filters.get("duration")).intValue();
            pattern.setDuration(Long.valueOf(duration));
        }

        TwoTuple<List<AuditRecord>,Long> result = null;
        try {
            result = auditService.query(dtBeg, dtEnd,
                                        kinds,
                                        pattern,
                                        gridDataOption.buildPageRequest(),
                                        gridDataOption.getSearch());
        } catch (ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        long total = result.b;
        return BaseService.buildResponse(result.a,gridDataOption,(int)total);

    }


    @Operation(summary = "Получение одной записи таблицы \"Аудит\" по ee идентификатору",
            description = "Возвращает одну запись таблицы \"Аудит\" по ee идентификатору ")
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "log/get", method = RequestMethod.POST)
    public AuditRecord get(@RequestBody AuditRequest rec) {
        AuditRecord record = auditService.get(rec.getLogId(),new Date(rec.getDatetime()));
        return record;
    }

}



