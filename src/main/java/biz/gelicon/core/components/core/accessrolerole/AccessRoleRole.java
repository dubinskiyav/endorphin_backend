package biz.gelicon.core.components.core.accessrolerole;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.accessrole.AccessRole;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "accessrolerole")
@TableDescription("Роль роли")
public class AccessRoleRole {

    @Id
    @Column(name = "accessrolerole_id",nullable = false)
    public Integer accessRoleRoleId;

    @OneToMany(targetEntity = AccessRole.class, cascade = {CascadeType.REMOVE})
    @Column(name = "accessrole_id_parent",nullable = false)
    @NotNull(message = "Поле 'Роль - родитель' не может быть пустым")
    public Integer accessRoleIdParent;

    @OneToMany(targetEntity = AccessRole.class, cascade = {CascadeType.REMOVE})
    @NotNull(message = "Поле 'Роль - ребенок' не может быть пустым")
    @Column(name = "accessrole_id_child",nullable = false)
    private Integer accessRoleIdChild;

    public Integer getAccessRoleRoleId() {
        return accessRoleRoleId;
    }

    public AccessRoleRole() {
    }

    public void setAccessRoleRoleId(Integer accessRoleRoleId) {
        this.accessRoleRoleId = accessRoleRoleId;
    }

    public void setAccessRoleIdParent(Integer accessRoleIdParent) {
        this.accessRoleIdParent = accessRoleIdParent;
    }

    public void setAccessRoleIdChild(Integer accessRoleIdChild) {
        this.accessRoleIdChild = accessRoleIdChild;
    }

    public Integer getAccessRoleIdParent() {
        return accessRoleIdParent;
    }

    public Integer getAccessRoleIdChild() {
        return accessRoleIdChild;
    }

    public AccessRoleRole(Integer accessRoleRoleId, Integer accessRoleIdParent,
            Integer accessRoleIdChild) {
        this.accessRoleRoleId = accessRoleRoleId;
        this.accessRoleIdParent = accessRoleIdParent;
        this.accessRoleIdChild = accessRoleIdChild;
    }
}

