/* Роль в модуле */
CREATE TABLE applicationrole (
	applicationrole_id	INTEGER NOT NULL,
	accessrole_id	INTEGER NOT NULL,
	application_id	INTEGER NOT NULL,
	CONSTRAINT applicationrole_pk PRIMARY KEY (applicationrole_id),
	CONSTRAINT applicationrole_ak1 UNIQUE (accessrole_id, application_id)
);
ALTER TABLE applicationrole ADD
	CONSTRAINT applicationrole_fk1 FOREIGN KEY (accessrole_id) REFERENCES accessrole;
ALTER TABLE applicationrole ADD
	CONSTRAINT applicationrole_fk2 FOREIGN KEY (application_id) REFERENCES application;
CREATE INDEX applicationrole_if1 ON applicationrole (accessrole_id);
CREATE INDEX applicationrole_if2 ON applicationrole (application_id);
COMMENT ON TABLE applicationrole IS 'Роль в модуле';
COMMENT ON COLUMN applicationrole.applicationrole_id IS 'Роль в модуле ИД';
COMMENT ON COLUMN applicationrole.accessrole_id IS 'Роль ИД';
COMMENT ON COLUMN applicationrole.application_id IS 'Приложение ИД';
CREATE SEQUENCE applicationrole_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE applicationrole_id_gen OWNED BY applicationrole.applicationrole_id;