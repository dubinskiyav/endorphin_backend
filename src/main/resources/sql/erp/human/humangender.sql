/* Пол человека */
CREATE TABLE humangender (
	humangender_id	         INTEGER NOT NULL,
	humangender_name	       VARCHAR(20) NOT NULL,
	humangender_abbr        VARCHAR(3) NOT NULL,
	CONSTRAINT humangender_pk PRIMARY KEY (humangender_id),
	CONSTRAINT humangender_ak1 UNIQUE (humangender_name),
	CONSTRAINT humangender_ak2 UNIQUE (humangender_abbr)
);
COMMENT ON TABLE humangender IS 'Пол человека';
COMMENT ON COLUMN humangender.humangender_id IS 'Пол человека ИД';
COMMENT ON COLUMN humangender.humangender_name IS 'Наименование';
COMMENT ON COLUMN humangender.humangender_abbr IS 'Сокращение';
CREATE SEQUENCE humangender_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE humangender_id_gen OWNED BY humangender.humangender_id;


