package biz.gelicon.core.components.core.proguser;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckAdminPermission;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.core.proguserchannel.ProguserChannel;
import biz.gelicon.core.components.core.progusergroup.Progusergroup;
import biz.gelicon.core.components.core.proguserrole.ProguserRoleDTO;
import biz.gelicon.core.components.core.subject.Subject;
import biz.gelicon.core.components.core.worker.Worker;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.dto.*;
import biz.gelicon.core.jobs.JobDispatcher;
import biz.gelicon.core.components.core.capcode.CapCodeRepository;
import biz.gelicon.core.components.core.subject.SubjectRepository;
import biz.gelicon.core.components.core.worker.WorkerRepository;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.StandardResponse;
import biz.gelicon.core.response.exceptions.IncorrectUserOrPasswordException;
import biz.gelicon.core.response.exceptions.NotFoundException;
import biz.gelicon.core.security.*;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.ConstantForControllers;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.QueryUtils;
import biz.gelicon.core.components.core.accessrole.AccessRoleView;
import biz.gelicon.core.components.core.proguserrole.ProguserAccessRoleView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Пользователи", description = "Контроллер для объектов \"Пользователь\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/admin/credential",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class ProguserController {

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionProguser extends GridDataOption {
        @Schema(description = "Фильтры для объекта Пользователь:" +
                "<ul>" +
                "<li>именованный фильтр status - фильтр по статусу: 1 - только активных, 0 - только заблокированных"
                +
                "<li>именованный фильтр type - фильтр по типу: 1 - только администраторы, 0 - только обычные"
                +
                "<li>быстрый фильтр по совпадению имени " +
                "<li>быстрый фильтр по вхождению имени " +
                "<li>\"filters\":{\"status\": 0, " +
                "<li>             \"type\": 1, " +
                "<li>              \"quick.proguserName.eq\":\"SYSDBA\"," +
                "<li>              \"quick.proguserName.like\":\"S\"}" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private ProguserService proguserService;
    @Autowired
    private CredentialProviderFactory credentialProviderFactory;
    @Autowired
    AuthenticationCashe authenticationCashe;
    @Autowired
    private ACL acl;
    @Autowired()
    @Qualifier("jobACL")
    private JobDispatcher jobACLDispather;
    @Autowired
    private CapCodeRepository capCodeRepository;
    @Autowired
    private ProgUserRepository progUserRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private AuthenticationBean authenticationBean;
    @Autowired
    private WorkerRepository workerRepository;


    @Operation(summary = ConstantForControllers.GETLIST_OPERATION_SUMMARY,
            description = ConstantForControllers.GETLIST_OPERATION_DESCRIPTION)
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "proguser/getlist", method = RequestMethod.POST)
    public DataResponse<ProguserView> getlist(@RequestBody GridDataOptionProguser gridDataOption) {

        gridDataOption.setProcessNamedFilter(filters ->
                filters.stream()
                        .map(f -> {
                            switch (f.getName()) {
                                case "roleId":
                                    // dav
                                    // todo - сделать
                                    Integer roleId = (Integer) f.getValue();
                                    if (roleId != null) {
                                        return ProguserService.ALIAS_MAIN + ".role_Id = "
                                                + roleId;
                                    }
                                    return null;
                                case "status":
                                    Integer status = (Integer) f.getValue();
                                    if (status != null) {
                                        return ProguserService.ALIAS_MAIN + ".proguser_status_id = "
                                                + status;
                                    }
                                    return null;
                                case "type":
                                    Integer type = (Integer) f.getValue();
                                    if (type != null) {
                                        return ProguserService.ALIAS_MAIN + ".proguser_type = "
                                                + type;
                                    }
                                    return null;
                                default:
                                    return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(" and "))
        );


        List<ProguserView> result = proguserService.getMainList(gridDataOption);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = proguserService.getMainCount(gridDataOption);
        }
        return BaseService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = ConstantForControllers.GET_OPERATION_SUMMARY,
            description = ConstantForControllers.GET_OPERATION_DESCRIPTION)
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "proguser/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public ProguserDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if(id==null) {
            Proguser entity = new Proguser();
            entity.setStatusId(CapCode.USER_IS_ACTIVE);
            CapCode status = progUserRepository.getForeignKeyEntity(entity,CapCode.class);
            ProguserDTO dto = new ProguserDTO(entity);
            dto.setStatusDisplay(status.getCapCodeName());
            return dto;
        } else {
            Proguser entity = proguserService.findById(id);
            if(entity==null)
                throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
            ProguserDTO dto = new ProguserDTO(entity);
            // для правильного отображения select
            CapCode status = progUserRepository.getForeignKeyEntity(entity,CapCode.class);
            dto.setStatusDisplay(status.getCapCodeName());

            // выбираем почтовый адрес
            ProguserChannel email = proguserService.getlEmail(id);
            if(email!=null) {
                dto.setProguserchannelAddress(email.getProguserChannelAddress());
            }

            // связь с ОАУ
            Subject sbj = subjectRepository.findSubjectLinkWithUser(entity.getProguserId());
            if(sbj!=null) {
                dto.setSubject(new SelectDisplayData<>(sbj.getSubjectId(),sbj.getSubjectName()));
            }
            // связь с Сотрудником
            Worker worker = workerRepository.findWorkerLinkWithUser(entity.getProguserId());
            if(worker!=null) {
                dto.setWorker(new SelectDisplayData<>(worker.getWorkerId(),worker.getFIO()));
            }

            return dto;
        }
    }

    @Operation(summary = ConstantForControllers.SAVE_OPERATION_SUMMARY,
            description = ConstantForControllers.SAVE_OPERATION_DESCRIPTION)
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "proguser/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_UPDATE,AuditKind.CALL_FOR_SAVE_INSERT})
    public ProguserView save(@RequestBody ProguserDTO proguserDTO) {
        Proguser entity = proguserDTO.toEntity();
        entity.setProguserGroupId(Progusergroup.EVERYONE); // всегда в одну группу
        Proguser result;
        if(entity.getProguserId()==null) {
            result = proguserService.add(entity);
        } else {
            result = proguserService.edit(entity);
        }
        // сброс кэша
        authenticationCashe.clearByUserName(result.getProguserName());
        // сохранение адреса
        proguserService.saveEmail(result.getProguserId(),proguserDTO.getProguserchannelAddress());
        // устанавливаем связь с ОАУ
        SelectDisplayData<Integer> subjectValue = proguserDTO.getSubject();
        subjectRepository.setSubjectLinkWithUser(result.getProguserId(),
                subjectValue!=null?subjectValue.getValue():null);
        // модифицируем атрибут у теущего пользователя
        setSubjectForCurrentUser(proguserDTO, result);

        // устанавливаем связь с Сотрудником
        SelectDisplayData<Integer> workerValue = proguserDTO.getWorker();
        progUserRepository.setWorkerLinkWithUser(result.getProguserId(),
                workerValue!=null?workerValue.getValue():null);

        // выбираем представление для одной записи
        return proguserService.getOne(result.getProguserId());

    }

    private void setSubjectForCurrentUser(@RequestBody ProguserDTO proguserDTO, Proguser result) {
        if(authenticationBean.getCurrentUser().getProguserId()==result.getProguserId()) {
            SelectDisplayData<Integer> subjectValue = proguserDTO.getSubject();
            Subject subject = subjectValue != null ? subjectRepository.findById(subjectValue.getValue()) : null;
            authenticationBean.getCurrentUser()
                    .getAttributes().put(Proguser.PROFILE_ATTR_SUBJECT,subject);
        }
    }

    @Operation(summary = ConstantForControllers.DELETE_OPERATION_SUMMARY,
            description = ConstantForControllers.DELETE_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "proguser/delete", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_DELETE})
    public String delete(@RequestBody int[] ids) {
        // сброс кэша
        for (int i = 0; i < ids.length; i++) {
            ProguserView pu = proguserService.getOne(ids[i]);
            authenticationCashe.clearByUserName(pu.getProguserName());
        }
        proguserService.deleteByIds(ids);
        return StandardResponse.SUCCESS;
    }


    @Operation(summary = "Смена своего пароля. Необходимо знать старый пароль",
            description = "Позволяет поменять собственный пароль пользователя")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "proguser/changepswd", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestBody
                               @Parameter(description = "Новый и старый пароли") PasswordDTO dto, Authentication authentication) {
        Proguser pu = ((UserDetailsImpl) authentication.getPrincipal()).getProgUser();
        // сброс
        authenticationCashe.clearByUserName(pu.getProguserName());
        // получаем провайдера
        CredentialProvider<String> crprovider = credentialProviderFactory.getProvider(CredentialProvider.CredentialProviderType.AuthByPassword);
        if(!crprovider.checkAuthentication(pu.getProguserId(),dto.getOldPassword())) {
            throw new IncorrectUserOrPasswordException();
        }
        crprovider.updateAuthentication(pu.getProguserId(),dto.getNewPassword(),false);
        return StandardResponse.SUCCESS;
    }

    @Operation(summary = "Установка пароля у пользователя",
            description = "Позволяет установить новый пароль у пользователя, включая временный")
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "proguser/setpswd", method = RequestMethod.POST)
    @ResponseBody
    public String setPassword(@RequestBody
                               @Parameter(description = "Новый пароль") NewProgUserPasswordDTO dto) {
        // получаем провайдера
        CredentialProvider<String> crprovider = credentialProviderFactory.getProvider(CredentialProvider.CredentialProviderType.AuthByPassword);
        if(!crprovider.updateAuthentication(dto.getProguserId(),dto.getNewPassword(), dto.getTempFlag()!=0)) {
            throw new RuntimeException("Не удалось установить пароль");
        }
        // сброс
        ProguserView pu = proguserService.getOne(dto.getProguserId());
        authenticationCashe.clearByUserName(pu.getProguserName());

        return StandardResponse.SUCCESS;
    }

    @Operation(summary = "Список ролей пользователя",
            description = "Возвращает список ролей указанного пользователя")
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "proguser/roles/getlist", method = RequestMethod.POST)
    public List<AccessRoleView> accessroles(@RequestBody ProguserRequest request) {
        List<AccessRole> roles  = proguserService.getRoleList(request.getProguserId());
        return roles.stream().map(AccessRoleView::new).collect(Collectors.toList());
    }

    @Operation(summary = "Список ролей пользователя для редактора ролей",
            description = "Возвращает список ролей указанного пользователя")
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "proguser/roles/get", method = RequestMethod.POST)
    public ProguserAccessRoleView getProguserAccessRoles(@RequestBody Integer id) {
        Proguser entity = proguserService.findById(id);
        if(entity==null)
            throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
        List<AccessRole> roles  = proguserService.getRoleList(id);
        ProguserAccessRoleView parv = new ProguserAccessRoleView(entity);
        parv.setAccessRoleIds(roles.stream()
                .map(r->r.getAccessRoleId())
                .collect(Collectors.toList()));
        return parv;
    }


    @Operation(summary = "Сохранить список ролей пользователя",
            description = "Сохраняет список ролей указанного пользователя")
    @CheckPermission
    @RequestMapping(value = "proguser/roles/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.SECURITY_SYSTEM})
    public String accessrolesSave(@RequestBody ProguserRoleDTO proguserRoleDTO) {
        proguserService.saveRoles(proguserRoleDTO.getProguserId(),proguserRoleDTO.getAccessRoleIds());
        // перестраиваем ACL асинхронно
        jobACLDispather.pushJob(()->acl.buildAccessTable());
        // dav
        // кеш сбросим
        String proguserName = proguserService.getOne(proguserRoleDTO.getProguserId()).getProguserName();
        authenticationCashe.clearByUserName(proguserName);

        return StandardResponse.SUCCESS;
    }

    @Schema(description = "Параметры выборки ролей пользователя")
    public static class ProguserRequest {
        @Schema(description = "Идентификатор пользователя")
        private Integer proguserId;

        public ProguserRequest() {
        }

        public ProguserRequest(int proguserId) {
            this.proguserId=proguserId;
        }

        public Integer getProguserId() {
            return proguserId;
        }

        public void setProguserId(Integer proguserId) {
            this.proguserId = proguserId;
        }
    }

    @Operation(summary = "Список пользователей по поисковой сроке",
            description = "Возвращает список пользователей в наименовании, или логине которых встречается поисковая строка")
    // dav убираем за ненадобностью @CheckAdminPermission
    @CheckPermission
    @RequestMapping(value = "proguser/find", method = RequestMethod.POST)
    public List<ProguserView> find(@RequestBody SimpleSearchOption options) {
        //Защита от коротких поисковых строк
        if(options.getSearch()!=null && options.getSearch().length()<4) {
            return new ArrayList<>();
        }
        GridDataOption qopt = new GridDataOption.Builder()
                .search(options.getSearch())
                .addSort("proguserName", Sort.Direction.ASC)
                .pagination(1, QueryUtils.UNLIMIT_PAGE_SIZE)
                .build();
        return proguserService.getListForFind(qopt);
    }

    public static class SimpleSearchOption {
        private String search;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }

}
