/* Месяц календаря */
CREATE TABLE monthcal (
	monthcal_id	INTEGER NOT NULL,
	monthcal_value	INTEGER NOT NULL,
	monthcal_notation	VARCHAR(8) NOT NULL,
	monthcal_shortnotation	VARCHAR(2) NOT NULL,
	CONSTRAINT monthcal_pk PRIMARY KEY (monthcal_id),
	CONSTRAINT monthcal_ak1 UNIQUE (monthcal_value),
	CONSTRAINT monthcal_ak2 UNIQUE (monthcal_notation),
	CONSTRAINT monthcal_ak3 UNIQUE (monthcal_shortnotation)
);
COMMENT ON TABLE monthcal IS 'Месяц календаря';
COMMENT ON COLUMN monthcal.monthcal_id IS 'Месяц календаря_ИД';
COMMENT ON COLUMN monthcal.monthcal_value IS 'Значение';
COMMENT ON COLUMN monthcal.monthcal_notation IS 'Обозначение';
COMMENT ON COLUMN monthcal.monthcal_shortnotation IS 'Краткое обозначение';
CREATE SEQUENCE monthcal_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE monthcal_id_gen OWNED BY monthcal.monthcal_id;