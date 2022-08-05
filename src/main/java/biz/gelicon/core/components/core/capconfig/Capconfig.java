package biz.gelicon.core.components.core.capconfig;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "capconfig"
)
@TableDescription("Конфигурация")
public class Capconfig {
    @Id
    @Column(name = "capconfig_id",nullable = false)
    public Integer capconfigId;

}
