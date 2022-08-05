CREATE TABLE capcodetype (
	capcodetype_id	INTEGER NOT NULL,
	capcodetype_code	VARCHAR(10) NOT NULL,
	capcodetype_name	VARCHAR(50) NOT NULL,
	capcodetype_text	BYTEA,
	CONSTRAINT capcodetype_pk PRIMARY KEY (capcodetype_id),
	CONSTRAINT capcodetype_ak1 UNIQUE (capcodetype_name),
	CONSTRAINT capcodetype_ak2 UNIQUE (capcodetype_code)
);
COMMENT ON TABLE capcodetype IS 'Тип кодификатора';
COMMENT ON COLUMN capcodetype.capcodetype_id IS 'Тип кодификатора ИД';
COMMENT ON COLUMN capcodetype.capcodetype_code IS 'Код';
COMMENT ON COLUMN capcodetype.capcodetype_name IS 'Наименование';
COMMENT ON COLUMN capcodetype.capcodetype_text IS 'Текст';
CREATE SEQUENCE capcodetype_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capcodetype_id_gen OWNED BY capcodetype.capcodetype_id;

INSERT INTO capcodetype VALUES (4,    '004', 'Тип данных атрибута',NULL);
INSERT INTO capcodetype VALUES (13,   '013', 'Тип статуса пользователя',NULL);
INSERT INTO capcodetype VALUES (2148, '2148','Типы аутентификации',NULL);
INSERT INTO capcodetype VALUES (90,    '90', 'Канал оповещения',NULL);

ALTER SEQUENCE capcodetype_id_gen RESTART WITH 2149;;
