package biz.gelicon.core.components.core.cal;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "cal"
)
@TableDescription("Календарь")
public class Cal {
    @Id
    @Column(name = "cal_id",nullable = false)
    public Integer calId;

}
