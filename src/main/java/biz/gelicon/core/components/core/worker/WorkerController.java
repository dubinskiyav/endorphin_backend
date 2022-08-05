package biz.gelicon.core.components.core.worker;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.exceptions.NotFoundException;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.QueryUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.response.StandardResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Контроллер Сотрудник", description = "Контроллер для объектов \"Сотрудник\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/person",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class WorkerController {
    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private AuthenticationBean authenticationBean;
    @Autowired
    private WorkerService workerService;

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionWorker extends GridDataOption {
        @Schema(description = "Фильтры для Сотрудник:" +
                "<ul>" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Operation(summary = "Список объектов \"Сотрудник\"",
            description = "Возвращает постраничный список объектов \"Сотрудник\"")
    @CheckPermission
    @RequestMapping(value = "worker/getlist", method = RequestMethod.POST)
    public DataResponse<WorkerView> getlist(@RequestBody GridDataOptionWorker gridDataOption) {

        // проверка фильтров
        /*
            Если фильтр необязательный
            boolean capClassTypeIdFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "capClassTypeId".equals(nf.getName()));
            if(!capClassTypeIdFound) {
                gridDataOption.getNamedFilters().add(new GridDataOption.NamedFilter("capClassTypeId",0));
            }
            Если фильтр обязательный
            boolean accessroleFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "accessRoleId".equals(nf.getName()));
            if(!accessroleFound) {
                throw new RuntimeException("Требуется именованный фильтр accessRoleId");
            }
         */

        List<WorkerView> result = workerService.getMainList(gridDataOption);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = workerService.getMainCount(gridDataOption);
        }
        return workerService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Получение списка сотрудников по поисковой сроке",
            description = "Возвращает список сотрудников в фамилии, имени, отчестве которых встречается поисковая строка")
    @CheckPermission
    @RequestMapping(value = "worker/find", method = RequestMethod.POST)
    public List<WorkerView> find(@RequestBody WorkerController.WorkerSearchOption options) {
        if(options.getSearch()!=null && options.getSearch().length()<4) {
            return new ArrayList<>();
        }
        GridDataOption.Builder builder = new GridDataOption.Builder()
                .search(options.getSearch())
                .addSort("workerFamilyname", Sort.Direction.ASC)
                .addSort("workerFirstname", Sort.Direction.ASC)
                .addSort("workerSurname", Sort.Direction.ASC)
                .pagination(1, QueryUtils.UNLIMIT_PAGE_SIZE);
        GridDataOption qopt = builder.build();
        return workerService.getListForFind(qopt);
    }

    @Schema(description = "Набор параметров для поиска")
    public static class WorkerSearchOption {
        @Schema(description = "Строка поиска")
        private String search;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }


    @Operation(summary = "Получение одной записи \"Сотрудник\" по ee идентификатору",
            description = "Возвращает одну запись \"Сотрудник\" по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует возвращается объект с пустым идентификатором " +
                    "и полями, заполненными по умолчанию")
    @CheckPermission
    @RequestMapping(value = "worker/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public WorkerDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if(id==null) {
            return new WorkerDTO();
        } else {
            Worker entity = workerService.findById(id);
            if(entity==null)
                throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
            return new WorkerDTO(entity);
        }
    }

    @Operation(summary = "Сохранение записи",
            description = "Сохранение одного объекта \"Сотрудник\" в таблицу. Объекты с пустым идентификатором добавляются, " +
                    "остальные изменяются")
    @CheckPermission
    @RequestMapping(value = "worker/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_INSERT,AuditKind.CALL_FOR_SAVE_UPDATE})
    public WorkerView save(@RequestBody WorkerDTO workerDTO) {
        Worker entity = workerDTO.toEntity();
        Worker result;
        if(entity.getWorkerId()==null) {
            result = workerService.add(entity);
        } else {
            result = workerService.edit(entity);
        }

        // выбираем представление для одной записи
        return workerService.getOne(result.getWorkerId());

    }

    @Operation(summary = "Удаление одного или нескольких объектов \"Сотрудник\"",
            description = "Удаление одного или нескольких объектов \"Сотрудник\" по массиву идентификаторов, " +
                    "переданному в теле запроса.")
    @CheckPermission
    @RequestMapping(value = "worker/delete", method = RequestMethod.POST)
    @Audit(kinds=AuditKind.CALL_FOR_DELETE)
    public String delete(@RequestBody int[] ids) {
        workerService.deleteByIds(ids);
        return StandardResponse.SUCCESS;
    }

    @Operation(summary = "Получение записи \"Сотрудник\" для текущего пользователя",
            description = "Возвращает одну запись \"Сотрудник\" для текущего пользователя. " +
                    "Если пользователь не соединен с сотрудником будет возвращено пустое значение.")
    @CheckPermission
    @RequestMapping(value = "worker/getbyuser", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.UNTYPED})
    public WorkerDTO getByUser(@RequestBody(required = false) String nix) {
        Worker entity = workerService.getByUser(authenticationBean.getCurrentUser().getProguserId());
        if(entity==null) {
            return null;
        }
        return new WorkerDTO(entity);
    }

}
