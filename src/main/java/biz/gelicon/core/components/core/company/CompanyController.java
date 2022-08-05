package biz.gelicon.core.components.core.company;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.config.EditionTag;
import biz.gelicon.core.dto.SelectDisplayData;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.core.subject.Subject;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.StandardResponse;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.components.core.subject.SubjectService;
import biz.gelicon.core.utils.ConstantForControllers;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.QueryUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Объекты \"Контрагенты\"", description = "Контроллер для справочника \"Контрагенты\"")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/company",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AuthenticationBean authenticationBean;
    @Autowired
    private SubjectService subjectService;

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionCompany extends GridDataOption {
        @Schema(description = "Фильтры для контрагентов:" +
                "<ul>" +
                "<li> attributeId - показывать контрагентов, обладающих данным признаком " +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Operation(summary = ConstantForControllers.GETLIST_OPERATION_SUMMARY,
            description = ConstantForControllers.GETLIST_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "company/getlist", method = RequestMethod.POST)
    public DataResponse<CompanyView> getlist(@RequestBody GridDataOptionCompany gridDataOption) {

        List<CompanyView> result = companyService.getMainList(gridDataOption);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = companyService.getMainCount(gridDataOption);
        }
        return BaseService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Получение одной записи \"Контрагент\" по ee идентификатору",
            description = "Возвращает одну запись \"Контрагент\" по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует возвращается объект с пустым идентификатором " +
                    "и полями, заполненными по умолчанию")
    @CheckPermission
    @RequestMapping(value = "company/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public CompanyDTO get(@RequestBody(required = false) Integer id) {
        if (id == null) {
            CompanyDTO dto = new CompanyDTO();
            //установим значения по умолчанию
            prepareCompany(dto);
            return dto;
        } else {
            CompanyView view = companyService.getOne(id);
            CompanyDTO dto = new CompanyDTO();
            BeanUtils.copyProperties(view, dto);
            dto.setTown(new SelectDisplayData<>(view.getTownId(), view.getTownName()));
            return dto;
        }
    }


    @Operation(summary = "Получение списка контрагентов по поисковой сроке",
            description = "Возвращает список контрагентов в наименовании, коде или описании которых встречается поисковая строка")
    @CheckPermission
    @RequestMapping(value = "company/find", method = RequestMethod.POST)
    public List<CompanyView> find(@RequestBody CompanyController.CompanySearchOption options) {
        //Защита от коротких поисковых строк
        if(options.getSearch()!=null && options.getSearch().length()<4) {
            return new ArrayList<>();
        }
        GridDataOption.Builder builer = new GridDataOption.Builder()
                .search(options.getSearch())
                .addSort("companyName", Sort.Direction.ASC)
                .pagination(1, QueryUtils.UNLIMIT_PAGE_SIZE);
        if(options.getAttributeId()!=null) {
            builer.addFilter("attributeId",options.getAttributeId());
        }
        GridDataOption qopt = builer.build();
        return companyService.getListForFind(qopt);
    }

    @Schema(description = "Набор параметров для поиска")
    public static class CompanySearchOption {
        @Schema(description = "Строка поиска")
        private String search;
        @Schema(description = "Идентификатор аттрибута")
        private Integer attributeId;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }

        public Integer getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(Integer attributeId) {
            this.attributeId = attributeId;
        }
    }


    @Operation(summary = "Возвращает список филиалов",description = "Возвращает список филиалов",parameters = {
            @Parameter(name="id",required = true,schema = @Schema(type = "int"),description = "Идентификатор головной организации")
    })
    @CheckPermission
    @RequestMapping(value = "company/branches", method = RequestMethod.POST)
    public List<CompanyView> getBranches(@RequestBody Integer id) {
        List<Company> branches = companyRepository.getBranchesAsCompany(id);
        return branches.stream()
                .map(c->new CompanyView(c))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Сохранение записи \"Контрагент\".",
            description = "Сохранение одного объекта \"Контрагент\" в таблицу. Объекты с пустым идентификатором добавляются, " +
                    "остальные изменяются")
    @CheckPermission
    @RequestMapping(value = "company/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_INSERT,AuditKind.CALL_FOR_SAVE_UPDATE})
    public CompanyView save(@RequestBody CompanyDTO companyDTO) {
        prepareCompany(companyDTO);
        Company entity = companyDTO.toEntity();
        Company result;
        Proguser pu = authenticationBean.getCurrentUser();
        //Заполним поля по умолчанию для объекта subject
        Subject subject = companyDTO.toSubject();
        if (entity.getCompanyId() == null) {
            //Заполним данные по пользователю
            subject.setProguserId(pu.getProguserId());
            subject.setLastProguserId(pu.getProguserId());
            //Установим дату создания = текущая дата
            subject.setSubjectDatecreate(new Date());
            //вставим subject и company
            Subject subjectResult = subjectService.add(subject);
            entity.setCompanyId(subjectResult.getSubjectId());
            result = companyService.add(entity);
        } else {
            Subject model = companyService.getSubject(companyDTO.getCompanyId());
            //Заполним данные по пользователю
            subject.setProguserId(model.getProguserId());
            subject.setLastProguserId(pu.getProguserId());
            //Установим дату создания = текущая дата
            subject.setSubjectDatecreate(model.getSubjectDatecreate());
            result = companyService.edit(entity);
        }
        if(Config.CURRENT_EDITION_TAG == EditionTag.GELICON_UTILITIES){
            //Вставим ИНН и КПП для контрагента в таблицу companyCode
            companyService.saveCodeGU(result.getCompanyId(), companyDTO.getCompanyInn(), companyDTO.getCompanyKpp());
        }else{
            //Вставим КПП для контрагента в таблицу companyCode
            companyService.saveCodeERP(result.getCompanyId(), companyDTO.getCompanyKpp());
        }
        // выбираем представление для одной записи
        return companyService.getOne(result.getCompanyId());

    }

    @Operation(summary = "Удаление одного или нескольких объектов \"Контрагент\"",
            description = "Удаление одного или нескольких объектов \"Контрагент\" по массиву идентификаторов, " +
                    "переданному в теле запроса.")
    @CheckPermission
    @RequestMapping(value = "company/delete", method = RequestMethod.POST)
    @Audit(kinds=AuditKind.CALL_FOR_DELETE)
    public String delete(@RequestBody int[] ids) {
        companyService.deleteCompanyCode(ids);
        companyService.deleteSubject(ids);
        companyService.deleteByIds(ids);
        return StandardResponse.SUCCESS;
    }

    //Метод для заполнения значений по умолчанию
    private void prepareCompany(CompanyDTO companyDTO){
        //Скопируем адрес и населенный пункт в фактические значения
        if (companyDTO.getCompanyAddress() != null) companyDTO.setCompanyAddressFact(companyDTO.getCompanyAddress());
        if (companyDTO.getTown() != null) companyDTO.setTownFactId(companyDTO.getTown().getValue());
        if(companyDTO.getCompanyUpdDate() == null) companyDTO.setCompanyUpdDate(new Date());
        if(companyDTO.getCompanyDuplicate() == null) companyDTO.setCompanyDuplicate(0);
        if(companyDTO.getCompanyBlockFlag() == null) companyDTO.setCompanyBlockFlag(0);
        if(companyDTO.getCompanyPhysicalPerson() == null) companyDTO.setCompanyPhysicalPerson(0);
        if(companyDTO.getCompanyBank() == null) companyDTO.setCompanyBank(0);
        if(Config.CURRENT_EDITION_TAG == EditionTag.GELICON_UTILITIES
                && companyDTO.getCompanyExcavation() == null) companyDTO.setCompanyExcavation(0);
    }

}

