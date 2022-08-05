package biz.gelicon.core.reports;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.core.capresource.Artifact;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.core.capresource.ArtifactRepository;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.ReportResponse;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.service.DownloaderCenter;
import biz.gelicon.core.utils.GridDataOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Печатные формы", description = "Контроллер для печатных форм")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/reports",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class ReportController {

    @Autowired
    private ReportManagerImpl manager;
    @Autowired
    private DownloaderCenter downloaderCenter;
    @Autowired
    private ArtifactRepository artifactRepository;

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionReport extends GridDataOption {
        @Schema(description = "Фильтры для объекта Печатная форма:" +
                "<ul>" +
                "<li>module - код модуля" +
                "<li>entity - код сущности" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Operation(summary = "Список объектов \"Печатная форма\"",
            description = "Возвращает полный список объектов \"Печатная форма\" для модуля и/или сущности")
    @CheckPermission
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    public DataResponse<ReportView> getlist(@RequestBody GridDataOptionReport gridDataOption,Authentication authentication) {
        UserDetailsImpl ud = (UserDetailsImpl) authentication.getPrincipal();
        String modCode = (String) gridDataOption.getFilters().get("module");
        String entityCode = (String) gridDataOption.getFilters().get("entity");

        if(entityCode==null && modCode==null)
            throw new RuntimeException("Параметры запроса пустыею Укажите module и/или entity");

        List<ReportDescriptionImpl> reports = manager.getReports();
        if(modCode!=null) {
            reports= reports.stream()
                    .filter(r -> modCode.equalsIgnoreCase(r.getUnitName()))
                    .collect(Collectors.toList());
        };
        if(entityCode!=null) {
            reports= reports.stream()
                    .filter(r -> entityCode.equalsIgnoreCase(r.getEntityName()))
                    .collect(Collectors.toList());
        }
        // допускаем только те, которые разрешены
        // Мотивация отказа от restrict
        /*
        sav:
        Давайте вернемся к модели, когда права надо явно давать. Иначе будет страшная путаница.
        Сейчас таблица со всеми ресурсами, и не понятно что будет если
        одному дать доступ, а другому запретить.
        */
        if(!ud.containsSystemRole()) {
            List<Artifact> allowReports = artifactRepository.getAllowArtifacts(ud.getProgUser().getProguserId(),ArtifactKinds.REPORT);
            reports = reports.stream()
                    .filter(a->allowReports.stream().anyMatch(r->a.getCode().equalsIgnoreCase(r.getArtifactCode())))
                    .collect(Collectors.toList());
        }

        return BaseService.buildResponse(reports.stream()
                .map(r->new ReportView(r))
                .collect(Collectors.toList()));

    }

    @Operation(summary = "Отправляет на выполнение печатную форму",
            description = "Получает параметры и отправляет на выполнение печатную форму. Возвращает url для доступа к готовому документу")
    @CheckPermission
    @RequestMapping(value = "run", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.PREPARE_REPORT})
    public ReportResponse run(@RequestBody ReportAndParams reportAndParams, Authentication authentication) {
        ReportDescriptionImpl rep = manager.getReports().stream()
                .filter(r -> r.getCode().equalsIgnoreCase(reportAndParams.getCode()))
                .findFirst()
                .orElse(null);
        if(rep==null)
            throw new RuntimeException(String.format("Печатная форма с кодом %s не найдена",reportAndParams.getCode()));

        Proguser pu = ((UserDetailsImpl) authentication.getPrincipal()).getProgUser();
        // поиск в ограничениях
        if(artifactRepository.isRestricted(rep.getCode(),ArtifactKinds.REPORT,pu.getProguserId())) {
            throw new AccessDeniedException("Нет доступа к выполнению печатной формы");
        }
        // добавляем текущего пользователя в параметры
        if(reportAndParams.getParams()==null) {
            reportAndParams.setParams(new HashMap<>());
        }
        reportAndParams.getParams().put("currentUserId",pu.getProguserId());

        String fileName = manager.runReport(reportAndParams.getCode(), reportAndParams.getParams());
        Integer correlationId = downloaderCenter.pushTask(fileName, pu);
        return new ReportResponse("/v"+ Config.CURRENT_VERSION+"/apps/reports/result/"+correlationId,rep.getName());
    }

    @CheckPermission
    @RequestMapping(value = "result/{correlationId}", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.DOWNLOAD_REPORT},storeOutput = false)
    public ResponseEntity<StreamingResponseBody> dowmnloadResult(@PathVariable("correlationId") Integer correlationId,
                                                                 HttpServletResponse response, Authentication authentication) {
        Proguser pu = ((UserDetailsImpl) authentication.getPrincipal()).getProgUser();

        DownloaderCenter.PendingTask task = downloaderCenter.getTask(correlationId, pu);

        response.setContentType(task.getMimeType());
        response.setHeader(
                "Content-Disposition",
                "attachment;"+task.getShortName());
        StreamingResponseBody stream = out -> {
            try(InputStream input = downloaderCenter.getInputStream(task)) {
                byte[] bytes=new byte[1024];
                int length;
                while ((length=input.read(bytes)) >= 0) {
                    out.write(bytes, 0, length);
                }
                downloaderCenter.finish(correlationId);
            }
        };
        return new ResponseEntity(stream, HttpStatus.OK);
    }
}
