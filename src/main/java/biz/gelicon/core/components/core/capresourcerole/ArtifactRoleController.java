package biz.gelicon.core.components.core.capresourcerole;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckAdminPermission;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.dto.AllowOrDenyArtifact;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.response.StandardResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Артефакт", description = "Контроллер, управляющий правами объектов \"Артефакт\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/admin/credential",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class ArtifactRoleController {
    private static final Logger logger = LoggerFactory.getLogger(ArtifactRoleController.class);

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionArtifact extends GridDataOption {
        @Schema(description = "Фильтры для Артефакт:" +
                "<ul>" +
                "<li> accessroleId - при установленном идентификаторе, в колонке 'Доступ', " +
                "указывается 1 для связанных с данной ролью артефактов " +
                "<li> resourceTypeId - тип артефакта" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private ArtifactRoleService artifactRoleService;

    @Operation(summary = "Список объектов \"Артефакт\"",
            description = "Возвращает постраничный список объектов \"Артефакт\" с информацией о включении в указанную роль")
    @CheckPermission
    @RequestMapping(value = "artifactrole/getlist", method = RequestMethod.POST)
    public DataResponse<ArtifactRoleView> getlist(@RequestBody GridDataOptionArtifact gridDataOption) {
        boolean accessroleFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "accessRoleId".equals(nf.getName()));
        if(!accessroleFound)
            throw new RuntimeException("Требуется именованный фильтр, пример \"filters\":{\"accessRoleId\": 3}");
        boolean resourceTypeIdFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "resourceTypeId".equals(nf.getName()));
        if(!resourceTypeIdFound) {
            gridDataOption.getNamedFilters().add(new GridDataOption.NamedFilter("resourceTypeId",0));
        }

        List<ArtifactRoleView> result = artifactRoleService.getMainList(gridDataOption);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = artifactRoleService.getMainCount(gridDataOption);
        }
        return BaseService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Установить запрет на \"Артефакт\"",
            description = "Установить запрет на объекты \"Артефакт\", чьи идентификаторы переданы, для роли, чей идентификатор также передан.")
    @CheckAdminPermission
    @RequestMapping(value = "artifactrole/restrict", method = RequestMethod.POST)
    @ResponseBody
    @Audit(kinds={AuditKind.SECURITY_SYSTEM})
    public String restrict(@RequestBody AllowOrDenyArtifact allowOrDeny) {
        artifactRoleService.restrict(allowOrDeny.getAccessRoleId(),allowOrDeny.getArtifactIds());
        return StandardResponse.SUCCESS;
    }

    @Operation(summary = "Установить разрешение на \"Артефакт\"",
            description = "Установить разрешение на объекты \"Артефакт\", чьи идентификаторы переданы, для роли, чей идентификатор также передан.")
    @CheckAdminPermission
    @RequestMapping(value = "artifactrole/allow", method = RequestMethod.POST)
    @ResponseBody
    @Audit(kinds={AuditKind.SECURITY_SYSTEM})
    public String allow(@RequestBody AllowOrDenyArtifact allowOrDeny) {
        artifactRoleService.allow(allowOrDeny.getAccessRoleId(),allowOrDeny.getArtifactIds());
        return StandardResponse.SUCCESS;
    }

    @Operation(summary = "Сбросить ограничение/разрешения на объект \"Артефакт\" у роли",
            description = "Сбросить ограничение/разрешения на объекты \"Артефакт\", чьи идентификаторы переданы, у роли, чей идентификатор также передан.")
    @CheckAdminPermission
    @RequestMapping(value = "artifactrole/drop", method = RequestMethod.POST)
    @ResponseBody
    @Audit(kinds={AuditKind.SECURITY_SYSTEM})
    public String drop(@RequestBody AllowOrDenyArtifact allowOrDeny) {
        artifactRoleService.dropLink(allowOrDeny.getAccessRoleId(),allowOrDeny.getArtifactIds());
        return StandardResponse.SUCCESS;
    }

}
