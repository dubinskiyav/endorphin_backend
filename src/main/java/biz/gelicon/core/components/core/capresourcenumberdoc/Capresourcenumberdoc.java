package biz.gelicon.core.components.core.capresourcenumberdoc;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "capresourcenumberdoc"
)
@TableDescription("Документ ресурса")
public class Capresourcenumberdoc {
    @Id
    @Column(name = "capresourcenumberdoc_id",nullable = false)
    public Integer capresourcenumberdocId;

}
