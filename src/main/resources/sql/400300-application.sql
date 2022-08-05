/* Приложение */
CREATE TABLE application (
	application_id	INTEGER NOT NULL,
	application_type	INTEGER NOT NULL,
	application_code	VARCHAR(50),
	application_name	VARCHAR(50) NOT NULL,
	application_exe	VARCHAR(255) NOT NULL,
	application_blob	BYTEA,
	application_desc	VARCHAR(255),
	CONSTRAINT application_pk PRIMARY KEY (application_id),
	CONSTRAINT application_ak1 UNIQUE (application_name)
);
COMMENT ON TABLE application IS 'Приложение';
COMMENT ON COLUMN application.application_id IS 'Приложение ИД';
COMMENT ON COLUMN application.application_type IS 'Тип приложения';
COMMENT ON COLUMN application.application_code IS 'Код приложения';
COMMENT ON COLUMN application.application_name IS 'Наименование приложения';
COMMENT ON COLUMN application.application_exe IS 'Имя исполняемого модуля';
COMMENT ON COLUMN application.application_blob IS 'Дополнительно';
COMMENT ON COLUMN application.application_desc IS 'Описание';
CREATE SEQUENCE application_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE application_id_gen OWNED BY application.application_id;
