/* Адрес */
CREATE TABLE address (
	address_id	INTEGER NOT NULL,
	building_id	INTEGER NOT NULL,
	district_id	INTEGER NOT NULL,
	subdistrict_id	INTEGER NOT NULL,
	street_id	INTEGER NOT NULL,
	addresss_building	VARCHAR(10) NOT NULL,
	addresss_korpus	VARCHAR(10),
	address_aoid	VARCHAR(36),
	address_aoguid	VARCHAR(36),
	address_upddate	TIMESTAMP NOT NULL,
	addresss_flat varchar(10) NULL,
	CONSTRAINT address_pk PRIMARY KEY (address_id),
	CONSTRAINT address_ak1 UNIQUE (street_id, addresss_building, addresss_korpus)
);
ALTER TABLE address ADD
	CONSTRAINT address_fk1 FOREIGN KEY (street_id) REFERENCES street;
ALTER TABLE address ADD
	CONSTRAINT address_fk2 FOREIGN KEY (building_id) REFERENCES building;
ALTER TABLE address ADD
	CONSTRAINT address_fk3 FOREIGN KEY (district_id) REFERENCES district;
ALTER TABLE address ADD
	CONSTRAINT address_fk4 FOREIGN KEY (subdistrict_id) REFERENCES subdistrict;
CREATE INDEX address_if1 ON address (street_id);
CREATE INDEX address_if2 ON address (building_id);
CREATE INDEX address_if3 ON address (district_id);
CREATE INDEX address_if4 ON address (subdistrict_id);
COMMENT ON TABLE address IS 'Адрес';
COMMENT ON COLUMN address.address_id IS 'Адрес ИД';
COMMENT ON COLUMN address.building_id IS 'Строение ИД';
COMMENT ON COLUMN address.district_id IS 'Район населенного пункта ИД';
COMMENT ON COLUMN address.subdistrict_id IS 'Микрорайон населенного пункта ИД';
COMMENT ON COLUMN address.street_id IS 'Улица ИД';
COMMENT ON COLUMN address.addresss_building IS 'Дом';
COMMENT ON COLUMN address.addresss_korpus IS 'Корпус';
COMMENT ON COLUMN address.address_aoid IS 'Код ФИАС';
COMMENT ON COLUMN address.address_aoguid IS 'Глобальный код ФИАС';
COMMENT ON COLUMN address.address_upddate IS 'Дата модификации';
CREATE SEQUENCE address_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE address_id_gen OWNED BY address.address_id;