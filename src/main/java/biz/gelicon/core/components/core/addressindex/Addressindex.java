package biz.gelicon.core.components.core.addressindex;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "addressindex"
)
@TableDescription("Индекс адреса")
public class Addressindex {
    @Id
    @Column(name = "district_id",nullable = false)
    public Integer addressId;

}
