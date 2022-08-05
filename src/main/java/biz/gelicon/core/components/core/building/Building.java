package biz.gelicon.core.components.core.building;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "building"
)
@TableDescription("Строение")
public class Building {
    @Id
    @Column(name = "building_id",nullable = false)
    public Integer buildingId;

}
