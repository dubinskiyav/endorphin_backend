package biz.gelicon.core.components.core.companycode;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "companycode"
)
@TableDescription(" Код контрагента")
public class Companycode {
    @Id
    @Column(name = "companycode_id",nullable = false)
    public Integer companycodeId;

}
