package biz.gelicon.core.components.erp.todo.documenttransit;

import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentWithStatusDTO;
import biz.gelicon.core.components.core.document.DocumentRepository;
import biz.gelicon.core.components.core.document.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@RestController
@Tag(name = "Возможные статусы для типа документа",
        description = "Контроллер для объектов 'Возможные статусы для типа документа' ")
@RequestMapping(value = "/v" + Config.CURRENT_VERSION + "/apps/system",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class DocumentTransitController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentTransitController.class);

    @Autowired
    private DocumentTransitService documentTransitService;
    @Autowired
    private DocumentTransitRepository documentTransitRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentService documentService;

    @Operation(summary = "Список возможных статусов для выбранных типов документов",
            description = "Возвращает список типов документов со списком возможных статутов"
                    + " для списка типов документов, перечисленных через запятую"
                    + " или для всех типов, если список пустой")
    @CheckPermission
    @RequestMapping(value = "documenttransit/getlist", method = RequestMethod.POST)
    public List<DocumentWithStatusDTO> getList(@RequestBody int[] ids) {
        if (ids == null || ids.length < 1) {
            // todo сделать метод возвращения всех document_id
            ids = documentTransitRepository.findQueryForMap(""
                    + " SELECT document_id, "
                    + "        document_name "
                    + " FROM   document "
                    + " ORDER BY document_name")
                    .stream()
                    .mapToInt(o -> (Integer) o.get("document_id")).toArray();
        }
        // Возвращать будем список DocumentWithStatusDTO
        List<DocumentWithStatusDTO> result = new ArrayList<>();
        // Цикл по документам
        for (int documentId : ids) {
            // Получим список возможных статусов для типа документа
            boolean withSelect = false;
            if (!withSelect) { // Без запросов здесь
                List<DocumentTransit> documentTransitList =
                        documentTransitRepository.findByDocument(documentId);
                List<DocumentWithStatusDTO.DocumentStatusShortDTO> documentStatusShortDTOList =
                        documentTransitList.stream()
                                .map(s -> new DocumentWithStatusDTO.DocumentStatusShortDTO(
                                                s.getDocumentTransitId(),
                                                s.getDocumentTransitNumber(),
                                                s.getDocumentTransitColor(),
                                                s.getDocumentTransitName(),
                                                s.getTransitChildIds().stream().mapToInt(Integer::parseInt)
                                                        .toArray()
                                        )
                                )
                                .collect(Collectors.toList());
                DocumentWithStatusDTO documentWithStatusDTO =
                        new DocumentWithStatusDTO(documentId, documentStatusShortDTOList);
                result.add(documentWithStatusDTO);
            } else { // с запросами здесь
                DocumentWithStatusDTO documentWithStatusDTO =
                        new DocumentWithStatusDTO(documentId, null);
                documentTransitRepository.findQueryForMap(""
                                + " SELECT documenttransit_id, "
                                + "        documenttransit_number, "
                                + "        documenttransit_color, "
                                + "        documenttransit_name "
                                + " FROM   documenttransit "
                                + " WHERE  document_id = " + documentId + " "
                                + " ORDER BY documenttransit_number",
                        null)
                        .forEach(o -> {
                            // получим дочерние статусы
                            // перепутаны дети с родителями?
                            int[] transitChildIds = documentTransitRepository.findQueryForMap(""
                                            + " SELECT documenttransitparent_id "
                                            + " FROM   documenttransitlink "
                                            + " WHERE  documenttransitchild_id = " + o
                                            .get("documenttransit_id"),
                                    null)
                                    .stream()
                                    .mapToInt(d -> (Integer) d.get("documenttransitparent_id"))
                                    .toArray();
                            DocumentWithStatusDTO.DocumentStatusShortDTO documentStatusShortDTO =
                                    new DocumentWithStatusDTO.DocumentStatusShortDTO(
                                            (Integer) o.get("documenttransit_id"),
                                            (Integer) o.get("documenttransit_number"),
                                            (Integer) o.get("documenttransit_color"),
                                            (String) o.get("documenttransit_name"),
                                            transitChildIds
                                    );
                            documentWithStatusDTO.getDocumentStatusShortDTOList()
                                    .add(documentStatusShortDTO);
                        });
                result.add(documentWithStatusDTO);
            }
        }
        return result;
    }

}
