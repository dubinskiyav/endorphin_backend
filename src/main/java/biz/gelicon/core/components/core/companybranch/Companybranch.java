package biz.gelicon.core.components.core.companybranch;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "companybranch"
)
@TableDescription("Филиал или представительство контрагента")
public class Companybranch {
    @Id
    @Column(name = "company_id",nullable = false)
    public Integer companyId;

}
