/* Тип транслятора */
CREATE TABLE resourcetrantype (
	resourcetrantype_id	INTEGER NOT NULL,
	resourcetrantype_code	VARCHAR(10),
	resourcetrantype_name	VARCHAR(50) NOT NULL,
	CONSTRAINT resourcetrantype_pk PRIMARY KEY (resourcetrantype_id),
	CONSTRAINT resourcetrantype_ak1 UNIQUE (resourcetrantype_name)
);
COMMENT ON TABLE resourcetrantype IS 'Тип транслятора';
COMMENT ON COLUMN resourcetrantype.resourcetrantype_id IS 'Тип транслятора ИД';
COMMENT ON COLUMN resourcetrantype.resourcetrantype_code IS 'Код';
COMMENT ON COLUMN resourcetrantype.resourcetrantype_name IS 'Наименование';
CREATE SEQUENCE resourcetrantype_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE resourcetrantype_id_gen OWNED BY resourcetrantype.resourcetrantype_id;

INSERT INTO resourcetrantype VALUES (1,'01','KAV/X');
INSERT INTO resourcetrantype VALUES (2,'02','GOAL');
INSERT INTO resourcetrantype VALUES (3,'03','Java');

ALTER SEQUENCE sqlaction_id_gen RESTART WITH 4;
