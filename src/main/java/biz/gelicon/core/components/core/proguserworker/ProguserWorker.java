package biz.gelicon.core.components.core.proguserworker;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "proguserworker"
)
@TableDescription("Связь пользователя с сотрудником")
public class ProguserWorker {
    @Id
    @Column(name = "proguser_id",nullable = false)
    public Integer proguserId;

}
