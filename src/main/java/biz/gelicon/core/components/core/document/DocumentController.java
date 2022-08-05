package biz.gelicon.core.components.core.document;

import biz.gelicon.core.annotations.Audit;
import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.audit.AuditKind;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
import biz.gelicon.core.components.core.capclass.CapClass;
import biz.gelicon.core.components.core.capclass.CapClassRepository;
import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRole;
import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRoleRepository;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransit;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitRepository;
import biz.gelicon.core.components.erp.todo.uniquetype.UniqueTypeRepository;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.deltads.MemoryDataSet;
import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRoleDTO;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitDTO;
import biz.gelicon.core.dto.SelectDisplayData;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Контроллер Тип документа", description = "Контроллер для объектов \"Тип документа\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/admin/config",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
    private static final boolean logFlag = true;

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionDocument extends GridDataOption {
        @Schema(description = "Фильтры для Тип документа:" +
                "<ul>" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private DocumentService documentService;
    @Autowired
    private UniqueTypeRepository uniqueTypeRepository;
    @Autowired
    private DocumentTransitRepository documentTransitRepository;
    @Autowired
    private AccessRoleRepository accessRoleRepository;
    @Autowired
    private DocumentAppendixRoleRepository documentAppendixRoleRepository;
    @Autowired
    private CapClassRepository сapClassRepository;

    @Operation(summary = "Список объектов \"Тип документа\"",
            description = "Возвращает постраничный список объектов \"Тип документа\"")
    @CheckPermission
    @RequestMapping(value = "document/getlist", method = RequestMethod.POST)
    public DataResponse<DocumentView> getlist(@RequestBody GridDataOptionDocument gridDataOption) {
        List<DocumentView> result = documentService.getMainList(gridDataOption);
        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = documentService.getMainCount(gridDataOption);
        }
        return documentService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Получение одной записи \"Тип документа\" по ee идентификатору",
            description = "Возвращает одну запись \"Тип документа\" по ee идентификатору, " +
                    "если он указан. Если идентификатор отсутствует возвращается объект с пустым идентификатором " +
                    "и полями, заполненными по умолчанию")
    @CheckPermission
    @RequestMapping(value = "document/get", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_EDIT,AuditKind.CALL_FOR_ADD})
    public DocumentDTO get(@RequestBody(required = false) Integer id) {
        // для добавления
        if(id==null) {
            throw new RuntimeException("Функция вставки не поддерживается");
        } else {
            Document entity = documentService.findById(id);
            if(entity==null)
                throw new NotFoundException(String.format("Запись с идентификатором %s не найдена", id));
            DocumentDTO dto = new DocumentDTO(entity);
            dto.setUniqueType(uniqueTypeRepository.findForDocument(id));
            // формируем статусы
            buildDocumentTransits(dto);
            // фоормируем доступы на ЭМ
            buildDocumentAppedixRoles(dto);

            return dto;
        }
    }

    private void buildDocumentTransits(DocumentDTO dto) {
        Map<Integer,String> mapAccessRoles = new HashMap<>();
        accessRoleRepository.findAll().stream().forEach(ar->
            mapAccessRoles.put(ar.getAccessRoleId(),ar.getAccessRoleName())
        );

        List<DocumentTransit> allTransits = documentTransitRepository.findByDocument(dto.getDocumentId());
        Map<Integer,String> mapTransitChilds  = new HashMap<>();
        allTransits.stream().forEach(tr->
                mapTransitChilds.put(tr.getDocumentTransitId(),tr.getDocumentTransitName())
        );

        List<DocumentTransitDTO> translist = allTransits.stream()
                .map(dt->{
                    DocumentTransitDTO dtDto = new DocumentTransitDTO(dt);
                    dtDto.buildAccessRoles(dt, id->mapAccessRoles.get(id));
                    dtDto.buildTransitChildIds(dt, id->mapTransitChilds.get(id));
                    return dtDto;
                })
                .collect(Collectors.toList());

        dto.setTransits(new MemoryDataSet<DocumentTransitDTO>(translist));
    }

    private void buildDocumentAppedixRoles(DocumentDTO dto) {
        AtomicInteger num = new AtomicInteger(1);
        List<DocumentAppendixRoleDTO> list = documentAppendixRoleRepository
                .findWhere("document_id=:documentId","documentId",dto.getDocumentId())
                .stream()
                .map(entity-> {
                    DocumentAppendixRoleDTO appendixRoleDTO = new DocumentAppendixRoleDTO(entity);
                    appendixRoleDTO.setPosNumber(num.getAndIncrement());
                    AccessRole accessRole = accessRoleRepository.findById(entity.getAccessroleId());
                    appendixRoleDTO.setAccessRole(new SelectDisplayData<>(accessRole.accessRoleId,accessRole.getAccessRoleName()));
                    CapClass appendixType = сapClassRepository.findById(entity.getAppendixtypeId());
                    appendixRoleDTO.setAppendixType(new SelectDisplayData<>(appendixType.capClassId,appendixType.getCapClassName()));
                    return appendixRoleDTO;
                })
                .collect(Collectors.toList());
        dto.setAppendixRoles(new MemoryDataSet<DocumentAppendixRoleDTO>(list));
    }

    @Operation(summary = "Сохранение записи",
            description = "Сохранение одного объекта \"Тип документа\" в таблицу. Объекты с пустым идентификатором добавляются, " +
                    "остальные изменяются")
    @CheckPermission
    @RequestMapping(value = "document/save", method = RequestMethod.POST)
    @Audit(kinds={AuditKind.CALL_FOR_SAVE_INSERT,AuditKind.CALL_FOR_SAVE_UPDATE})

    // устанавливается менеджер БЕЗ марктровки rollbackOnly из-за алгоритма обновления
    // позиций
    @Transactional(transactionManager = "txNoRollbackFlagManager")

    public DocumentView save(@RequestBody DocumentDTO documentDTO) {
        Document entity = documentDTO.toEntity();
        if(entity.getDocumentId()==null) {
            throw new RuntimeException("Функция вставки не поддерживается");
        } else {
            documentService.saveUniqueTypeForDocument(entity.getDocumentId(),documentDTO.getUniqueTypeId());
            // проводим изменения в статусах
            updateStatusModel(documentDTO);
            // проводим изменения в правах на ЭМ
            updateAppendixRole(documentDTO);
        }
        // выбираем представление для одной записи
        return documentService.getOne(entity.getDocumentId());

    }

    private void updateStatusModel(DocumentDTO documentDTO) {
        documentDTO.getTransits().forEachDelta(r->{
            DocumentTransit transitEntity = r.getRecord().toEntity();
            transitEntity.setDocumentId(documentDTO.getDocumentId());
            switch(r.getStatus()) {
                case INSERTED:
                    Integer safeDocumentTransitId = transitEntity.getDocumentTransitId();
                    transitEntity.setDocumentTransitId(null);
                    documentTransitRepository.insert(transitEntity);

                    // во всех записях где используется вставляемая запись заменяем ID
                    final Integer insertedNumber = r.getRecord().getPositionNumber();
                    documentDTO.getTransits()
                            .withNumber(insertedNumber)
                            .forEach(delta1->{
                                delta1.getRecord().setDocumentTransitId(transitEntity.getDocumentTransitId());
                            });
                    // ссылки на Предыдущие статусы тоже надо поправить
                    documentDTO.getTransits().getDelta().stream()
                            .flatMap(t->t.getRecord().getTransitChildIds().stream())
                            .filter(dd->safeDocumentTransitId.equals(dd.getValue()))
                            .forEach(dd->dd.setValue(transitEntity.getDocumentTransitId()));

                    break;
                case UPDATED:
                    documentTransitRepository.update(transitEntity);
                    break;
                case DELETED:
                    documentTransitRepository.delete(transitEntity.getDocumentTransitId());
                    break;
            }
        });
    }

    private void updateAppendixRole(DocumentDTO documentDTO) {
        documentDTO.getAppendixRoles().forEachDelta(r->{
            DocumentAppendixRole docAppendixRoleEntity = r.getRecord().toEntity();
            docAppendixRoleEntity.setDocumentId(documentDTO.getDocumentId());
            switch(r.getStatus()) {
                case INSERTED:
                    Integer safeId = docAppendixRoleEntity.getDocumentAppendixRoleId();
                    docAppendixRoleEntity.setDocumentAppendixRoleId(null);
                    documentAppendixRoleRepository.insert(docAppendixRoleEntity);

                    // во всех записях где используется вставляемая запись заменяем ID
                    final Integer insertedNumber = r.getRecord().getPositionNumber();
                    documentDTO.getAppendixRoles()
                            .withNumber(insertedNumber)
                            .forEach(delta1->{
                                delta1.getRecord().setDocumentAppendixRoleId(docAppendixRoleEntity.getDocumentAppendixRoleId());
                            });

                    break;
                case UPDATED:
                    documentAppendixRoleRepository.update(docAppendixRoleEntity);
                    break;
                case DELETED:
                    documentAppendixRoleRepository.delete(docAppendixRoleEntity.getDocumentAppendixRoleId());
                    break;
            }
        });
    }

}
