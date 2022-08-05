package biz.gelicon.core.components.core.progusersubject;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "progusersubject"
)
@TableDescription("Рользователь - объект аналитики")
public class Progusersubject {
    @Id
    @Column(name = "proguser_id",nullable = false)
    public Integer proguserId;
}
