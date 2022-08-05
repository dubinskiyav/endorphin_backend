/* Тип ресурса */
CREATE TABLE resourcetype (
	resourcetype_id	INTEGER NOT NULL,
	resourcetype_code	VARCHAR(10),
	resourcetype_name	VARCHAR(50) NOT NULL,
	resourcetype_text	BYTEA,
	CONSTRAINT resourcetype_pk PRIMARY KEY (resourcetype_id),
	CONSTRAINT resourcetype_ak1 UNIQUE (resourcetype_name)
);
COMMENT ON TABLE resourcetype IS 'Тип ресурса';
COMMENT ON COLUMN resourcetype.resourcetype_id IS 'Тип ресурса ИД';
COMMENT ON COLUMN resourcetype.resourcetype_code IS 'Код';
COMMENT ON COLUMN resourcetype.resourcetype_name IS 'Наименование';
COMMENT ON COLUMN resourcetype.resourcetype_text IS 'Текст расчета по умолчанию';
CREATE SEQUENCE resourcetype_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE resourcetype_id_gen OWNED BY resourcetype.resourcetype_id;

INSERT INTO resourcetype VALUES ( 1, '01','Печатные формы',NULL);
INSERT INTO resourcetype VALUES ( 4, '04','Функция пользователя',NULL);
INSERT INTO resourcetype VALUES (11, '11','Нумератор',NULL);
INSERT INTO resourcetype VALUES (12, '12','Признак',NULL);
INSERT INTO resourcetype VALUES (13, '13','Константа',NULL);
INSERT INTO resourcetype VALUES (15, '15','Типы электронных материалов',NULL);


ALTER SEQUENCE resourcetype_id_gen RESTART WITH 16;
