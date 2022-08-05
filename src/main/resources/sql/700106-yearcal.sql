/* Год календаря */
CREATE TABLE yearcal (
	yearcal_id	INTEGER NOT NULL,
	yearcal_value	INTEGER NOT NULL,
	yearcal_notation	VARCHAR(4) NOT NULL,
	yearcal_shortnotation	VARCHAR(2) NOT NULL,
	CONSTRAINT yearcal_pk PRIMARY KEY (yearcal_id),
	CONSTRAINT yearcal_ak1 UNIQUE (yearcal_value),
	CONSTRAINT yearcal_ak2 UNIQUE (yearcal_notation),
	CONSTRAINT yearcal_ak3 UNIQUE (yearcal_shortnotation)
);
COMMENT ON TABLE yearcal IS 'Год календаря';
COMMENT ON COLUMN yearcal.yearcal_id IS 'Год календаря_ид';
COMMENT ON COLUMN yearcal.yearcal_value IS 'Значение';
COMMENT ON COLUMN yearcal.yearcal_notation IS 'Обозначение';
COMMENT ON COLUMN yearcal.yearcal_shortnotation IS 'Краткое обозначение';
CREATE SEQUENCE yearcal_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE yearcal_id_gen OWNED BY yearcal.yearcal_id;