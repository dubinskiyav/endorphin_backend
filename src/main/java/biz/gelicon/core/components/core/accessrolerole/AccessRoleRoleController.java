package biz.gelicon.core.components.core.accessrolerole;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.ConstantForControllers;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Роли роли", description = "Контроллер для объектов 'Роли роли'")
@RequestMapping(value = "/v" + Config.CURRENT_VERSION + "/apps/admin/credential",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class AccessRoleRoleController {

    private static final Logger logger = LoggerFactory.getLogger(AccessRoleRoleController.class);

    @Autowired
    private AccessRoleRoleService accessRoleRoleService;

    // Главный запрос. Используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/accessrolerole/mainSQL.sql")
    Resource mainSQL;
    public static final String ALIAS_MAIN = "T";

    @Operation(summary = ConstantForControllers.GETLIST_OPERATION_SUMMARY,
            description = ConstantForControllers.GETLIST_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "accessrolerole/getlist", method = RequestMethod.POST)
    public DataResponse<AccessRoleRoleView> getlist(
            @RequestBody GridDataOption gridDataOption) {
        // dav
        // FullTextJoin будем делать вручную
        Query.QueryBuilder<AccessRoleRoleView> queryBuilder = new Query.QueryBuilder<>(mainSQL);
        String fts = "";
        if (gridDataOption.isFullTextSearch()) {
            fts = gridDataOption.buildFullTextJoin("accessrolerole", ALIAS_MAIN)
                    + " AND lower(ft_.fulltext) like '%" + gridDataOption.getSearch() + "%'";
            queryBuilder.injectSQL("/*FullTextJoin*/", fts);
            gridDataOption.setSearch(null);
           // logger.info(fts);
        }
        List<AccessRoleRoleView> result = queryBuilder
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                //.setFrom(gridDataOption.buildFullTextJoin("accessrolerole", ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(AccessRoleRoleView.class, ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(AccessRoleRoleView.class)
                .execute();

        int total = 0;
        if (gridDataOption.getPagination().getPageSize() > 0) {
            total = queryBuilder
                    .setMainAlias(ALIAS_MAIN)
                    //.setFrom(gridDataOption.buildFullTextJoin("accessrolerole", ALIAS_MAIN))
                    .setPredicate(
                            gridDataOption.buildPredicate(AccessRoleRoleView.class, ALIAS_MAIN))
                    .setParams(gridDataOption.buildQueryParams())
                    .build(AccessRoleRoleView.class)
                    .count();
        }
        return BaseService.buildResponse(result, gridDataOption, total);
    }

    @Operation(summary = ConstantForControllers.DELETE_OPERATION_SUMMARY,
            description = ConstantForControllers.DELETE_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "accessrolerole/delete", method = RequestMethod.POST)
    @Audit(kinds = {AuditKind.CALL_FOR_DELETE})
    public String delete(@RequestBody int[] ids) {
        // сброс кэша
        for (int i = 0; i < ids.length; i++) {
            //clearAuthCasheForRole(ids[i]);
        }
        accessRoleRoleService.deleteByIds(ids);
        return "{\"status\": \"success\"}";
    }


    /*
    @Operation(summary = ConstantForControllers.GET_OPERATION_SUMMARY,
            description = ConstantForControllers.GET_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "accessrolerole/get", method = RequestMethod.POST)
    @Audit(kinds = {AuditKind.CALL_FOR_EDIT, AuditKind.CALL_FOR_ADD})
    public AccessRoleRoleDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if (id == null) {
            AccessRoleRoleDTO dto = new AccessRoleRoleDTO();
            dto.setAccessRoleRoleVisible(1);
            return dto;
        } else {
            AccessRoleRole entity = accessRoleService.findById(id);
            if (entity == null) {
                throw new NotFoundException(
                        String.format("Запись с идентификатором %s не найдена", id));
            }
            return new AccessRoleRoleDTO(entity);
        }
    }

    @Operation(summary = ConstantForControllers.SAVE_OPERATION_SUMMARY,
            description = ConstantForControllers.SAVE_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "accessrolerole/save", method = RequestMethod.POST)
    @Audit(kinds = {AuditKind.CALL_FOR_SAVE_INSERT, AuditKind.CALL_FOR_SAVE_UPDATE})
    public AccessRoleRoleView save(@RequestBody AccessRoleRoleDTO accessRoleDTO) {
        AccessRoleRole entity = accessRoleDTO.toEntity();
        AccessRoleRole result;
        if (entity.getAccessRoleRoleId() == null) {
            result = accessRoleService.add(entity);
        } else {
            result = accessRoleService.edit(entity);
        }
        clearAuthCasheForRole(result.getAccessRoleRoleId());
        // выбираем представление для одной записи
        return accessRoleService.getOne(result.getAccessRoleRoleId());

    }

    private void clearAuthCasheForRole(int id) {
        List<Proguser> list = progUserRepository.findByRole(id);
        list.forEach(pu -> {
            authenticationCashe.clearByUserName(pu.getProguserName());
        });
    }

 */
}
