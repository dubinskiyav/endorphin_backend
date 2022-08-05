package biz.gelicon.core.components.core.weeknumber;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "weeknumber"
)
@TableDescription("Номер недели")
public class Weeknumber {
    @Id
    @Column(name = "weeknumber_id",nullable = false)
    public Integer weeknumberId;

}
