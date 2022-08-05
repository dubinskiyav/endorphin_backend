/* Контролируемый объект роли */
CREATE TABLE controlobjectrole (
	controlobjectrole_id	INTEGER NOT NULL,
	controlobject_id	INTEGER NOT NULL,
	accessrole_id	INTEGER NOT NULL,
	sqlaction_id	INTEGER NOT NULL,
	CONSTRAINT controlobjectrole_pk PRIMARY KEY (controlobjectrole_id),
	CONSTRAINT controlobjectrole_ak1 UNIQUE (controlobject_id, accessrole_id, sqlaction_id)
);
ALTER TABLE controlobjectrole ADD
	CONSTRAINT controlobjectrole_fk1 FOREIGN KEY (controlobject_id) REFERENCES controlobject;
ALTER TABLE controlobjectrole ADD
	CONSTRAINT controlobjectrole_fk2 FOREIGN KEY (accessrole_id) REFERENCES accessrole;
ALTER TABLE controlobjectrole ADD
	CONSTRAINT controlobjectrole_fk3 FOREIGN KEY (sqlaction_id) REFERENCES sqlaction;
CREATE INDEX controlobjectrole_if1 ON controlobjectrole (controlobject_id);
CREATE INDEX controlobjectrole_if2 ON controlobjectrole (accessrole_id);
CREATE INDEX controlobjectrole_if3 ON controlobjectrole (sqlaction_id);
COMMENT ON TABLE controlobjectrole IS 'Контролируемый объект роли';
COMMENT ON COLUMN controlobjectrole.controlobjectrole_id IS 'Контролируемый объект роли ИД';
COMMENT ON COLUMN controlobjectrole.controlobject_id IS 'Контролируемый объект ИД';
COMMENT ON COLUMN controlobjectrole.accessrole_id IS 'Роль ИД';
COMMENT ON COLUMN controlobjectrole.sqlaction_id IS 'Действие ИД';
CREATE SEQUENCE controlobjectrole_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE controlobjectrole_id_gen OWNED BY controlobjectrole.controlobjectrole_id;