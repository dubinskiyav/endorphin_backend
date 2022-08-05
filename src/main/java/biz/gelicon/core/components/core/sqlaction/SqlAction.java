package biz.gelicon.core.components.core.sqlaction;

import biz.gelicon.core.annotations.TableDescription;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table(
        name = "sqlaction",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"sqlaction_sql"})
        }
)
@TableDescription("Действие")
public class SqlAction {

    public static final int REED_ACTION = 1;
    public static final int INSERT_ACTION = 2;
    public static final int UPDATE_ACTION = 3;
    public static final int DELETE_ACTION = 4;

    @Id
    @Column(name = "sqlaction_id", nullable = false)
    public Integer sqlactionId;

    @Column(name = "sqlaction_sql", nullable = false)
    @Size(max = 10, message = "Поле \"Оператор SQL\" должно содержать не более {max} символов")
    @NotBlank(message = "Поле \"Оператор SQL\" не может быть пустым")
    private String sqlactionSql;

    @Column(name = "sqlaction_note", nullable = true)
    @Size(max = 20, message = "Поле \"Примечание\" должно содержать не более {max} символов")
    private String sqlactionNote;

    public SqlAction() {
    }

    public SqlAction(Integer sqlactionId, String sqlactionSql, String sqlactionNote) {
        this.sqlactionId = sqlactionId;
        this.sqlactionSql = sqlactionSql;
        this.sqlactionNote = sqlactionNote;
    }

    public void setSqlactionId(Integer sqlactionId) {
        this.sqlactionId = sqlactionId;
    }

    public void setSqlactionSql(String sqlactionSql) {
        this.sqlactionSql = sqlactionSql;
    }

    public void setSqlactionNote(String sqlactionNote) {
        this.sqlactionNote = sqlactionNote;
    }

    public Integer getSqlactionId() {
        return sqlactionId;
    }

    public String getSqlactionSql() {
        return sqlactionSql;
    }

    public String getSqlactionNote() {
        return sqlactionNote;
    }
}
