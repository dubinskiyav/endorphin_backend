/* Страна */
CREATE TABLE country (
	country_id	INTEGER NOT NULL,
	country_name	VARCHAR(100) NOT NULL,
	country_code	VARCHAR(5),
	CONSTRAINT country_pk PRIMARY KEY (country_id)
);
COMMENT ON TABLE country IS 'Страна';
COMMENT ON COLUMN country.country_id IS 'Страна ИД';
COMMENT ON COLUMN country.country_name IS 'Наименование';
COMMENT ON COLUMN country.country_code IS 'Код';
CREATE SEQUENCE country_id_gen AS INTEGER START WITH 1000 INCREMENT BY 1;
ALTER SEQUENCE country_id_gen OWNED BY country.country_id;