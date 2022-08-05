/* Тип классификатора */
CREATE TABLE capclasstype (
	capclasstype_id	INTEGER NOT NULL,
	capresource_id	INTEGER NOT NULL,
	capclasstype_code	VARCHAR(10),
	capclasstype_name	VARCHAR(128) NOT NULL,
	CONSTRAINT capclasstype_pk PRIMARY KEY (capclasstype_id),
	CONSTRAINT capclasstype_ak1 UNIQUE (capclasstype_name),
	CONSTRAINT capclasstype_ak2 UNIQUE (capresource_id)
);
ALTER TABLE capclasstype ADD
	CONSTRAINT capclasstype_fk1 FOREIGN KEY (capresource_id) REFERENCES capresource;
CREATE INDEX capclasstype_if1 ON capclasstype (capresource_id);
COMMENT ON TABLE capclasstype IS 'Тип классификатора';
COMMENT ON COLUMN capclasstype.capclasstype_id IS 'Тип классификатора ИД';
COMMENT ON COLUMN capclasstype.capresource_id IS 'Ресурс ИД';
COMMENT ON COLUMN capclasstype.capclasstype_code IS 'Код';
COMMENT ON COLUMN capclasstype.capclasstype_name IS 'Наименование';
CREATE SEQUENCE capclasstype_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capclasstype_id_gen OWNED BY capclasstype.capclasstype_id;

ALTER SEQUENCE capclasstype_id_gen RESTART WITH 100000;;
