/* Атрибут ОАУ со значением */
CREATE TABLE subjectattributevalue (
	subjectattributevalue_id	INTEGER NOT NULL,
	subject_id	INTEGER NOT NULL,
	attribute_id	INTEGER NOT NULL,
	subjectattributevalue_value	VARCHAR(255),
	subjectattributevalue_float	DOUBLE PRECISION,
	subjectattributevalue_integer	INTEGER,
	subjectattributevalue_date	TIMESTAMP,
	subjectattributevalue_string	VARCHAR(255),
	subjectattributevalue_blob	BYTEA,
	subject_id_value	INTEGER,
	capclass_id	INTEGER,
	subjectattributevalue_valdate	DATE,
	CONSTRAINT subjectattributevalue_pk PRIMARY KEY (subjectattributevalue_id),
	CONSTRAINT subjectattributevalue_ak1 UNIQUE (subject_id, attribute_id, subjectattributevalue_valdate)
);
ALTER TABLE subjectattributevalue ADD
	CONSTRAINT subjectattributevalue_fk1 FOREIGN KEY (subject_id) REFERENCES subject;
ALTER TABLE subjectattributevalue ADD
	CONSTRAINT subjectattributevalue_fk2 FOREIGN KEY (attribute_id) REFERENCES attribute;
ALTER TABLE subjectattributevalue ADD
	CONSTRAINT subjectattributevalue_fk3 FOREIGN KEY (subject_id_value) REFERENCES subject;
ALTER TABLE subjectattributevalue ADD
	CONSTRAINT subjectattributevalue_fk4 FOREIGN KEY (capclass_id) REFERENCES capclass;
CREATE INDEX subjectattributevalue_if1 ON subjectattributevalue (subject_id);
CREATE INDEX subjectattributevalue_if2 ON subjectattributevalue (attribute_id);
CREATE INDEX subjectattributevalue_if3 ON subjectattributevalue (subject_id_value);
CREATE INDEX subjectattributevalue_if4 ON subjectattributevalue (capclass_id);
COMMENT ON TABLE subjectattributevalue IS 'Атрибут ОАУ со значением';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_id IS 'Атрибут ОАУ со значением ИД';
COMMENT ON COLUMN subjectattributevalue.subject_id IS 'Объект аналитики ИД';
COMMENT ON COLUMN subjectattributevalue.attribute_id IS 'Ресурс ИД';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_value IS 'Значение';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_float IS 'Вещественное';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_integer IS 'Целое';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_date IS 'Дата';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_string IS 'Строка';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_blob IS 'Таблица';
COMMENT ON COLUMN subjectattributevalue.subject_id_value IS 'Объект аналитики';
COMMENT ON COLUMN subjectattributevalue.capclass_id IS 'Классификатор ИД';
COMMENT ON COLUMN subjectattributevalue.subjectattributevalue_valdate IS 'Дата значения';
CREATE SEQUENCE subjectattributevalue_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE subjectattributevalue_id_gen OWNED BY subjectattributevalue.subjectattributevalue_id;