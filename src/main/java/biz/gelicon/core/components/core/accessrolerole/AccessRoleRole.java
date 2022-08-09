package biz.gelicon.core.components.core.accessrolerole;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.capcodetype.CapCodeType;
import biz.gelicon.core.security.ACL;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "accessrolerole")
@TableDescription("Роль роли")
public class AccessRoleRole {

    @Id
    @Column(name = "accessrolerole_id",nullable = false)
    public Integer accessRoleRoleId;

    @ManyToMany(targetEntity = AccessRole.class, cascade = {CascadeType.REMOVE})
    @Column(name = "accessrole_id_parent",nullable = false)
    @NotNull(message = "Поле 'Роль - родитель' не может быть пустым")
    private Integer accessroleIdParent;

    @ManyToMany(targetEntity = AccessRole.class, cascade = {CascadeType.REMOVE})
    @NotNull(message = "Поле 'Роль - ребенок' не может быть пустым")
    @Column(name = "accessrole_id_child",nullable = false)
    private Integer accessroleIdChild;

    public Integer getAccessRoleRoleId() {
        return accessRoleRoleId;
    }

    public void setAccessRoleRoleId(Integer accessRoleRoleId) {
        this.accessRoleRoleId = accessRoleRoleId;
    }

    public void setAccessroleIdParent(Integer accessroleIdParent) {
        this.accessroleIdParent = accessroleIdParent;
    }

    public void setAccessroleIdChild(Integer accessroleIdChild) {
        this.accessroleIdChild = accessroleIdChild;
    }

    public Integer getAccessroleIdParent() {
        return accessroleIdParent;
    }

    public Integer getAccessroleIdChild() {
        return accessroleIdChild;
    }

    public AccessRoleRole(Integer accessRoleRoleId, Integer accessroleIdParent,
            Integer accessroleIdChild) {
        this.accessRoleRoleId = accessRoleRoleId;
        this.accessroleIdParent = accessroleIdParent;
        this.accessroleIdChild = accessroleIdChild;
    }
}

