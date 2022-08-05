package biz.gelicon.core.components.core.subjectattributevalue;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "subjectattributevalue"
)
@TableDescription("Атрибут ОАУ со значением")
public class Subjectattributevalue {
    @Id
    @Column(name = "subjectattributevalue_id",nullable = false)
    public Integer subjectattributevalueId;

}
