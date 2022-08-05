/* Кодификатор */
CREATE TABLE capcode (
	capcode_id	INTEGER NOT NULL,
	capcodetype_id	INTEGER NOT NULL,
	capcode_code	VARCHAR(10) NOT NULL,
	capcode_name	VARCHAR(50) NOT NULL,
	capcode_sortcode	VARCHAR(10),
	capcode_text	BYTEA,
	CONSTRAINT capcode_pk PRIMARY KEY (capcode_id),
	CONSTRAINT capcode_ak1 UNIQUE (capcodetype_id, capcode_name),
	CONSTRAINT capcode_ak2 UNIQUE (capcodetype_id, capcode_code)
);
ALTER TABLE capcode ADD
	CONSTRAINT capcode_fk1 FOREIGN KEY (capcodetype_id) REFERENCES capcodetype;
CREATE INDEX capcode_if1 ON capcode (capcodetype_id);
COMMENT ON TABLE capcode IS 'Кодификатор';
COMMENT ON COLUMN capcode.capcode_id IS 'Кодификатор ИД';
COMMENT ON COLUMN capcode.capcodetype_id IS 'Тип кодификатора ИД';
COMMENT ON COLUMN capcode.capcode_code IS 'Код';
COMMENT ON COLUMN capcode.capcode_name IS 'Наименование';
COMMENT ON COLUMN capcode.capcode_sortcode IS 'Код сортировки';
COMMENT ON COLUMN capcode.capcode_text IS 'Текст';
CREATE SEQUENCE capcode_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capcode_id_gen OWNED BY capcode.capcode_id;

INSERT INTO capcode VALUES (400,   4,'400','Не указан',                   '000',NULL);
INSERT INTO capcode VALUES (401,   4,'401','Вещественный',                '001',NULL);
INSERT INTO capcode VALUES (402,   4,'402','Целый',                       '002',NULL);
INSERT INTO capcode VALUES (403,   4,'403','Дата',                        '003',NULL);
INSERT INTO capcode VALUES (404,   4,'404','Строка',                      '004',NULL);
INSERT INTO capcode VALUES (405,   4,'405','Блоб (бинарные данные)',      '005',NULL);
INSERT INTO capcode VALUES (406,   4,'406','Денежный',                    '006',NULL);
INSERT INTO capcode VALUES (407,   4,'407','Блоб (текстовые данные)',     '007',NULL);
INSERT INTO capcode VALUES (408,   4,'408','Объект аналитического учета', '008',NULL);
INSERT INTO capcode VALUES (409,   4,'409','Дата и время',                '009',NULL);
INSERT INTO capcode VALUES (410,   4,'410','Логический',                  '010',NULL);
INSERT INTO capcode VALUES (411,   4,'411','Справочник',                  '011',NULL);

INSERT INTO capcode VALUES (1301,  13,'1301','Активный',   '001',NULL);
INSERT INTO capcode VALUES (1302,  13,'1302','Неактивный', '002',NULL);
INSERT INTO capcode VALUES (1303,  13,'1332','Архивный',   '003',NULL);

INSERT INTO capcode VALUES (9001, 90,'9001','Электронная почта', '001',NULL);
INSERT INTO capcode VALUES (9002, 90,'9002','Телефон',           '002',NULL);
INSERT INTO capcode VALUES (9003, 90,'9003','Прочий',            '003',NULL);

INSERT INTO capcode VALUES (21481, 2148,'1302','Аутентификация по паролю', '01',NULL);


ALTER SEQUENCE capcode_id_gen RESTART WITH 2149;;
