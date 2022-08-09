/* Роль роли */
CREATE TABLE accessrolerole (
	accessrolerole_id	    INTEGER NOT NULL,
	accessrole_id_parent	INTEGER NOT NULL,
	accessrole_id_child	  INTEGER NOT NULL,
	CONSTRAINT accessrolerole_pk PRIMARY KEY (accessrolerole_id),
	CONSTRAINT accessrolerole_ak1 UNIQUE (accessrole_id_parent, accessrole_id_child)
);
ALTER TABLE accessrolerole ADD
	CONSTRAINT accessrolerole_fk1 FOREIGN KEY (accessrole_id_parent) REFERENCES accessrole ON DELETE CASCADE;
ALTER TABLE accessrolerole ADD
	CONSTRAINT accessrolerole_fk2 FOREIGN KEY (accessrole_id_child) REFERENCES accessrole  ON DELETE CASCADE;
CREATE INDEX accessrolerole_if1 ON accessrolerole (accessrole_id_parent);
CREATE INDEX accessrolerole_if2 ON accessrolerole (accessrole_id_child);
COMMENT ON TABLE accessrolerole IS 'Роль роли';
COMMENT ON COLUMN accessrolerole.accessrolerole_id IS 'Роль роли ИД';
COMMENT ON COLUMN accessrolerole.accessrole_id_parent IS 'Роль - родитель ИД';
COMMENT ON COLUMN accessrolerole.accessrole_id_child IS 'Роль - ребенок ИД';
CREATE SEQUENCE accessrolerole_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE accessrolerole_id_gen OWNED BY accessrolerole.accessrolerole_id;