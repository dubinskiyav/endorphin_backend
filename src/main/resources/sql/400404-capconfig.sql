/* Конфигурация */
CREATE TABLE capconfig (
	capconfig_id	INTEGER NOT NULL,
	capconfig_code	VARCHAR(10),
	capconfig_name	VARCHAR(50) NOT NULL,
	CONSTRAINT capconfig_pk PRIMARY KEY (capconfig_id),
	CONSTRAINT capconfig_ak1 UNIQUE (capconfig_name)
);
COMMENT ON TABLE capconfig IS 'Конфигурация';
COMMENT ON COLUMN capconfig.capconfig_id IS 'Конфигурация системы ИД';
COMMENT ON COLUMN capconfig.capconfig_code IS 'Код';
COMMENT ON COLUMN capconfig.capconfig_name IS 'Наименование';
CREATE SEQUENCE capconfig_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capconfig_id_gen OWNED BY capconfig.capconfig_id;

INSERT INTO capconfig VALUES (1, '01','Базовая конфигурация');
INSERT INTO capconfig VALUES (2, '02','Конфигурация пользователя');


ALTER SEQUENCE capconfig_id_gen RESTART WITH 3;
