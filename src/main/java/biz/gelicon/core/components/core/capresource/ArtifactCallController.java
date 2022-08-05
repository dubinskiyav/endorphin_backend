package biz.gelicon.core.components.core.capresource;

import biz.gelicon.artifacts.ArtifactDescription;
import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.artifacts.ArtifactDescriptionImpl;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.ObjectResponse;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Выполнение артефактов", description = "Контроллер обладает функциями выбора и выполнения артефактов (всех, кроме печатных форм)")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/artifacts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class ArtifactCallController {

    @Schema(description = "Параметры выборки для \"Описания артефакта\"")
    public static class GridDataOptionArtifactDesk extends GridDataOption {
        @Schema(description = "Фильтры для объекта \"Описания артефакта\":" +
                "<ul>" +
                "<li>module - код модуля" +
                "<li>entity - код сущности" +
                "<li>kind - тип артефакта. Соответствует resourcetype_id" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private ArtifactManagerImpl manager;
    @Autowired
    private ArtifactRepository artifactRepository;


    @Operation(summary = "Список объектов для вызова",
            description = "Возвращает полный список объектов \"Описание артефакта\" для вызова из модуля или сущности")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "list-of-call", method = RequestMethod.POST)
    public DataResponse<ArtifactDescView> getlist(@RequestBody GridDataOptionArtifactDesk gridDataOption, Authentication authentication) {
        UserDetailsImpl ud = (UserDetailsImpl) authentication.getPrincipal();
        String modCode = (String) gridDataOption.getFilters().get("module");
        String entityCode = (String) gridDataOption.getFilters().get("entity");
        Integer kind = (Integer) gridDataOption.getFilters().get("kind");

        if(kind==null)
            throw new RuntimeException("Необходим тип артефакта");
        if(entityCode!=null && modCode!=null)
            throw new RuntimeException("Артефакт может запрашиваться либо для модуля либо для сущности");
        if(entityCode==null && modCode==null)
            throw new RuntimeException("Параметры запроса пустые");

        ArtifactKinds akind = ArtifactKinds.values()[kind.intValue() - 1];

        // получаем все зарегистрированные в serviceproviders артефакты
        List<ArtifactDescription> artifacts;
        if(modCode!=null) {
            artifacts= manager.getArtifacts(akind).stream()
                    .filter(r -> modCode.equalsIgnoreCase(((ArtifactDescriptionImpl)r).getUnitName()))
                    .collect(Collectors.toList());
        } else {
            artifacts= manager.getArtifacts(akind).stream()
                    .filter(r -> entityCode.equalsIgnoreCase(((ArtifactDescriptionImpl)r).getEntityName()))
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
            List<Artifact> allowArtifacts = artifactRepository.getAllowArtifacts(ud.getProgUser().getProguserId(),akind);
            artifacts = artifacts.stream()
                    .filter(a->allowArtifacts.stream().anyMatch(r->a.getCode().equalsIgnoreCase(r.getArtifactCode())))
                    .collect(Collectors.toList());
        }

        return BaseService.buildResponse(artifacts.stream()
                .map(r->new ArtifactDescView(r))
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Выполнение",
            description = "Выполняет код артефакта с переданными параметрами")
    @CheckPermission
    @RequestMapping(value = "run", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.RUN_ARTIFACT})
    public ObjectResponse run(@RequestBody ArtifactDescDTO artifact, Authentication authentication) {
        Proguser pu = ((UserDetailsImpl) authentication.getPrincipal()).getProgUser();
        ArtifactKinds akind = ArtifactKinds.values()[artifact.getKind().intValue() - 1];
        // поиск в ограничениях
        if(artifactRepository.isRestricted(artifact.getCode(),akind,pu.getProguserId())) {
            throw new AccessDeniedException("Нет доступа к выполнению артефакта");
        }
        return new ObjectResponse(manager.run(artifact.getKind().intValue(), artifact.getCode(),artifact.getParams()));
    }

}
