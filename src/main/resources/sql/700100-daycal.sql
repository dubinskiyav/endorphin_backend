/* День календаря */
CREATE TABLE daycal (
	daycal_id	INTEGER NOT NULL,
	daycal_value	INTEGER NOT NULL,
	daycal_notation	VARCHAR(2) NOT NULL,
	CONSTRAINT daycal_pk PRIMARY KEY (daycal_id),
	CONSTRAINT daycal_ak1 UNIQUE (daycal_value),
	CONSTRAINT daycal_ak2 UNIQUE (daycal_notation)
);
COMMENT ON TABLE daycal IS 'День календаря';
COMMENT ON COLUMN daycal.daycal_id IS 'День календаря_ИД';
COMMENT ON COLUMN daycal.daycal_value IS 'Значение';
COMMENT ON COLUMN daycal.daycal_notation IS 'Обозначение';
CREATE SEQUENCE daycal_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE daycal_id_gen OWNED BY daycal.daycal_id;