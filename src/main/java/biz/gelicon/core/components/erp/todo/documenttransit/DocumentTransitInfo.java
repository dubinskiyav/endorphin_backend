package biz.gelicon.core.components.erp.todo.documenttransit;

import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransit;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class DocumentTransitInfo {

    @Schema(description = "Идентификатор \"Статус типа документа\"")
    @Column(name="documenttransit_id")
    private Integer documentTransitId;

    @Schema(description = "Порядковый номер")
    @Column(name="documenttransit_number")
    private Integer documentTransitNumber;

    @Schema(description = "Наименование")
    @Column(name="documenttransit_name")
    private String documentTransitName;

    @Schema(description = "Уровень статуса")
    @Column(name="documenttransit_level")
    private Integer documentTransitLevel;

    @Schema(description = "Цвет")
    @Column(name="documenttransit_color")
    private Integer documentTransitColor;

    @Schema(description = "Идентификаторы дочерних статусов")
    private List<Integer> transitChildIds = new ArrayList<>();

    @Schema(description = "Запрет повторения")
    private Integer documentTransitTwicecheck;

    public DocumentTransitInfo() {
    }

    public DocumentTransitInfo(DocumentTransit entity) {
        this.documentTransitId = entity.getDocumentTransitId();
        this.documentTransitNumber = entity.getDocumentTransitNumber();
        this.documentTransitName = entity.getDocumentTransitName();
        this.documentTransitLevel = entity.getDocumentTransitLevel();
        this.documentTransitColor = entity.getDocumentTransitColor();
        this.documentTransitTwicecheck = entity.getDocumentTransitTwicecheck();
        entity.getTransitChildIds().stream()
                .map(Integer::valueOf)
                .forEach(this.transitChildIds::add);
    }

    public Integer getDocumentTransitId() {
        return documentTransitId;
    }

    public void setDocumentTransitId(Integer documentTransitId) {
        this.documentTransitId = documentTransitId;
    }

    public Integer getDocumentTransitNumber() {
        return documentTransitNumber;
    }

    public void setDocumentTransitNumber(Integer documentTransitNumber) {
        this.documentTransitNumber = documentTransitNumber;
    }

    public String getDocumentTransitName() {
        return documentTransitName;
    }

    public void setDocumentTransitName(String documentTransitName) {
        this.documentTransitName = documentTransitName;
    }

    public Integer getDocumentTransitLevel() {
        return documentTransitLevel;
    }

    public void setDocumentTransitLevel(Integer documentTransitLevel) {
        this.documentTransitLevel = documentTransitLevel;
    }

    public Integer getDocumentTransitColor() {
        return documentTransitColor;
    }

    public void setDocumentTransitColor(Integer documentTransitColor) {
        this.documentTransitColor = documentTransitColor;
    }

    public List<Integer> getTransitChildIds() {
        return transitChildIds;
    }

    public void setTransitChildIds(List<Integer> transitChildIds) {
        this.transitChildIds = transitChildIds;
    }

    public Integer getDocumentTransitTwicecheck() {
        return documentTransitTwicecheck;
    }

    public void setDocumentTransitTwicecheck(Integer documentTransitTwicecheck) {
        this.documentTransitTwicecheck = documentTransitTwicecheck;
    }
}
