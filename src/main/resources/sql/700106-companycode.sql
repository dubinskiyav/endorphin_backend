/* Код контрагента */
CREATE TABLE companycode (
	companycode_id	INTEGER NOT NULL,
	company_id	INTEGER NOT NULL,
	capclass_id	INTEGER NOT NULL,
	companycode_code	VARCHAR(20) NOT NULL,
	CONSTRAINT companycode_pk PRIMARY KEY (companycode_id),
	CONSTRAINT companycode_ak1 UNIQUE (company_id, capclass_id)
);
ALTER TABLE companycode ADD
	CONSTRAINT companycode_fk1 FOREIGN KEY (capclass_id) REFERENCES capclass;
ALTER TABLE companycode ADD
	CONSTRAINT companycode_fk2 FOREIGN KEY (company_id) REFERENCES company;
CREATE INDEX companycode_if1 ON companycode (capclass_id);
CREATE INDEX companycode_if2 ON companycode (company_id);
COMMENT ON TABLE companycode IS 'Код контрагента';
COMMENT ON COLUMN companycode.companycode_id IS 'Код контрагента ИД';
COMMENT ON COLUMN companycode.company_id IS 'Контрагент ИД';
COMMENT ON COLUMN companycode.capclass_id IS 'Классификатор ИД';
COMMENT ON COLUMN companycode.companycode_code IS 'Код';
CREATE SEQUENCE companycode_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE companycode_id_gen OWNED BY companycode.companycode_id;