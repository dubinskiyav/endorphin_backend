/* Признак */
CREATE TABLE attribute (
	attribute_id	INTEGER NOT NULL,
	attributegroup_id	INTEGER NOT NULL,
	capcode_id	INTEGER NOT NULL,
	capclasstype_id	INTEGER,
	attribute_historicity	INTEGER NOT NULL,
	subject_id	INTEGER,
	CONSTRAINT attribute_pk PRIMARY KEY (attribute_id)
);
ALTER TABLE attribute ADD
	CONSTRAINT attribute_fk1 FOREIGN KEY (capcode_id) REFERENCES capcode;
ALTER TABLE attribute ADD
	CONSTRAINT attribute_fk2 FOREIGN KEY (attribute_id) REFERENCES capresource;
ALTER TABLE attribute ADD
	CONSTRAINT attribute_fk3 FOREIGN KEY (attributegroup_id) REFERENCES capclass;
ALTER TABLE attribute ADD
	CONSTRAINT attribute_fk4 FOREIGN KEY (capclasstype_id) REFERENCES capclasstype;
ALTER TABLE attribute ADD
	CONSTRAINT attribute_fk5 FOREIGN KEY (subject_id) REFERENCES subject;
CREATE INDEX attribute_if1 ON attribute (capcode_id);
CREATE INDEX attribute_if2 ON attribute (attribute_id);
CREATE INDEX attribute_if3 ON attribute (attributegroup_id);
CREATE INDEX attribute_if4 ON attribute (capclasstype_id);
CREATE INDEX attribute_if5 ON attribute (subject_id);
COMMENT ON TABLE attribute IS 'Признак';
COMMENT ON COLUMN attribute.attribute_id IS 'Ресурс ИД';
COMMENT ON COLUMN attribute.attributegroup_id IS 'Группа признаков ИД';
COMMENT ON COLUMN attribute.capcode_id IS 'Кодификатор ИД';
COMMENT ON COLUMN attribute.capclasstype_id IS 'Тип классификатора ИД';
COMMENT ON COLUMN attribute.attribute_historicity IS 'Историчный';
COMMENT ON COLUMN attribute.subject_id IS 'Объект аналитики ИД';
CREATE SEQUENCE attribute_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE attribute_id_gen OWNED BY attribute.attribute_id;