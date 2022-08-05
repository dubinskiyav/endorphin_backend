/* Улица */
CREATE TABLE street (
	street_id	INTEGER NOT NULL,
	town_id	INTEGER NOT NULL,
	streettype_id	INTEGER NOT NULL,
	street_name	VARCHAR(50) NOT NULL,
	street_code	VARCHAR(20) NOT NULL,
	street_aoid	VARCHAR(36),
	street_aoguid	VARCHAR(36),
	street_upddate	TIMESTAMP NOT NULL,
	street_startdate timestamp NULL,
	street_enddate timestamp NULL,
	CONSTRAINT street_pk PRIMARY KEY (street_id),
	CONSTRAINT street_ak2 UNIQUE (street_code)
);
ALTER TABLE street ADD
	CONSTRAINT street_fk1 FOREIGN KEY (town_id) REFERENCES town;
ALTER TABLE street ADD
	CONSTRAINT street_fk2 FOREIGN KEY (streettype_id) REFERENCES streettype;
CREATE INDEX street_if1 ON street (town_id);
CREATE INDEX street_if2 ON street (streettype_id);
COMMENT ON TABLE street IS 'Улица';
COMMENT ON COLUMN street.street_id IS 'Улица ИД';
COMMENT ON COLUMN street.town_id IS 'Населенный пункт ИД';
COMMENT ON COLUMN street.streettype_id IS 'Тип улицы ИД';
COMMENT ON COLUMN street.street_name IS 'Наименование';
COMMENT ON COLUMN street.street_code IS 'Код';
COMMENT ON COLUMN street.street_aoid IS 'Код ГУИД';
COMMENT ON COLUMN street.street_aoguid IS 'Глобальный код ГУИД';
COMMENT ON COLUMN street.street_upddate IS 'Дата модификации';
COMMENT ON COLUMN street.street_startdate IS 'Дата основания';
COMMENT ON COLUMN street.street_enddate IS 'Дата назначенной гибели';
CREATE SEQUENCE street_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE street_id_gen OWNED BY street.street_id;