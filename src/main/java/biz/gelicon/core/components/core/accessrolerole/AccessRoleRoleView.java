package biz.gelicon.core.components.core.accessrolerole;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;

@Schema(description = "Представление объекта 'Роль роли'")
public class AccessRoleRoleView {

    @Schema(description = "Идентификатор 'Роль роли'")
    @Column(name="accessrolerole_id")
    public Integer accessRoleRoleId;

    @Schema(description = "Идентификатор родителя")
    @Column(name="accessrole_id_parent")
    public Integer accessRoleIdParent;

    @Schema(description = "Наименование родителя")
    @Column(name="accessrole_name_parent")
    private String accessRoleNameParent;

    @Schema(description = "Описание родителя")
    @Column(name="accessrole_note_parent")
    private String accessRoleNoteParent;

    @Schema(description = "Видимость родителя")
    @Column(name="accessrole_visible_parent")
    private Integer accessRoleVisibleParent;

    @Schema(description = "Идентификатор ребенка")
    @Column(name="accessrole_id_child")
    public Integer accessRoleIdChild;

    @Schema(description = "Наименование ребенка")
    @Column(name="accessrole_name_child")
    private String accessRoleNameChild;

    @Schema(description = "Описание ребенка")
    @Column(name="accessrole_note_child")
    private String accessRoleNoteChild;

    @Schema(description = "Видимость ребенка")
    @Column(name="accessrole_visible_child")
    private Integer accessRoleVisibleChild;

    public AccessRoleRoleView() {}

    public AccessRoleRoleView(Integer accessRoleRoleId, Integer accessRoleIdParent,
            String accessRoleNameParent, String accessRoleNoteParent,
            Integer accessRoleVisibleParent,
            Integer accessRoleIdChild, String accessRoleNameChild, String accessRoleNoteChild,
            Integer accessRoleVisibleChild) {
        this.accessRoleRoleId = accessRoleRoleId;
        this.accessRoleIdParent = accessRoleIdParent;
        this.accessRoleNameParent = accessRoleNameParent;
        this.accessRoleNoteParent = accessRoleNoteParent;
        this.accessRoleVisibleParent = accessRoleVisibleParent;
        this.accessRoleIdChild = accessRoleIdChild;
        this.accessRoleNameChild = accessRoleNameChild;
        this.accessRoleNoteChild = accessRoleNoteChild;
        this.accessRoleVisibleChild = accessRoleVisibleChild;
    }

    public void setAccessRoleRoleId(Integer accessRoleRoleId) {
        this.accessRoleRoleId = accessRoleRoleId;
    }

    public void setAccessRoleIdParent(Integer accessRoleIdParent) {
        this.accessRoleIdParent = accessRoleIdParent;
    }

    public void setAccessRoleNameParent(String accessRoleNameParent) {
        this.accessRoleNameParent = accessRoleNameParent;
    }

    public void setAccessRoleNoteParent(String accessRoleNoteParent) {
        this.accessRoleNoteParent = accessRoleNoteParent;
    }

    public void setAccessRoleVisibleParent(Integer accessRoleVisibleParent) {
        this.accessRoleVisibleParent = accessRoleVisibleParent;
    }

    public void setAccessRoleIdChild(Integer accessRoleIdChild) {
        this.accessRoleIdChild = accessRoleIdChild;
    }

    public void setAccessRoleNameChild(String accessRoleNameChild) {
        this.accessRoleNameChild = accessRoleNameChild;
    }

    public void setAccessRoleNoteChild(String accessRoleNoteChild) {
        this.accessRoleNoteChild = accessRoleNoteChild;
    }

    public void setAccessRoleVisibleChild(Integer accessRoleVisibleChild) {
        this.accessRoleVisibleChild = accessRoleVisibleChild;
    }

    public Integer getAccessRoleRoleId() {
        return accessRoleRoleId;
    }

    public Integer getAccessRoleIdParent() {
        return accessRoleIdParent;
    }

    public String getAccessRoleNameParent() {
        return accessRoleNameParent;
    }

    public String getAccessRoleNoteParent() {
        return accessRoleNoteParent;
    }

    public Integer getAccessRoleVisibleParent() {
        return accessRoleVisibleParent;
    }

    public Integer getAccessRoleIdChild() {
        return accessRoleIdChild;
    }

    public String getAccessRoleNameChild() {
        return accessRoleNameChild;
    }

    public String getAccessRoleNoteChild() {
        return accessRoleNoteChild;
    }

    public Integer getAccessRoleVisibleChild() {
        return accessRoleVisibleChild;
    }
}

