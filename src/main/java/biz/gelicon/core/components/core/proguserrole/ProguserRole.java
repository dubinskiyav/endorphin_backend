package biz.gelicon.core.components.core.proguserrole;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.proguser.Proguser;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "proguserrole")
@TableDescription("Роль пользователя")
public class ProguserRole {

    @Id
    @Column(name = "proguserrole_id",nullable = false)
    public Integer proguserroleId;

    @ManyToOne(targetEntity = Proguser.class)
    @Column(name = "proguser_id", nullable = false)
    private Integer proguserId;

    @ManyToOne(targetEntity = AccessRole.class)
    @Column(name = "accessrole_id", nullable = false)
    private Integer accessroleId;

    public void setProguserroleId(Integer proguserroleId) {
        this.proguserroleId = proguserroleId;
    }

    public void setProguserId(Integer proguserId) {
        this.proguserId = proguserId;
    }

    public void setAccessroleId(Integer accessroleId) {
        this.accessroleId = accessroleId;
    }

    public Integer getProguserroleId() {
        return proguserroleId;
    }

    public Integer getProguserId() {
        return proguserId;
    }

    public Integer getAccessroleId() {
        return accessroleId;
    }

    public ProguserRole() {}

    public ProguserRole(Integer proguserroleId, Integer proguserId, Integer accessroleId) {
        this.proguserroleId = proguserroleId;
        this.proguserId = proguserId;
        this.accessroleId = accessroleId;
    }

}

