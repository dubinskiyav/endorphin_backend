package biz.gelicon.core.components.core.capclass;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.exceptions.NotFoundException;
import biz.gelicon.core.utils.GridDataOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.response.StandardResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Контроллер Классификатор (capclass)", description = "Контроллер для объектов \"Классификатор\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/capclass",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class CapClassController {
    private static final Logger logger = LoggerFactory.getLogger(CapClassController.class);

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionCapClass extends GridDataOption {
        @Schema(description = "Фильтры для Классификатор:" +
                "<ul>" +
                "<li> capClassTypeId - тип классификатора"+
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private CapClassService capClassService;

    @Operation(summary = "Список объектов \"Классификатор\"",
            description = "Возвращает постраничный список объектов \"Классификатор\"")
    @CheckPermission
    @RequestMapping(value = "capclass/getlist", method = RequestMethod.POST)
    public DataResponse<CapClassView> getlist(@RequestBody GridDataOptionCapClass gridDataOption) {
        boolean capClassTypeIdFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "capClassTypeId".equals(nf.getName()));
        if(!capClassTypeIdFound) {
            gridDataOption.getNamedFilters().add(new GridDataOption.NamedFilter("capClassTypeId",0));
        }
        List<CapClassView> result = capClassService.getMainList(gridDataOption);
        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = capClassService.getMainCount(gridDataOption);
        }
        return capClassService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Получение одной записи \"Классификатор\" по ee идентификатору",
            description = "Возвращает одну запись \"Классификатор\" по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует возвращается объект с пустым идентификатором " +
                    "и полями, заполненными по умолчанию")
    @CheckPermission
    @RequestMapping(value = "capclass/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public CapClassDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if(id==null) {
            CapClassDTO dto = new CapClassDTO();
            dto.setCapClassBlockflag(0);
            return dto;
        } else {
            CapClass entity = capClassService.findById(id);
            if(entity==null)
                throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
            return new CapClassDTO(entity);
        }
    }

    @Operation(summary = "Сохранение записи",
            description = "Сохранение одного объекта \"Классификатор\" в таблицу. Объекты с пустым идентификатором добавляются, " +
                    "остальные изменяются")
    @CheckPermission
    @RequestMapping(value = "capclass/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_INSERT,AuditKind.CALL_FOR_SAVE_UPDATE})
    public CapClassView save(@RequestBody CapClassDTO capClassDTO) {
        CapClass entity = capClassDTO.toEntity();
        CapClass result;
        if(entity.getCapClassId()==null) {
            result = capClassService.add(entity);
        } else {
            result = capClassService.edit(entity);
        }

        // выбираем представление для одной записи
        return capClassService.getOne(result.getCapClassId());

    }

    @Operation(summary = "Удаление одного или нескольких объектов \"Классификатор\"",
            description = "Удаление одного или нескольких объектов \"Классификатор\" по массиву идентификаторов, " +
                    "переданному в теле запроса.")
    @CheckPermission
    @RequestMapping(value = "capclass/delete", method = RequestMethod.POST)
    @Audit(kinds=AuditKind.CALL_FOR_DELETE)
    public String delete(@RequestBody int[] ids) {
        capClassService.deleteByIds(ids);
        return StandardResponse.SUCCESS;
    }
}
