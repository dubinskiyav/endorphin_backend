package biz.gelicon.core.components.core.dayofweek;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "dayofweek"
)
@TableDescription("День недели календаря")
public class Dayofweek {
    @Id
    @Column(name = "dayofweek_id",nullable = false)
    public Integer dayofweekId;

}
