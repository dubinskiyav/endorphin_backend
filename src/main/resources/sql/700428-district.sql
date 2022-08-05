/* Район */
CREATE TABLE district (
	district_id int4 NOT NULL,
	town_id int4 NOT NULL,
	district_name varchar(100) NOT NULL,
	district_code varchar(20) NOT NULL,
	CONSTRAINT district_ak1 UNIQUE (district_code),
	CONSTRAINT district_pk PRIMARY KEY (district_id),
	CONSTRAINT district_fk1 FOREIGN KEY (town_id) REFERENCES town(town_id)
);
CREATE INDEX district_if1 ON district USING btree (town_id);

COMMENT ON TABLE district IS 'Район';
COMMENT ON COLUMN district.district_id IS 'Район ИД';
COMMENT ON COLUMN district.town_id IS 'Город ИД';
COMMENT ON COLUMN district.district_name IS 'Наименование';
COMMENT ON COLUMN district.district_code IS 'Код';
CREATE SEQUENCE district_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE district_id_gen OWNED BY district.district_id;