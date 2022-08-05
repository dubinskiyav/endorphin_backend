/* Своя организация */
CREATE TABLE mycompany (
	company_id	INTEGER NOT NULL,
	CONSTRAINT mycompany_pk PRIMARY KEY (company_id)
);
ALTER TABLE mycompany ADD
	CONSTRAINT mycompany_fk1 FOREIGN KEY (company_id) REFERENCES company;
CREATE INDEX mycompany_if1 ON mycompany (company_id);
COMMENT ON TABLE mycompany IS 'Своя организация';
COMMENT ON COLUMN mycompany.company_id IS 'Контрагент ИД';
