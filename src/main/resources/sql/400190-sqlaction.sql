/* Действие */
CREATE TABLE sqlaction (
	sqlaction_id	INTEGER NOT NULL,
	sqlaction_sql	VARCHAR(10) NOT NULL,
	sqlaction_note	VARCHAR(20),
	CONSTRAINT sqlaction_pk PRIMARY KEY (sqlaction_id),
	CONSTRAINT sqlaction_ak1 UNIQUE (sqlaction_sql)
);
COMMENT ON TABLE sqlaction IS 'Действие';
COMMENT ON COLUMN sqlaction.sqlaction_id IS 'Действие ИД';
COMMENT ON COLUMN sqlaction.sqlaction_sql IS 'Оператор SQL';
COMMENT ON COLUMN sqlaction.sqlaction_note IS 'Примечание';
CREATE SEQUENCE sqlaction_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE sqlaction_id_gen OWNED BY sqlaction.sqlaction_id;

INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (1,'SELECT','Просмотр');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (2,'INSERT','Добавление');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (3,'UPDATE','Изменение');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (4,'DELETE','Удаление');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (5,'SEARCH','Поиск');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (6,'DEBUG','Отладка');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (7,'ABORT','Прерывание');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (8,'CHOWNER','Смена владельца');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (9,'EXECUTE','Выполнение');
INSERT INTO sqlaction (sqlaction_id,sqlaction_sql,sqlaction_note) VALUES (10,'ADMIN','Администрирование');

ALTER SEQUENCE sqlaction_id_gen RESTART WITH 11;;
