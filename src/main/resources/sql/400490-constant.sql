/* Константа */
CREATE TABLE constant (
	constant_id	INTEGER NOT NULL,
	constantgroup_id	INTEGER NOT NULL,
	constant_type	INTEGER NOT NULL,
	CONSTRAINT constant_pk PRIMARY KEY (constant_id)
);
ALTER TABLE constant ADD
	CONSTRAINT constant_fk1 FOREIGN KEY (constant_id) REFERENCES capresource;
ALTER TABLE constant ADD
	CONSTRAINT constant_fk2 FOREIGN KEY (constantgroup_id) REFERENCES capclass;
CREATE INDEX constant_if1 ON constant (constant_id);
CREATE INDEX constant_if2 ON constant (constantgroup_id);
COMMENT ON TABLE constant IS 'Константа';
COMMENT ON COLUMN constant.constant_id IS 'Ресурс ИД';
COMMENT ON COLUMN constant.constantgroup_id IS 'Группа констант ИД';
COMMENT ON COLUMN constant.constant_type IS 'Тип';
CREATE SEQUENCE constant_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE constant_id_gen OWNED BY constant.constant_id;