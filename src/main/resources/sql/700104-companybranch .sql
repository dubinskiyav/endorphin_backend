/* Филиал или представительство контрагента */
CREATE TABLE companybranch (
	company_id	INTEGER NOT NULL,
	companymain_id	INTEGER NOT NULL,
	CONSTRAINT companybranch_pk PRIMARY KEY (company_id)
);
ALTER TABLE companybranch ADD
	CONSTRAINT companybranch_fk1 FOREIGN KEY (company_id) REFERENCES company;
ALTER TABLE companybranch ADD
	CONSTRAINT companybranch_fk2 FOREIGN KEY (companymain_id) REFERENCES company;
CREATE INDEX companybranch_if1 ON companybranch (company_id);
CREATE INDEX companybranch_if2 ON companybranch (companymain_id);
COMMENT ON TABLE companybranch IS 'Филиал или представительство контрагента';
COMMENT ON COLUMN companybranch.company_id IS 'Контрагент ИД';
COMMENT ON COLUMN companybranch.companymain_id IS 'Головная организация ИД';