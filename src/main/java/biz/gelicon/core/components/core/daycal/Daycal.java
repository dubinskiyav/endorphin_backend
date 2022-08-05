package biz.gelicon.core.components.core.daycal;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "daycal"
)
@TableDescription("День календаря")
public class Daycal {
    @Id
    @Column(name = "daycal_id",nullable = false)
    public Integer daycalId;

}
