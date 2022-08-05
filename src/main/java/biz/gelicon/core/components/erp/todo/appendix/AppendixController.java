package biz.gelicon.core.components.erp.todo.appendix;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;
import biz.gelicon.core.components.core.sqlaction.SqlAction;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.response.StandardResponse;
import biz.gelicon.core.response.exceptions.NotFoundException;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.service.EMSService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.MimeMap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestController
@Tag(name = "Контроллер Электронный материал", description = "Контроллер для объектов \"Электронный материал\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/files",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class AppendixController {
    private static final Logger logger = LoggerFactory.getLogger(AppendixController.class);

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionAppendix extends GridDataOption {
        @Schema(description = "Фильтры для Электронный материал:" +
                "<ul>" +
                "<li>documentRealId - фильтр по документам, "+
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private AppendixService appendixService;
    @Autowired
    private AppendixRepository appendixRepository;
    @Autowired
    private AuthenticationBean authenticationBean;
    @Autowired
    private EMSService emsService;

    @Operation(summary = "Список объектов \"Электронный материал\"",
            description = "Возвращает постраничный список объектов \"Электронный материал\"")
    @CheckPermission
    @RequestMapping(value = "appendix/getlist", method = RequestMethod.POST)
    public DataResponse<AppendixView> getlist(@RequestBody GridDataOptionAppendix gridDataOption) {

        // проверка фильтров
        boolean documentRealIdFound = gridDataOption.getNamedFilters().stream().anyMatch(nf -> "documentRealId".equals(nf.getName()));
        if(!documentRealIdFound) {
            throw new RuntimeException("Требуется именованный фильтр, пример \"filters\":{\"documentRealId\": 3}");
        }

        List<AppendixView> result = appendixService.getMainList(gridDataOption);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = appendixService.getMainCount(gridDataOption);
        }
        return appendixService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Получение одной записи \"Электронный материал\" по ee идентификатору",
            description = "Возвращает одну запись \"Электронный материал\" по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует возвращается объект с пустым идентификатором " +
                    "и полями, заполненными по умолчанию")
    @CheckPermission
    @RequestMapping(value = "appendix/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public AppendixDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if(id==null) {
            AppendixDTO dto = new AppendixDTO();
            return dto;
        } else {
            Appendix entity = appendixService.findById(id);
            if(entity==null)
                throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
            AppendixDTO dto = new AppendixDTO(entity);
            dto.setDocumentRealIds(appendixService.getDocRealIdsForAppendix(id));
            return dto;
        }
    }


    @Operation(summary = "Сохранение записи",
            description = "Сохранение одного объекта \"Электронный материал\" в таблицу. Объекты с пустым идентификатором добавляются, " +
                    "остальные изменяются. При добавлении определяется владелец идата/время создания. При модификации определяется дата/время модицфикации")
    @CheckPermission
    @RequestMapping(value = "appendix/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_INSERT,AuditKind.CALL_FOR_SAVE_UPDATE})
    public AppendixView save(@RequestBody AppendixDTO appendixDTO) {
        Appendix entity = appendixDTO.toEntity();
        String fileName = entity.getAppendixName();
        if(entity.getAppendixExt() == null){
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);
            entity.setAppendixExt(extension);
        }
        if(entity.getAppendixMime() == null){
            entity.setAppendixMime(MimeMap.getContentTypeFor(fileName));
        }
        Date now = new Date();
        entity.setAppendixDatemodify(now);
        // тип ЭМ по умолчанию
        if(entity.getCapclassId()==null) {
            entity.setCapclassId(Appendix.APPENDIX_TYPE_FILE);
        }
        Appendix result;
        List<Integer> docRealIdsInDB = new ArrayList<>();
        if(entity.getAppendixId()==null) {
            entity.setAppendixDate(now);
            entity.setProguserId(authenticationBean.getCurrentUser().getProguserId());
            result = appendixService.add(entity);
        } else {
            // нельзя переписывать некоторые поля
            Appendix orig = appendixRepository.findById(entity.getAppendixId());
            entity.setAppendixDate(orig.getAppendixDate());
            entity.setAppendixRepositoryid(orig.getAppendixRepositoryid());
            entity.setProguserId(orig.getProguserId());
            // а можем ли мы менять сам ЭМ с текущими связями?
            docRealIdsInDB = appendixService.getDocRealIdsForAppendix(entity.getAppendixId());
            DocumentReal validDocReal = appendixService.validateAccessAppendixForDocumentIds(orig, SqlAction.UPDATE_ACTION, docRealIdsInDB);
            if(validDocReal!=null) {
                throw  new RuntimeException(
                        String.format("Нет прав на изменение ЭМ (Тип = %s) из-за связи с документом %s (Тип = %s)",
                                orig.getCapclassId().toString(), validDocReal.getDocumentRealName(),validDocReal.getDocumentId().toString())
                );
            }
            result = appendixService.edit(entity);
        }
        // привязка/отвязка к требуемыми документами (с проверками доступа)
        bindWithDocumentReal(result,appendixDTO.getDocumentRealIds(),docRealIdsInDB);
        // выбираем представление для одной записи
        return appendixService.getOne(result.getAppendixId());
    }


    @Operation(summary = "Удаление одного или нескольких объектов \"Электронный материал\"",
            description = "Удаление одного или нескольких объектов \"Электронный материал\" по массиву идентификаторов, " +
                    "переданному в теле запроса.")
    @CheckPermission
    @RequestMapping(value = "appendix/delete", method = RequestMethod.POST)
    @Audit(kinds=AuditKind.CALL_FOR_DELETE)
    public String delete(@RequestBody int[] ids) {
        for (int value : ids) {
            Appendix appendix = appendixService.findById(value);
            if (appendixService.checkAccess(appendix, SqlAction.DELETE_ACTION)) {
                appendixRepository.delete(value);
            } else {
                throw new RuntimeException("У пользователя нет прав для удаления объекта \"Электронный материал\" с id=" + value);
            }
        }
        return StandardResponse.SUCCESS;
    }

    @Operation(summary = "Загрузка ЭМ",
            description = "При загрузке файла учитывается право обновления карточки ЭМ всех документов с которыми ЭМ связана")
    @CheckPermission
    @RequestMapping(value = "appendix/upload/{appendixId}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Audit(kinds = AuditKind.UNTYPED)
    public String upload(@RequestParam("upload") MultipartFile file,
                         @PathVariable(name = "appendixId") Integer appendixId){
        Appendix appendix = appendixService.findById(appendixId);
        if(appendix==null)
            throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", appendixId));
        if(appendixService.checkAccess(appendix, SqlAction.UPDATE_ACTION)){

            try (InputStream inputStream = file.getInputStream()) {
                // сохраняем и возвращаем хеш файла
                String hash = emsService.saveIntoStorage(inputStream,appendix.getAppendixExt());
                appendixService.saveHash(appendix.getAppendixId(), hash);
                return StandardResponse.SUCCESS;
            } catch (IOException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else{
            throw new RuntimeException("У пользователя нет прав на загрузку ЭМ");
        }
    }

    @Operation(summary = "Получение ссылки для выгрузки ЭМ",
            description = "Получение ссылки на скачивание ЭМ по его идентификатору карточки. Учитывается право чтения карточки ЭМ всех документов с которыми ЭМ связана")
    @CheckPermission
    @RequestMapping(value = "appendix/download/{appendixId}", method = RequestMethod.POST)
    @Audit(kinds = AuditKind.UNTYPED)
    public String download( @PathVariable(name = "appendixId") Integer appendixId){
        Appendix appendix = appendixService.findById(appendixId);
        if(appendixService.checkAccess(appendix, SqlAction.REED_ACTION)){
            return emsService.getDownloadFromStorageLink(appendixService.getHash(appendix.getAppendixId()),appendix.getAppendixExt());
        }else{
            throw new RuntimeException("У пользователя нет прав на выгрузку ЭМ.");
        }
    }

    private void bindWithDocumentReal(Appendix entity, List<Integer> needIds, List<Integer> docRealIdsInDB) {
        if(needIds == null){
            needIds =  new ArrayList<>();
        }
        // находим что вставить
        List<Integer> insIds = needIds.stream().collect(Collectors.toList());
        insIds.removeAll(docRealIdsInDB);
        // находим что удалить
        List<Integer> delIds = docRealIdsInDB.stream().collect(Collectors.toList());
        delIds.removeAll(needIds);

        appendixService.setDocumentRealIdsForAppendix(entity.getAppendixId(), insIds,delIds);
    }

}
