package biz.gelicon.core.components.core.mycompany;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "mycompany"
)
@TableDescription("Моя компания")
public class Mycompany {
    @Id
    @Column(name = "company_id",nullable = false)
    public Integer companyId;

}
