package biz.gelicon.core.components.core.resourcetrantype;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "resourcetrantype"
)
@TableDescription("Тип транслятора")
public class Resourcetrantype {
    @Id
    @Column(name = "resourcetrantype_id",nullable = false)
    public Integer resourcetrantypeId;

}
