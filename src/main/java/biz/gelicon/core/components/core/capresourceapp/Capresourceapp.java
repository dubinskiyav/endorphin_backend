package biz.gelicon.core.components.core.capresourceapp;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "capresourceapp"
)
@TableDescription("Приложение ресурса")
public class Capresourceapp {
    @Id
    @Column(name = "capresourceapp_id",nullable = false)
    public Integer capresourceappId;

}
