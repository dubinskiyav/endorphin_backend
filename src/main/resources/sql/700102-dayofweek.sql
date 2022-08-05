/* День недели календаря */
CREATE TABLE dayofweek (
	dayofweek_id	INTEGER NOT NULL,
	dayofweek_value	INTEGER NOT NULL,
	dayofweek_notation	VARCHAR(11) NOT NULL,
	dayofweek_shortnotation	VARCHAR(2) NOT NULL,
	CONSTRAINT dayofweek_pk PRIMARY KEY (dayofweek_id),
	CONSTRAINT dayofweek_ak1 UNIQUE (dayofweek_value),
	CONSTRAINT dayofweek_ak2 UNIQUE (dayofweek_notation),
	CONSTRAINT dayofweek_ak3 UNIQUE (dayofweek_shortnotation)
);
COMMENT ON TABLE dayofweek IS 'День недели календаря';
COMMENT ON COLUMN dayofweek.dayofweek_id IS 'День недели календаря_ИД';
COMMENT ON COLUMN dayofweek.dayofweek_value IS 'Значение';
COMMENT ON COLUMN dayofweek.dayofweek_notation IS 'Обозначение';
COMMENT ON COLUMN dayofweek.dayofweek_shortnotation IS 'Краткое обозначение';
CREATE SEQUENCE dayofweek_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dayofweek_id_gen OWNED BY dayofweek.dayofweek_id;