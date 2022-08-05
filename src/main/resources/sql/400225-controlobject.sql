/* Контролируемый объект */
CREATE TABLE controlobject (
	controlobject_id	INTEGER NOT NULL,
	controlobject_name	VARCHAR(128) NOT NULL,
	controlobject_url	VARCHAR(255) NOT NULL,
	CONSTRAINT controlobject_pk PRIMARY KEY (controlobject_id),
	CONSTRAINT controlobject_ak1 UNIQUE (controlobject_name),
	CONSTRAINT controlobject_ak2 UNIQUE (controlobject_url)
);
COMMENT ON TABLE controlobject IS 'Контролируемый объект';
COMMENT ON COLUMN controlobject.controlobject_id IS 'Контролируемый объект ИД';
COMMENT ON COLUMN controlobject.controlobject_name IS 'Наименование';
COMMENT ON COLUMN controlobject.controlobject_url IS 'Идентификатор';
CREATE SEQUENCE controlobject_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE controlobject_id_gen OWNED BY controlobject.controlobject_id;
