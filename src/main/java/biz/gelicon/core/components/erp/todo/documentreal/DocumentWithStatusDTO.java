package biz.gelicon.core.components.erp.todo.documentreal;

import java.util.ArrayList;
import java.util.List;

/**
 * Для функции считывания всех статусов
 */
public class DocumentWithStatusDTO {
    // Вспомогательный класс
    public static class DocumentStatusShortDTO {
        Integer documentTransitId;
        Integer documentTransitNumber;
        Integer documentTransitColor;
        String documentTransitName;
        int[] transitChildIds;

        public DocumentStatusShortDTO() {
        }

        public DocumentStatusShortDTO(Integer documentTransitId, Integer documentTransitNumber,
                Integer documentTransitColor, String documentTransitName, int[] transitChildIds) {
            this.documentTransitId = documentTransitId;
            this.documentTransitNumber = documentTransitNumber;
            this.documentTransitColor = documentTransitColor;
            this.documentTransitName = documentTransitName;
            this.transitChildIds = transitChildIds;
        }
        public Integer getDocumentTransitId() {
            return documentTransitId;
        }

        public Integer getDocumentTransitNumber() {
            return documentTransitNumber;
        }

        public Integer getDocumentTransitColor() {
            return documentTransitColor;
        }

        public String getDocumentTransitName() {
            return documentTransitName;
        }

        public int[] getTransitChildIds() {
            return transitChildIds;
        }

        public void setDocumentTransitId(Integer documentTransitId) {
            this.documentTransitId = documentTransitId;
        }

        public void setDocumentTransitNumber(Integer documentTransitNumber) {
            this.documentTransitNumber = documentTransitNumber;
        }

        public void setDocumentTransitColor(Integer documentTransitColor) {
            this.documentTransitColor = documentTransitColor;
        }

        public void setDocumentTransitName(String documentTransitName) {
            this.documentTransitName = documentTransitName;
        }

        public void setTransitChildIds(int[] transitChildIds) {
            this.transitChildIds = transitChildIds;
        }

    }

    // Тип документа
    private Integer documentId;

    // Список возможных статусов
    private List<DocumentStatusShortDTO> documentStatusShortDTOList;

    public DocumentWithStatusDTO() {
        // Создадим, чтобы сразу добавлять, не проверяя
        documentStatusShortDTOList = new ArrayList<>();
    }

    public DocumentWithStatusDTO(Integer documentId,
            List<DocumentStatusShortDTO> documentStatusShortDTOList) {
        this.documentId = documentId;
        this.documentStatusShortDTOList = documentStatusShortDTOList;
        if (this.documentStatusShortDTOList == null) {
            // Создадим, чтобы сразу добавлять, не проверяя
            this.documentStatusShortDTOList = new ArrayList<>();
        }
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public List<DocumentStatusShortDTO> getDocumentStatusShortDTOList() {
        return documentStatusShortDTOList;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public void setDocumentStatusShortDTOList(
            List<DocumentStatusShortDTO> documentStatusShortDTOList) {
        this.documentStatusShortDTOList = documentStatusShortDTOList;
    }
}
