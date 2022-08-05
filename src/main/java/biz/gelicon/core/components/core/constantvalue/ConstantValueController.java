package biz.gelicon.core.components.core.constantvalue;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.components.core.constant.ConstantRepository;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.core.constant.Constant;
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
@Tag(name = "Контроллер для объкетов \"Значение константы\"", description = "Контроллер для объектов \"Значения константы\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/admin/resource",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class ConstantValueController {
    private static final Logger logger = LoggerFactory.getLogger(ConstantValueController.class);

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionConstantValue extends GridDataOption {
        @Schema(description = "Фильтры для Значения константы:" +
                "<ul>" +
                "<li> constantId - идентификатор артефакта-константы. Обязателен"+
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private ConstantValueService constantValueService;
    @Autowired
    private ConstantRepository constantRepository;

    @Operation(summary = "Список объектов \"Значения константы\"",
            description = "Возвращает постраничный список объектов \"Значения константы\"")
    @CheckPermission
    @RequestMapping(value = "constantvalue/getlist", method = RequestMethod.POST)
    public DataResponse<ConstantValueView> getlist(@RequestBody GridDataOptionConstantValue gridDataOption) {
        boolean constantIdFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "constantId".equals(nf.getName()));
        if(!constantIdFound) {
            throw new RuntimeException("Требуется именованный фильтр, пример \"filters\":{\"constantId\": 3}");
        }
        List<ConstantValueView> result = constantValueService.getMainList(gridDataOption);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = constantValueService.getMainCount(gridDataOption);
        }
        return constantValueService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Получение одной записи \"Значения константы\" по ee идентификатору",
            description = "Возвращает одну запись \"Значения константы\" по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует возвращается объект с пустым идентификатором " +
                    "и полями, заполненными по умолчанию")
    @CheckPermission
    @RequestMapping(value = "constantvalue/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public ConstantValueDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if(id==null) {
            return new ConstantValueDTO();
        } else {
            ConstantValue entity = constantValueService.findById(id);
            if(entity==null)
                throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
            Constant c = constantRepository.findById(entity.getConstantId());
            if(c==null)
                throw new RuntimeException(String.format("Константа с id=%d не найдена", entity.getConstantId()));
            ConstantValueDTO dto = new ConstantValueDTO(entity);
            constantValueService.loadTypedValue(c,dto);
            return dto;
        }
    }

    @Operation(summary = "Сохранение записи",
            description = "Сохранение одного объекта \"Значения константы\" в таблицу. Объекты с пустым идентификатором добавляются, " +
                    "остальные изменяются")
    @CheckPermission
    @RequestMapping(value = "constantvalue/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_INSERT,AuditKind.CALL_FOR_SAVE_UPDATE})
    public ConstantValueView save(@RequestBody ConstantValueDTO constantValueDTO) {
        ConstantValue entity = constantValueDTO.toEntity();
        // нужно получить для какой константы
        Constant c = constantRepository.findById(entity.getConstantId());
        if(c==null)
            throw new RuntimeException(String.format("Константа с id=%d не найдена", entity.getConstantId()));
        // устанавливаем представление константы. Зависит от ее типа
        entity.setConstantValueDisplay(constantValueService.getDisplayForConstant(c,constantValueDTO));

        ConstantValue result;
        if(entity.getConstantValueId()==null) {
            result = constantValueService.add(entity);
        } else {
            result = constantValueService.edit(entity);
        }
        // сохраненеи типизированного значения
        constantValueService.saveTypedValue(c,result.constantValueId, constantValueDTO);

        // выбираем представление для одной записи
        return constantValueService.getOne(result.getConstantValueId());

    }

    @Operation(summary = "Удаление одного или нескольких объектов \"Значения константы\"",
            description = "Удаление одного или нескольких объектов \"Значения константы\" по массиву идентификаторов, " +
                    "переданному в теле запроса.")
    @CheckPermission
    @RequestMapping(value = "constantvalue/delete", method = RequestMethod.POST)
    @Audit(kinds=AuditKind.CALL_FOR_DELETE)
    public String delete(@RequestBody int[] ids) {
        constantValueService.deleteByIds(ids);
        return StandardResponse.SUCCESS;
    }
}
