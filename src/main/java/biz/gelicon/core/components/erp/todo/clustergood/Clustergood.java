package biz.gelicon.core.components.erp.todo.clustergood;

import biz.gelicon.core.annotations.TableDescription;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/* Сущность сгенерирована 10.06.2021 19:52 */
@Table(name = "clustergood")
@TableDescription("Уровень товаров")
public class Clustergood {

    @Id
    @Column(name = "clustergood_id",nullable = false)
    public Integer clustergoodId;

    public Integer getClustergoodId() {
        return clustergoodId;
    }

    public void setClustergoodId(Integer value) {
        this.clustergoodId = value;
    }


    public Clustergood() {}

    public Clustergood(
            Integer clustergoodId) {
        this.clustergoodId = clustergoodId;
    }
}

