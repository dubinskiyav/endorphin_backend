/* Роль пользователя */
CREATE TABLE proguserrole (
	proguserrole_id	INTEGER NOT NULL,
	proguser_id	INTEGER NOT NULL,
	accessrole_id	INTEGER NOT NULL,
	CONSTRAINT proguserrole_pk PRIMARY KEY (proguserrole_id),
	CONSTRAINT proguserrole_ak1 UNIQUE (proguser_id, accessrole_id)
);
ALTER TABLE proguserrole ADD
	CONSTRAINT proguserrole_fk1 FOREIGN KEY (proguser_id) REFERENCES proguser;
ALTER TABLE proguserrole ADD
	CONSTRAINT proguserrole_fk2 FOREIGN KEY (accessrole_id) REFERENCES accessrole;
CREATE INDEX proguserrole_if1 ON proguserrole (proguser_id);
CREATE INDEX proguserrole_if2 ON proguserrole (accessrole_id);
COMMENT ON TABLE proguserrole IS 'Роль пользователя';
COMMENT ON COLUMN proguserrole.proguserrole_id IS 'Роль пользователя ИД';
COMMENT ON COLUMN proguserrole.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN proguserrole.accessrole_id IS 'Роль ИД';
CREATE SEQUENCE proguserrole_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE proguserrole_id_gen OWNED BY proguserrole.proguserrole_id;