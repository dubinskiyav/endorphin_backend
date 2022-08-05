/* Номер недели */
CREATE TABLE weeknumber (
	weeknumber_id	INTEGER NOT NULL,
	CONSTRAINT weeknumber_pk PRIMARY KEY (weeknumber_id)
);
COMMENT ON TABLE weeknumber IS 'Номер недели';
COMMENT ON COLUMN weeknumber.weeknumber_id IS 'Номер недели_ИД';
CREATE SEQUENCE weeknumber_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE weeknumber_id_gen OWNED BY weeknumber.weeknumber_id;