package biz.gelicon.core.components.core.applicationrole;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "applicationrole"
)
@TableDescription("Роль в модуле")
public class Applicationrole {
    @Id
    @Column(name = "applicationrole_id",nullable = false)
    public Integer applicationroleId;

}
