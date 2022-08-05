/* Пользователь */
CREATE TABLE proguser (
	proguser_id	INTEGER NOT NULL,
	progusergroup_id	INTEGER NOT NULL,
	proguser_status_id	INTEGER NOT NULL,
	proguser_type	INTEGER,
	proguser_name	VARCHAR(50) NOT NULL,
	proguser_fullname	VARCHAR(50),
	proguser_webpassword	VARCHAR(128),
	proguser_timezonecode	VARCHAR(50),
	CONSTRAINT proguser_pk PRIMARY KEY (proguser_id)
);
ALTER TABLE proguser ADD
	CONSTRAINT proguser_fk1 FOREIGN KEY (progusergroup_id) REFERENCES progusergroup;
ALTER TABLE proguser ADD
	CONSTRAINT proguser_fk2 FOREIGN KEY (proguser_status_id) REFERENCES capcode;
CREATE INDEX proguser_if1 ON proguser (progusergroup_id);
CREATE INDEX proguser_if2 ON proguser (proguser_status_id);
COMMENT ON TABLE proguser IS 'Пользователь';
COMMENT ON COLUMN proguser.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN proguser.progusergroup_id IS 'Группа EVERYONE';
COMMENT ON COLUMN proguser.proguser_status_id IS 'Статус';
COMMENT ON COLUMN proguser.proguser_type IS 'Тип';
COMMENT ON COLUMN proguser.proguser_name IS 'Наименование';
COMMENT ON COLUMN proguser.proguser_fullname IS 'Описание';
COMMENT ON COLUMN proguser.proguser_webpassword IS 'WEB пароль';
COMMENT ON COLUMN proguser.proguser_timezonecode IS 'Код часового пояса';
CREATE SEQUENCE proguser_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE proguser_id_gen OWNED BY proguser.proguser_id;


INSERT INTO proguser VALUES (1, 1, 1301,1, 'SYSDBA','Системный администратор', NULL, NULL);
