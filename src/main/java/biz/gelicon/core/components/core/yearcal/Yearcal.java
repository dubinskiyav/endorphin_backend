package biz.gelicon.core.components.core.yearcal;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "yearcal"
)
@TableDescription("Год календаря")
public class Yearcal {
    @Id
    @Column(name = "yearcal_id",nullable = false)
    public Integer yearcalId;

}
