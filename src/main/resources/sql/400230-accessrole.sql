/* Роль */
CREATE TABLE accessrole (
	accessrole_id	INTEGER NOT NULL,
	accessrole_name	VARCHAR(30) NOT NULL,
	accessrole_note	VARCHAR(255),
	accessrole_visible	INTEGER NOT NULL,
	CONSTRAINT accessrole_pk PRIMARY KEY (accessrole_id),
	CONSTRAINT accessrole_ak1 UNIQUE (accessrole_name)
);
COMMENT ON TABLE accessrole IS 'Роль';
COMMENT ON COLUMN accessrole.accessrole_id IS 'Роль ИД';
COMMENT ON COLUMN accessrole.accessrole_name IS 'Наименование';
COMMENT ON COLUMN accessrole.accessrole_note IS 'Описание';
COMMENT ON COLUMN accessrole.accessrole_visible IS 'Видимость';
CREATE SEQUENCE accessrole_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE accessrole_id_gen OWNED BY accessrole.accessrole_id;
