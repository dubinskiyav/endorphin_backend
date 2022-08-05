package biz.gelicon.core.components.core.capresource;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.StandardResponse;
import biz.gelicon.core.response.exceptions.NotFoundException;
import biz.gelicon.core.security.UserDetailsImpl;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.UsefulUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Манипулирование артефактами", description = "Контроллер обладает функциями модификации, выбора артефактов")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/admin/config",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class ArtifactController {

    @Schema(description = "Параметры выборки для объектов \"Артефакт\"")
    public static class GridDataOptionArtifact extends GridDataOption {
        @Schema(description = "Фильтры для Артефакт:" +
                "<ul>" +
                "<li> resourceTypeId - тип артефакта. Для выбора всех типов 0 или пропустить" +
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
    @Autowired
    private ArtifactService artifactService;


    @Operation(summary = "Список объектов \"Артефакт\"",
            description = "Возвращает постраничный список объектов \"Артефакт\"")
    @CheckPermission
    @RequestMapping(value = "artifact/getlist", method = RequestMethod.POST)
    public DataResponse<ArtifactView> getlist(@RequestBody GridDataOptionArtifact gridDataOption) {
        boolean resourceTypeIdFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "resourceTypeId".equals(nf.getName()));
        if(!resourceTypeIdFound) {
            gridDataOption.getNamedFilters().add(new GridDataOption.NamedFilter("resourceTypeId",0));
        }

        List<ArtifactView> result = artifactService.getMainList(gridDataOption);
        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = artifactService.getMainCount(gridDataOption);
        }
        return artifactService.buildResponse(result,gridDataOption,total);
    }


    @Operation(summary = "Получение одной записи \"Артефакт\" по ee идентификатору",
            description = "Возвращает одну запись \"Артефакт\" по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует возвращается объект с пустым идентификатором " +
                    "и полями, заполненными по умолчанию")
    @CheckPermission
    @RequestMapping(value = "artifact/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public ArtifactDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if(id==null) {
            ArtifactDTO dto = new ArtifactDTO();
            return dto;
        } else {
            Artifact entity = artifactService.findById(id);
            if(entity==null)
                throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
            ArtifactDTO dto = new ArtifactDTO(entity);
            // загружаем специфику
            artifactService.loadForConstant(dto);
            artifactService.loadForAttribute(dto);
            // загружаем привязки
            dto.setArtifactApplicationIds(artifactRepository.getLinkWithApplications(dto.getArtifactId()));
            dto.setArtifactDocumentIds(artifactRepository.getLinkWithDocuments(dto.getArtifactId()));

            return dto;
        }
    }

    @Operation(summary = "Сохранение записи",
            description = "Сохранение одного объекта \"Артефакт\" в таблицу. Объекты с пустым идентификатором добавляются, " +
                    "остальные изменяются")
    @CheckPermission
    @RequestMapping(value = "artifact/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_INSERT,AuditKind.CALL_FOR_SAVE_UPDATE})
    public ArtifactView save(@RequestBody ArtifactDTO artifactDTO, Authentication authentication) {

        Artifact entity = artifactDTO.toEntity();
        Artifact result;
        // дату и пользователя модификации
        entity.setArtifactLastmodify(new Date());
        Proguser pu = ((UserDetailsImpl) authentication.getPrincipal()).getProgUser();
        entity.setLastProguserId(pu.getProguserId());

        if(entity.getArtifactId()==null) {
            // устанавливаем дату создания
            entity.setArtifactDate(new Date());
            result = artifactService.add(entity);
        } else {
            // для поддержки изменения старых ресурсов
            Artifact original = artifactService.findById(entity.getArtifactId());
            if(original!=null) {
                entity.setResourcetrantypeId(original.getResourcetrantypeId());
                entity.setCapconfigId(original.getCapconfigId());
                entity.setProguserId(original.getProguserId());
            }
            result = artifactService.edit(entity);
        }
        // сохраняем специфику
        artifactService.saveConstant(artifactDTO,result);
        artifactService.saveAttribute(artifactDTO,result);
        // сохраняем приязки
        artifactService.saveApplicationBinding(artifactDTO,result);
        artifactService.saveDocumentBinding(artifactDTO,result);

        // выбираем представление для одной записи
        return artifactService.getOne(result.getArtifactId());

    }

    @Operation(summary = "Удаление одного или нескольких объектов \"Артефакт\"",
            description = "Удаление одного или нескольких объектов \"Артефакт\" по массиву идентификаторов, " +
                    "переданному в теле запроса.")
    @CheckPermission
    @RequestMapping(value = "artifact/delete", method = RequestMethod.POST)
    @Audit(kinds=AuditKind.CALL_FOR_DELETE)
    public String delete(@RequestBody int[] ids) {
        checkDelete(ids);
        artifactService.deleteByIds(ids);
        return StandardResponse.SUCCESS;
    }

    private void checkDelete(int[] ids) {
        for (int id :ids) {
            Artifact artifact = artifactRepository.findById(id);
            if(UsefulUtils.indexOf(ArtifactKinds.getExternalResourceType(),artifact.getResourceTypeId())>=0) {
                throw new RuntimeException(String.format("Удаление артефактов данного типа запрещено. Тип: %s",
                        ArtifactKinds.values()[artifact.getResourceTypeId()-1].toString()));
            }
        }
    }


}
