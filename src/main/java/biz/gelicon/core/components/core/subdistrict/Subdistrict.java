package biz.gelicon.core.components.core.subdistrict;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "subdistrict"
)
@TableDescription("Микрорайон населенного пункта")
public class Subdistrict {
    @Id
    @Column(name = "subdistrict_id",nullable = false)
    public Integer subdistrictId;

}
