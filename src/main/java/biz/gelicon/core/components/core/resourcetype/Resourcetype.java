package biz.gelicon.core.components.core.resourcetype;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "resourcetype"
)
@TableDescription("Тип ресурса")
public class Resourcetype {
    @Id
    @Column(name = "resourcetype_id",nullable = false)
    public Integer resourcetypeId;

}
