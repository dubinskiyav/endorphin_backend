/* Строение */
CREATE TABLE building (
	building_id	INTEGER NOT NULL,
	building_longitude	DOUBLE PRECISION NOT NULL,
	building_latitude	DOUBLE PRECISION NOT NULL,
	building_floors	INTEGER,
	building_remark	VARCHAR(255),
	building_upddate	TIMESTAMP NOT NULL,
	CONSTRAINT building_pk PRIMARY KEY (building_id),
	CONSTRAINT building_ak1 UNIQUE (building_latitude, building_longitude)
);
COMMENT ON TABLE building IS 'Строение';
COMMENT ON COLUMN building.building_id IS 'Строение ИД';
COMMENT ON COLUMN building.building_longitude IS 'Долгота';
COMMENT ON COLUMN building.building_latitude IS 'Широта';
COMMENT ON COLUMN building.building_floors IS 'Этажность';
COMMENT ON COLUMN building.building_remark IS 'Примечание';
COMMENT ON COLUMN building.building_upddate IS 'Дата модификации';
CREATE SEQUENCE building_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE building_id_gen OWNED BY building.building_id;