package biz.gelicon.core.components.erp.todo.documenttransit;

import biz.gelicon.core.deltads.RecordInfo;
import biz.gelicon.core.dto.SelectDisplayData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* Объект сгенерирован 18.06.2021 17:55 */
@Schema(description = "Объект \"Статус типа документа\"")
public class DocumentTransitDTO implements RecordInfo {

    @Id
    @Schema(description = "Идентификатор \"Статус типа документа\"")
    private Integer documentTransitId;

    @Schema(description = "Тип документа ИД")
    private Integer documentId;

    @Schema(description = "Порядковый номер")
    private Integer documentTransitNumber;

    @Schema(description = "Наименование")
    private String documentTransitName;

    @Schema(description = "Уровень статуса")
    private Integer documentTransitLevel;

    @Schema(description = "Обязательность")
    private Integer documentTransitRequired = 1;

    @Schema(description = "Возможность удаления")
    private Integer documentTransitCandelete = 1;

    @Schema(description = "Необходимость статуса")
    private Integer documentTransitUseadmin = 0;

    @Schema(description = "Возможность изменения")
    private Integer documentTransitCanedit = 1;

    @Schema(description = "Запрет повторения")
    private Integer documentTransitTwicecheck = 0;

    @Schema(description = "Логирование изменений")
    private Integer documentTransitFlaghistory = 0;

    @Schema(description = "Флаг первого статуса")
    private Integer documentTransitFlagone = 0;

    @Schema(description = "Блокировка ОАУ документа")
    private Integer documentTransitLocksubj = 0;

    @Schema(description = "Возможность изменения листа согласования")
    private Integer documentTransitAgreeedit = 0;

    @Schema(description = "Цвет")
    private Integer documentTransitColor = 0xffff00;

    @Schema(description = "Идентификаторы ролей для которых дан доступ")
    private List<SelectDisplayData<Integer>> accessRoleIds = new ArrayList<>();

    @Schema(description = "Идентификаторы дочерних статусов")
    private List<SelectDisplayData<Integer>> transitChildIds = new ArrayList<>();

    public DocumentTransitDTO() {}

    public DocumentTransitDTO(
            Integer documentTransitId,
            Integer documentId,
            Integer documentTransitNumber,
            String documentTransitName,
            Integer documentTransitLevel,
            Integer documentTransitRequired,
            Integer documentTransitCandelete,
            Integer documentTransitUseadmin,
            Integer documentTransitCanedit,
            Integer documentTransitTwicecheck,
            Integer documentTransitFlaghistory,
            Integer documentTransitFlagone,
            Integer documentTransitLocksubj,
            Integer documentTransitAgreeedit,
            Integer documentTransitColor) {
        this.documentTransitId = documentTransitId;
        this.documentId = documentId;
        this.documentTransitNumber = documentTransitNumber;
        this.documentTransitName = documentTransitName;
        this.documentTransitLevel = documentTransitLevel;
        this.documentTransitRequired = documentTransitRequired;
        this.documentTransitCandelete = documentTransitCandelete;
        this.documentTransitUseadmin = documentTransitUseadmin;
        this.documentTransitCanedit = documentTransitCanedit;
        this.documentTransitTwicecheck = documentTransitTwicecheck;
        this.documentTransitFlaghistory = documentTransitFlaghistory;
        this.documentTransitFlagone = documentTransitFlagone;
        this.documentTransitLocksubj = documentTransitLocksubj;
        this.documentTransitAgreeedit = documentTransitAgreeedit;
        this.documentTransitColor = documentTransitColor;
    }

    public DocumentTransitDTO(DocumentTransit entity) {
        this.documentTransitId = entity.getDocumentTransitId();
        this.documentId = entity.getDocumentId();
        this.documentTransitNumber = entity.getDocumentTransitNumber();
        this.documentTransitName = entity.getDocumentTransitName();
        this.documentTransitLevel = entity.getDocumentTransitLevel();
        this.documentTransitRequired = entity.getDocumentTransitRequired();
        this.documentTransitCandelete = entity.getDocumentTransitCandelete();
        this.documentTransitUseadmin = entity.getDocumentTransitUseadmin();
        this.documentTransitCanedit = entity.getDocumentTransitCanedit();
        this.documentTransitTwicecheck = entity.getDocumentTransitTwicecheck();
        this.documentTransitFlaghistory = entity.getDocumentTransitFlaghistory();
        this.documentTransitFlagone = entity.getDocumentTransitFlagone();
        this.documentTransitLocksubj = entity.getDocumentTransitLocksubj();
        this.documentTransitAgreeedit = entity.getDocumentTransitAgreeedit();
        this.documentTransitColor = entity.getDocumentTransitColor();
    }

    public void buildTransitChildIds(DocumentTransit entity, Function<Integer,String> mapper) {
        if(entity.getTransitChildIds() !=null) {
            entity.getTransitChildIds().stream()
                    .map(Integer::valueOf)
                    .map(id->new SelectDisplayData<>(id,mapper.apply(id)))
                    .forEach(this.transitChildIds::add);
        }
    }

    public void buildAccessRoles(DocumentTransit entity, Function<Integer,String> mapper) {
        if(entity.getAccessRoles()!=null) {
            entity.getAccessRoles().stream()
                    .map(Integer::valueOf)
                    .map(id->new SelectDisplayData<>(id,mapper.apply(id)))
                    .forEach(this.accessRoleIds::add);
        }
    }

    public DocumentTransit toEntity() {
        return toEntity(new DocumentTransit());
    }

    public DocumentTransit toEntity(DocumentTransit entity) {
        entity.setDocumentTransitId(this.documentTransitId);
        entity.setDocumentId(this.documentId);
        entity.setDocumentTransitNumber(this.documentTransitNumber);
        entity.setDocumentTransitName(this.documentTransitName);
        entity.setDocumentTransitLevel(this.documentTransitLevel);
        entity.setDocumentTransitRequired(this.documentTransitRequired);
        entity.setDocumentTransitCandelete(this.documentTransitCandelete);
        entity.setDocumentTransitUseadmin(this.documentTransitUseadmin);
        entity.setDocumentTransitCanedit(this.documentTransitCanedit);
        entity.setDocumentTransitTwicecheck(this.documentTransitTwicecheck);
        entity.setDocumentTransitFlaghistory(this.documentTransitFlaghistory);
        entity.setDocumentTransitFlagone(this.documentTransitFlagone);
        entity.setDocumentTransitLocksubj(this.documentTransitLocksubj);
        entity.setDocumentTransitAgreeedit(this.documentTransitAgreeedit);
        entity.setDocumentTransitColor(this.documentTransitColor);
        entity.setAccessRoles(this.accessRoleIds.stream()
                .map(ac->String.valueOf(ac.getValue()))
                .collect(Collectors.toList()));
        entity.setTransitChildIds(this.transitChildIds.stream()
                .map(t->String.valueOf(t.getValue()))
                .collect(Collectors.toList()));
        return entity;
    }

    public Integer getDocumentTransitId() {
        return documentTransitId;
    }

    public void setDocumentTransitId(Integer value) {
        this.documentTransitId = value;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer value) {
        this.documentId = value;
    }

    public Integer getDocumentTransitNumber() {
        return documentTransitNumber;
    }

    public void setDocumentTransitNumber(Integer value) {
        this.documentTransitNumber = value;
    }

    public String getDocumentTransitName() {
        return documentTransitName;
    }

    public void setDocumentTransitName(String value) {
        this.documentTransitName = value;
    }

    public Integer getDocumentTransitLevel() {
        return documentTransitLevel;
    }

    public void setDocumentTransitLevel(Integer value) {
        this.documentTransitLevel = value;
    }

    public Integer getDocumentTransitRequired() {
        return documentTransitRequired;
    }

    public void setDocumentTransitRequired(Integer value) {
        this.documentTransitRequired = value;
    }

    public Integer getDocumentTransitCandelete() {
        return documentTransitCandelete;
    }

    public void setDocumentTransitCandelete(Integer value) {
        this.documentTransitCandelete = value;
    }

    public Integer getDocumentTransitUseadmin() {
        return documentTransitUseadmin;
    }

    public void setDocumentTransitUseadmin(Integer value) {
        this.documentTransitUseadmin = value;
    }

    public Integer getDocumentTransitCanedit() {
        return documentTransitCanedit;
    }

    public void setDocumentTransitCanedit(Integer value) {
        this.documentTransitCanedit = value;
    }

    public Integer getDocumentTransitTwicecheck() {
        return documentTransitTwicecheck;
    }

    public void setDocumentTransitTwicecheck(Integer value) {
        this.documentTransitTwicecheck = value;
    }

    public Integer getDocumentTransitFlaghistory() {
        return documentTransitFlaghistory;
    }

    public void setDocumentTransitFlaghistory(Integer value) {
        this.documentTransitFlaghistory = value;
    }

    public Integer getDocumentTransitFlagone() {
        return documentTransitFlagone;
    }

    public void setDocumentTransitFlagone(Integer value) {
        this.documentTransitFlagone = value;
    }

    public Integer getDocumentTransitLocksubj() {
        return documentTransitLocksubj;
    }

    public void setDocumentTransitLocksubj(Integer value) {
        this.documentTransitLocksubj = value;
    }

    public Integer getDocumentTransitAgreeedit() {
        return documentTransitAgreeedit;
    }

    public void setDocumentTransitAgreeedit(Integer value) {
        this.documentTransitAgreeedit = value;
    }

    public Integer getDocumentTransitColor() {
        return documentTransitColor;
    }

    public void setDocumentTransitColor(Integer value) {
        this.documentTransitColor = value;
    }

    public List<SelectDisplayData<Integer>> getAccessRoleIds() {
        return accessRoleIds;
    }

    public void setAccessRoleIds(List<SelectDisplayData<Integer>> accessRoleIds) {
        this.accessRoleIds.clear();
        if(accessRoleIds !=null) {
            this.accessRoleIds.addAll(accessRoleIds);
        }
    }

    public List<SelectDisplayData<Integer>> getTransitChildIds() {
        return transitChildIds;
    }

    public void setTransitChildIds(List<SelectDisplayData<Integer>> transitChildIds) {
        this.transitChildIds.clear();
        if(transitChildIds!=null) {
            this.transitChildIds.addAll(transitChildIds);
        }
    }

    @JsonIgnore
    @Override
    public Integer getPositionNumber() {
        return documentTransitNumber;
    }
}