/* Запрет доступа к ресурсу системы */
CREATE TABLE capresourcerole (
	capresourcerole_id	INTEGER NOT NULL,
	accessrole_id	INTEGER NOT NULL,
	capresource_id	INTEGER NOT NULL,
	capresourcerole_restrictflag	INTEGER NOT NULL,
	CONSTRAINT capresourcerole_pk PRIMARY KEY (capresourcerole_id),
	CONSTRAINT capresourcerole_ak1 UNIQUE (accessrole_id, capresource_id)
);
ALTER TABLE capresourcerole ADD
	CONSTRAINT capresourcerole_fk1 FOREIGN KEY (accessrole_id) REFERENCES accessrole;
ALTER TABLE capresourcerole ADD
	CONSTRAINT capresourcerole_fk2 FOREIGN KEY (capresource_id) REFERENCES capresource;
CREATE INDEX capresourcerole_if1 ON capresourcerole (accessrole_id);
CREATE INDEX capresourcerole_if2 ON capresourcerole (capresource_id);
COMMENT ON TABLE capresourcerole IS 'Запрет доступа к ресурсу системы';
COMMENT ON COLUMN capresourcerole.capresourcerole_id IS 'Запрет доступа к ресурсу системы ИД';
COMMENT ON COLUMN capresourcerole.accessrole_id IS 'Роль ИД';
COMMENT ON COLUMN capresourcerole.capresource_id IS 'Ресурс ИД';
COMMENT ON COLUMN capresourcerole.capresourcerole_restrictflag IS 'Флаг запрета';
CREATE SEQUENCE capresourcerole_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capresourcerole_id_gen OWNED BY capresourcerole.capresourcerole_id;