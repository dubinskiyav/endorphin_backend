/* Группа пользователей */
CREATE TABLE progusergroup (
	progusergroup_id	INTEGER NOT NULL,
	progusergroup_name	VARCHAR(30) NOT NULL,
	progusergroup_note	VARCHAR(255),
	progusergroup_visible	INTEGER NOT NULL,
	CONSTRAINT progusergroup_pk PRIMARY KEY (progusergroup_id),
	CONSTRAINT progusergroup_ak1 UNIQUE (progusergroup_name)
);
COMMENT ON TABLE progusergroup IS 'Группа пользователей';
COMMENT ON COLUMN progusergroup.progusergroup_id IS 'Группа пользователей ИД';
COMMENT ON COLUMN progusergroup.progusergroup_name IS 'Наименование';
COMMENT ON COLUMN progusergroup.progusergroup_note IS 'Описание';
COMMENT ON COLUMN progusergroup.progusergroup_visible IS 'Видимость';
CREATE SEQUENCE progusergroup_id_gen AS INTEGER START WITH 2 INCREMENT BY 1;
ALTER SEQUENCE progusergroup_id_gen OWNED BY progusergroup.progusergroup_id;

INSERT INTO progusergroup VALUES (1,'Все пользователи','Все пользователи',1);



