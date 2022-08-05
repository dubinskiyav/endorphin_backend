package biz.gelicon.core.components.core.subjecttype;

import biz.gelicon.core.annotations.TableDescription;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/* Сущность сгенерирована 20.04.2021 11:02 */
@Table(
    name = "subjecttype",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"subjecttype_name"})
    }
)
@TableDescription("Тип аналитики")
public class SubjectType {
    public static final int FOLDER_SUBJECTTYPE_ID = 1;
    public static final int ITEM_SUBJECTTYPE_ID = 2;

    @Id
    @Column(name = "subjecttype_id",nullable = false)
    public Integer subjectTypeId;

    @Column(name = "subjecttype_name", nullable = false)
    @Size(max = 30, message = "Поле \"Наименование\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"subjectTypeName\" не может быть пустым")
    private String subjectTypeName;

    @Column(name = "subjecttype_sortcode", nullable = true)
    @Size(max = 10, message = "Поле \"Код сортировки\" должно содержать не более {max} символов")
    private String subjectTypeSortcode;

    public Integer getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(Integer value) {
        this.subjectTypeId = value;
    }

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String value) {
        this.subjectTypeName = value;
    }

    public String getSubjectTypeSortcode() {
        return subjectTypeSortcode;
    }

    public void setSubjectTypeSortcode(String value) {
        this.subjectTypeSortcode = value;
    }


    public SubjectType() {}

    public SubjectType(
            Integer subjectTypeId,
            String subjectTypeName,
            String subjectTypeSortcode) {
        this.subjectTypeId = subjectTypeId;
        this.subjectTypeName = subjectTypeName;
        this.subjectTypeSortcode = subjectTypeSortcode;
    }
}

