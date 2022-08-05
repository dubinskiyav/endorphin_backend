package biz.gelicon.core.components.core.subcluster;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "subcluster"
)
@TableDescription("Суб Кластер аналитики")
public class Subcluster {
    @Id
    @Column(name = "subject_id",nullable = false)
    public Integer subjectId;

}
