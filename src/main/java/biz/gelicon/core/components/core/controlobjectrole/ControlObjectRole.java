package biz.gelicon.core.components.core.controlobjectrole;

import biz.gelicon.core.annotations.TableDescription;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.controlobject.ControlObject;
import biz.gelicon.core.components.core.sqlaction.SqlAction;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "controlobjectrole")
@TableDescription("Контролируемый объект роли")
public class ControlObjectRole {

    @Id
    @Column(name = "controlobjectrole_id",nullable = false)
    public Integer controlobjectroleId;

    @ManyToOne(targetEntity = ControlObject.class)
    @Column(name = "controlobject_id", nullable = false)
    private Integer controlobjectId;

    @ManyToOne(targetEntity = AccessRole.class)
    @Column(name = "accessrole_id", nullable = false)
    private Integer accessroleId;

    @ManyToOne(targetEntity = SqlAction.class)
    @Column(name = "sqlaction_id", nullable = false)
    private Integer sqlactionId;

    public void setControlobjectroleId(Integer controlobjectroleId) {
        this.controlobjectroleId = controlobjectroleId;
    }

    public void setControlobjectId(Integer controlobjectId) {
        this.controlobjectId = controlobjectId;
    }

    public void setAccessroleId(Integer accessroleId) {
        this.accessroleId = accessroleId;
    }

    public void setSqlactionId(Integer sqlactionId) {
        this.sqlactionId = sqlactionId;
    }

    public Integer getControlobjectroleId() {
        return controlobjectroleId;
    }

    public Integer getControlobjectId() {
        return controlobjectId;
    }

    public Integer getAccessroleId() {
        return accessroleId;
    }

    public Integer getSqlactionId() {
        return sqlactionId;
    }

    public ControlObjectRole() {}

    public ControlObjectRole(Integer controlobjectroleId, Integer controlobjectId,
            Integer accessroleId,
            Integer sqlactionId) {
        this.controlobjectroleId = controlobjectroleId;
        this.controlobjectId = controlobjectId;
        this.accessroleId = accessroleId;
        this.sqlactionId = sqlactionId;
    }


}

