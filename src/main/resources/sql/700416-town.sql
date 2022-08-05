/* Населенный пункт */
CREATE TABLE town (
	town_id	INTEGER NOT NULL,
	towntype_id	INTEGER NOT NULL,
	town_name	VARCHAR(50) NOT NULL,
	town_code	VARCHAR(20) NOT NULL,
	town_index	VARCHAR(10),
	town_aoid	VARCHAR(36),
	town_aoguid	VARCHAR(36),
	town_upddate	TIMESTAMP NOT NULL,
	town_startdate timestamp NULL,
	town_enddate timestamp NULL,
	town_longitudemin float8 NULL,
	town_latitudemin float8 NULL,
	town_longitudemax float8 NULL,
	town_latitudemax float8 NULL,
	country_id	INTEGER NOT NULL,
	townsubordinate_id	INTEGER NOT NULL,
	town_phonecode varchar(10) NULL,
	CONSTRAINT town_pk PRIMARY KEY (town_id),
	CONSTRAINT town_ak1 UNIQUE (town_code)
);
ALTER TABLE town ADD
	CONSTRAINT town_fk1 FOREIGN KEY (towntype_id) REFERENCES towntype;
ALTER TABLE town ADD
	CONSTRAINT town_fk2 FOREIGN KEY (country_id) REFERENCES country;
ALTER TABLE town ADD
	CONSTRAINT town_fk3 FOREIGN KEY (townsubordinate_id) REFERENCES townsubordinate;
CREATE INDEX town_if1 ON town (towntype_id);
CREATE INDEX town_if2 ON town (country_id);
CREATE INDEX town_if3 ON town (townsubordinate_id);
COMMENT ON TABLE town IS 'Населенный пункт';
COMMENT ON COLUMN town.town_id IS 'Населенный пункт ИД';
COMMENT ON COLUMN town.towntype_id IS 'Тип населенного пункта ИД';
COMMENT ON COLUMN town.town_name IS 'Наименование';
COMMENT ON COLUMN town.town_code IS 'Код';
COMMENT ON COLUMN town.town_index IS 'Индекс';
COMMENT ON COLUMN town.town_aoid IS 'Код ФИАС';
COMMENT ON COLUMN town.town_aoguid IS 'Глобальный код ФИАС';
COMMENT ON COLUMN town.town_upddate IS 'Дата модификации';
COMMENT ON COLUMN town.country_id IS 'Страна ИД';
COMMENT ON COLUMN town.townsubordinate_id IS 'Тип подчиненности города_ИД';
CREATE SEQUENCE town_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE town_id_gen OWNED BY town.town_id;