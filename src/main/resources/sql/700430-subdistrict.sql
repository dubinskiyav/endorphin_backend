/* Микрорайон населенного пункта */
CREATE TABLE subdistrict (
	subdistrict_id	INTEGER NOT NULL,
	town_id	INTEGER NOT NULL,
	subdistrict_name	VARCHAR(35) NOT NULL,
	subdistrict_code	VARCHAR(20) NOT NULL,
	CONSTRAINT subdistrict_pk PRIMARY KEY (subdistrict_id),
	CONSTRAINT subdistrict_ak1 UNIQUE (subdistrict_code)
);
ALTER TABLE subdistrict ADD
	CONSTRAINT subdistrict_fk1 FOREIGN KEY (town_id) REFERENCES town;
CREATE INDEX subdistrict_if1 ON subdistrict (town_id);
COMMENT ON TABLE subdistrict IS 'Микрорайон населенного пункта';
COMMENT ON COLUMN subdistrict.subdistrict_id IS 'Микрорайон населенного пункта ИД';
COMMENT ON COLUMN subdistrict.town_id IS 'Населенный пункт ИД';
COMMENT ON COLUMN subdistrict.subdistrict_name IS 'Наименование';
COMMENT ON COLUMN subdistrict.subdistrict_code IS 'Код';
CREATE SEQUENCE subdistrict_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE subdistrict_id_gen OWNED BY subdistrict.subdistrict_id;