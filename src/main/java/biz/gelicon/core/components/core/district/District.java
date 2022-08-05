package biz.gelicon.core.components.core.district;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "district"
)
@TableDescription("Район")
public class District {
    @Id
    @Column(name = "district_id",nullable = false)
    public Integer districtId;

}
