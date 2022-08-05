/* Классификатор */
CREATE TABLE capclass (
	capclass_id	INTEGER NOT NULL,
	capclasstype_id	INTEGER NOT NULL,
	capclass_code	VARCHAR(20) NOT NULL,
	capclass_name	VARCHAR(255) NOT NULL,
	capclass_value	DOUBLE PRECISION,
	capclass_sortcode	VARCHAR(10),
	capclass_text	BYTEA,
	capclass_flag	INTEGER,
	capclass_blockflag	INTEGER NOT NULL,
	capclass_remark	VARCHAR(255),
	CONSTRAINT capclass_pk PRIMARY KEY (capclass_id),
	CONSTRAINT capclass_ak1 UNIQUE (capclass_code, capclasstype_id)
);
ALTER TABLE capclass ADD
	CONSTRAINT capclass_fk1 FOREIGN KEY (capclasstype_id) REFERENCES capclasstype;
CREATE INDEX capclass_if1 ON capclass (capclasstype_id);
COMMENT ON TABLE capclass IS 'Классификатор';
COMMENT ON COLUMN capclass.capclass_id IS 'Классификатор ИД';
COMMENT ON COLUMN capclass.capclasstype_id IS 'Тип классификатора ИД';
COMMENT ON COLUMN capclass.capclass_code IS 'Код';
COMMENT ON COLUMN capclass.capclass_name IS 'Наименование';
COMMENT ON COLUMN capclass.capclass_value IS 'Значение';
COMMENT ON COLUMN capclass.capclass_sortcode IS 'Код сортировки';
COMMENT ON COLUMN capclass.capclass_text IS 'Текст';
COMMENT ON COLUMN capclass.capclass_flag IS 'Флаг';
COMMENT ON COLUMN capclass.capclass_blockflag IS 'Флаг блокировки';
COMMENT ON COLUMN capclass.capclass_remark IS 'Примечание';
CREATE SEQUENCE capclass_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capclass_id_gen OWNED BY capclass.capclass_id;

ALTER SEQUENCE capclass_id_gen RESTART WITH 100000;;
