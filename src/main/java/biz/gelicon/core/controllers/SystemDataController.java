package biz.gelicon.core.controllers;

import biz.gelicon.core.annotations.MethodPermission;
import biz.gelicon.core.components.core.capclass.CapClassRepository;
import biz.gelicon.core.components.core.capclass.CapClassView;
import biz.gelicon.core.components.core.capclasstype.CapClassTypeRepository;
import biz.gelicon.core.components.core.capclasstype.CapClassTypeView;
import biz.gelicon.core.components.core.capcode.CapCodeRepository;
import biz.gelicon.core.components.core.capcode.CapCodeView;
import biz.gelicon.core.components.core.document.DocumentRepository;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitInfo;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitRepository;
import biz.gelicon.core.components.erp.todo.uniquetype.UniqueTypeRepository;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.core.capclass.CapClass;
import biz.gelicon.core.components.core.capclasstype.CapClassType;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.StringResponse;
import biz.gelicon.core.response.exceptions.NotFoundException;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.ConstantForControllers;
import biz.gelicon.core.utils.QueryUtils;
import biz.gelicon.core.view.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Общесистемные данные",
        description = "Контроллер для общесистемных данных, "
                + "доступных любому авторизированному пользователю")
@RequestMapping(value = "/v" + Config.CURRENT_VERSION + "/apps/system",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
@MethodPermission(noStore = true)
public class SystemDataController {

    @Schema(description = "Параметры для запроса Кодификатора")
    public static class CapCodeRequest {

        @Schema(description = "Тип Кодификатора")
        private Integer capCodeTypeId;

        public Integer getCapCodeTypeId() {
            return capCodeTypeId;
        }

        public void setCapCodeTypeId(Integer capCodeTypeId) {
            this.capCodeTypeId = capCodeTypeId;
        }
    }

    @Schema(description = "Параметры для запроса Классификатора")
    public static class CapClassRequest {

        @Schema(description = "Тип Классификатора")
        private Integer capClassTypeId;

        public Integer getCapClassTypeId() {
            return capClassTypeId;
        }

        public void setCapClassTypeId(Integer capClassTypeId) {
            this.capClassTypeId = capClassTypeId;
        }
    }

    @Autowired
    private CapCodeRepository capCodeRepository;
    @Autowired
    private CapClassRepository capClassRepository;
    @Autowired
    private UniqueTypeRepository uniqueTypeRepository;
    @Autowired
    private CapClassTypeRepository capClassTypeRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentTransitRepository documentTransitRepository;
    @Autowired
    private DocumentRealRepository documentRealRepository;

    @Operation(summary = "Список объектов \"Тип уникальности\"",
            description = ConstantForControllers.GETLIST_FULL_OPERATION_DESCRIPTION)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "uniquetype/getlist", method = RequestMethod.POST)
    public DataResponse<UniqueTypeView> getUniqueList(@RequestBody(required = false) String str) {
        List<UniqueTypeView> types = uniqueTypeRepository.findAll("uniquetype_id").stream()
                .map(UniqueTypeView::new)
                .collect(Collectors.toList());
        return new BaseService<UniqueTypeView>().buildResponse(types);
    }

    @Operation(summary = "Список объектов \"Кодификатор\"",
            description = ConstantForControllers.GETLIST_FULL_OPERATION_DESCRIPTION)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "capcode/getlist", method = RequestMethod.POST)
    public DataResponse<CapCodeView> capCodeGetlist(@RequestBody CapCodeRequest type) {
        if (type.getCapCodeTypeId() == null) {
            throw new RuntimeException("Требуется тип кодификатора");
        }
        List<CapCode> list = capCodeRepository.findWhere("m0.capcodetype_id=:capcodetype_id",
                "capcodetype_id", type.getCapCodeTypeId(),
                QueryUtils.allPagesAndSort("capcode_name"));
        return BaseService.buildResponse(list.stream()
                .map(CapCodeView::new)
                .collect(Collectors.toList()));
    }

    ;

    @Operation(summary = "Список объектов \"Классификатор\"",
            description = ConstantForControllers.GETLIST_FULL_OPERATION_DESCRIPTION)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "capclass/getlist", method = RequestMethod.POST)
    public DataResponse<CapClassView> capClassGetlist(@RequestBody CapClassRequest type) {
        if (type.capClassTypeId == null) {
            throw new RuntimeException("Требуется тип Классификатора");
        }
        List<CapClass> list = capClassRepository.findWhere("m0.capclasstype_id=:capclasstype_id",
                "capclasstype_id", type.getCapClassTypeId(),
                QueryUtils.allPagesAndSort("capclass_name"));
        return BaseService.buildResponse(list.stream()
                .map(CapClassView::new)
                .collect(Collectors.toList()));
    }

    ;


    @Operation(summary = "Список объектов \"Тип классификатора\"",
            description = ConstantForControllers.GETLIST_FULL_OPERATION_DESCRIPTION)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "capclasstype/getlist", method = RequestMethod.POST)
    public DataResponse<CapClassTypeView> getCapClassTypeList(
            @RequestBody(required = false) String str) {
        List<CapClassType> list = capClassTypeRepository.findAll("capclasstype_name");
        return BaseService.buildResponse(list.stream()
                .map(CapClassTypeView::new)
                .collect(Collectors.toList()));
    }

    ;

    @Operation(summary = "Список возможных статусов для всех типов документов",
            description = "Возвращает список типов документов со списком возможных статусов"
                    + " для списка документов")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "document/getstatuslist", method = RequestMethod.POST)
    public Map<Integer, List<DocumentTransitInfo>> getStatusList(
            @RequestBody(required = false) String str) {
        Map<Integer, List<DocumentTransitInfo>> result = new HashMap<>();
        documentRepository.findAll().forEach(d -> {
            List<DocumentTransitInfo> dtList = documentTransitRepository.findByDocument(
                            d.getDocumentId()).stream()
                    .map(o -> new DocumentTransitInfo(o))
                    .collect(Collectors.toList());
            result.put(d.getDocumentId(), dtList);
        });
        return result;
    }

    @Operation(summary = "Получает имя документа",
            description = "Возвращает имя документа по его идентификатору")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "documentreal/getname", method = RequestMethod.POST)
    public StringResponse getDocumentRealName(@RequestBody(required = false) Integer id) {
        DocumentReal doc = documentRealRepository.findById(id);
        if (doc == null) {
            throw new NotFoundException(
                    String.format("Документ с идентификатором %s не найден", id));
        }
        return new StringResponse(doc.getDocumentRealName());
    }


}
