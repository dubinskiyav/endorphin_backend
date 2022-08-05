package biz.gelicon.core.components.core.clusterr;

import biz.gelicon.core.annotations.TableDescription;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/* Сущность сгенерирована 20.04.2021 11:02 */
@Table(name = "clusterr")
@TableDescription("Уровень аналитики")
public class Clusterr {

    @Id
    @Column(name = "cluster_id",nullable = false)
    public Integer clusterrClusterId;

    public Integer getClusterrClusterId() {
        return clusterrClusterId;
    }

    public void setClusterrClusterId(Integer value) {
        this.clusterrClusterId = value;
    }


    public Clusterr() {}

    public Clusterr(
            Integer clusterrClusterId) {
        this.clusterrClusterId = clusterrClusterId;
    }
}

