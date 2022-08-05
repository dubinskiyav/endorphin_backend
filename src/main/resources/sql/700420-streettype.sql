/* Тип улицы */
CREATE TABLE streettype (
	streettype_id	INTEGER NOT NULL,
	streettype_name	VARCHAR(50) NOT NULL,
	streettype_code	VARCHAR(10) NOT NULL,
	CONSTRAINT streettype_pk PRIMARY KEY (streettype_id),
	CONSTRAINT streettype_ak1 UNIQUE (streettype_name),
	CONSTRAINT streettype_ak2 UNIQUE (streettype_code)
);
COMMENT ON TABLE streettype IS 'Тип улицы';
COMMENT ON COLUMN streettype.streettype_id IS 'Тип улицы ИД';
COMMENT ON COLUMN streettype.streettype_name IS 'Наименование';
COMMENT ON COLUMN streettype.streettype_code IS 'Код';
CREATE SEQUENCE streettype_id_gen AS INTEGER START WITH 4 INCREMENT BY 1;
ALTER SEQUENCE streettype_id_gen OWNED BY streettype.streettype_id;
INSERT INTO streettype (streettype_id,streettype_name,streettype_code) VALUES
(-1,'',''),
(1,'УЛИЦА','УЛ'),
(2,'ПЕРЕУЛОК','ПЕР'),
(3,'ПРОСПЕКТ','ПР-КТ');