package biz.gelicon.core.components.core.capresourcerole;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table(
        name = "capresourcerole"
)
@TableDescription("Запрет доступа к ресурсу системы")
public class Capresourcerole {

    @Id
    @Column(name = "capresourcerole_id", nullable = false)
    public Integer capresourceroleId;
}
