/* Тип аналитики */
CREATE TABLE subjecttype (
	subjecttype_id	INTEGER NOT NULL,
	subjecttype_name	VARCHAR(30) NOT NULL,
	subjecttype_sortcode	VARCHAR(10),
	CONSTRAINT subjecttype_pk PRIMARY KEY (subjecttype_id),
	CONSTRAINT subjecttype_ak1 UNIQUE (subjecttype_name)
);
COMMENT ON TABLE subjecttype IS 'Тип аналитики';
COMMENT ON COLUMN subjecttype.subjecttype_id IS 'Тип аналитики ИД';
COMMENT ON COLUMN subjecttype.subjecttype_name IS 'Наименование';
COMMENT ON COLUMN subjecttype.subjecttype_sortcode IS 'Код сортировки';
CREATE SEQUENCE subjecttype_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE subjecttype_id_gen OWNED BY subjecttype.subjecttype_id;

INSERT INTO subjecttype VALUES (1,'Уровень','1');
INSERT INTO subjecttype VALUES (2,'Элемент','2');


