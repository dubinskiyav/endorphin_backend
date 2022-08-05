package biz.gelicon.core.components.core.monthcal;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "monthcal"
)
@TableDescription("Месяц календаря")
public class Monthcal {
    @Id
    @Column(name = "monthcal_id",nullable = false)
    public Integer monthcalId;

}
