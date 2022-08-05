/* Контрагент */
CREATE TABLE company (
	company_id	INTEGER NOT NULL,
	town_id	INTEGER NOT NULL,
	address_id	INTEGER,
	company_billingcode	VARCHAR(20),
	company_name	VARCHAR(300) NOT NULL,
	company_fullname	VARCHAR(350) NOT NULL,
	company_address	VARCHAR(200) NOT NULL,
	company_telefon	VARCHAR(50),
	company_email	VARCHAR(100),
	company_web	VARCHAR(100),
	company_excavation	INTEGER NOT NULL,
	company_remark	VARCHAR(200),
	company_longitude	DOUBLE PRECISION,
	company_latitude	DOUBLE PRECISION,
	company_upddate	TIMESTAMP NOT NULL,
	company_duplicate	INTEGER NOT NULL,
	company_blockflag	INTEGER NOT NULL,
	company_physicalperson	INTEGER NOT NULL,
	company_flat	INTEGER,
	company_bank	INTEGER NOT NULL,
	addressfact_id	INTEGER,
	company_addressfact	VARCHAR(200) NOT NULL,
	townfact_id	INTEGER NOT NULL,
	company_longitudefact	DOUBLE PRECISION,
	company_latitudefact	DOUBLE PRECISION,
	CONSTRAINT company_pk PRIMARY KEY (company_id)
);
ALTER TABLE company ADD
	CONSTRAINT company_fk1 FOREIGN KEY (address_id) REFERENCES address;
ALTER TABLE company ADD
	CONSTRAINT company_fk2 FOREIGN KEY (town_id) REFERENCES town;
ALTER TABLE company ADD
	CONSTRAINT company_fk3 FOREIGN KEY (addressfact_id) REFERENCES address;
CREATE INDEX company_if1 ON company (address_id);
CREATE INDEX company_if2 ON company (town_id);
CREATE INDEX company_if3 ON company (addressfact_id);
COMMENT ON TABLE company IS 'Контрагент';
COMMENT ON COLUMN company.company_id IS 'Контрагент ИД';
COMMENT ON COLUMN company.town_id IS 'Населенный пункт ИД';
COMMENT ON COLUMN company.address_id IS 'Адрес ИД';
COMMENT ON COLUMN company.company_billingcode IS 'Уникальный ключ из системы Биллинг';
COMMENT ON COLUMN company.company_name IS 'Наименование';
COMMENT ON COLUMN company.company_fullname IS 'Полное наименование';
COMMENT ON COLUMN company.company_address IS 'Адрес';
COMMENT ON COLUMN company.company_telefon IS 'Телефон';
COMMENT ON COLUMN company.company_email IS 'Электронная почта';
COMMENT ON COLUMN company.company_web IS 'Сайт';
COMMENT ON COLUMN company.company_excavation IS 'Флаг согласования раскопки';
COMMENT ON COLUMN company.company_remark IS 'Примечание';
COMMENT ON COLUMN company.company_longitude IS 'Долгота';
COMMENT ON COLUMN company.company_latitude IS 'Широта';
COMMENT ON COLUMN company.company_upddate IS 'Дата модификации';
COMMENT ON COLUMN company.company_duplicate IS 'Является дублем';
COMMENT ON COLUMN company.company_blockflag IS 'Флаг блокировки';
COMMENT ON COLUMN company.company_physicalperson IS 'Физическое дицо';
COMMENT ON COLUMN company.company_flat IS 'Квартира';
COMMENT ON COLUMN company.company_bank IS 'Банк';
COMMENT ON COLUMN company.addressfact_id IS 'Адрес фактический ИД';
COMMENT ON COLUMN company.company_addressfact IS 'Адрес фактический';
COMMENT ON COLUMN company.townfact_id IS 'Населенный пункт факт ИД';
COMMENT ON COLUMN company.company_longitudefact IS 'Долгота факт.';
COMMENT ON COLUMN company.company_latitudefact IS 'Широта факт.';
CREATE SEQUENCE company_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE company_id_gen OWNED BY company.company_id;