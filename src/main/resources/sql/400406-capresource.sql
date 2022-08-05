/* Ресурс */
CREATE TABLE capresource (
	capresource_id	INTEGER NOT NULL,
	resourcetrantype_id	INTEGER NOT NULL,
	resourcetype_id	INTEGER NOT NULL,
	capconfig_id	INTEGER NOT NULL,
	proguser_id	INTEGER NOT NULL,
	lastproguser_id	INTEGER,
	capresource_code	VARCHAR(15),
	capresource_name	VARCHAR(128) NOT NULL,
	capresource_active	INTEGER NOT NULL,
	capresource_shortcut	VARCHAR(20),
	capresource_autor	VARCHAR(50),
	capresource_date	DATE,
	capresource_remark	BYTEA,
	capresource_text	BYTEA,
	capresource_blob	BYTEA,
	capresource_pcode	BYTEA,
	capresource_lastmodify	TIMESTAMP,
	capresource_build	INTEGER,
	capresource_texthash	VARCHAR(32),
	capresource_blobhash	VARCHAR(32),
	capresource_image	BYTEA,
	capresource_jscode	BYTEA,
	capresource_define	BYTEA,
	capresource_datebeg	DATE DEFAULT NOW() NOT NULL,
	capresource_dateend	DATE DEFAULT NOW() NOT NULL,
	capresource_path	VARCHAR(256),
	CONSTRAINT capresource_pk PRIMARY KEY (capresource_id)
);
ALTER TABLE capresource ADD
	CONSTRAINT capresource_fk1 FOREIGN KEY (resourcetrantype_id) REFERENCES resourcetrantype;
ALTER TABLE capresource ADD
	CONSTRAINT capresource_fk2 FOREIGN KEY (resourcetype_id) REFERENCES resourcetype;
ALTER TABLE capresource ADD
	CONSTRAINT capresource_fk3 FOREIGN KEY (capconfig_id) REFERENCES capconfig;
ALTER TABLE capresource ADD
	CONSTRAINT capresource_fk4 FOREIGN KEY (proguser_id) REFERENCES proguser;
ALTER TABLE capresource ADD
	CONSTRAINT capresource_fk5 FOREIGN KEY (lastproguser_id) REFERENCES proguser;
CREATE INDEX capresource_if1 ON capresource (resourcetrantype_id);
CREATE INDEX capresource_if2 ON capresource (resourcetype_id);
CREATE INDEX capresource_if3 ON capresource (capconfig_id);
CREATE INDEX capresource_if4 ON capresource (proguser_id);
CREATE INDEX capresource_if5 ON capresource (lastproguser_id);
COMMENT ON TABLE capresource IS 'Ресурс';
COMMENT ON COLUMN capresource.capresource_id IS 'Ресурс ИД';
COMMENT ON COLUMN capresource.resourcetrantype_id IS 'Тип транслятора ИД';
COMMENT ON COLUMN capresource.resourcetype_id IS 'Тип ресурса ИД';
COMMENT ON COLUMN capresource.capconfig_id IS 'Конфигурация системы ИД';
COMMENT ON COLUMN capresource.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN capresource.lastproguser_id IS 'Последний пользователь';
COMMENT ON COLUMN capresource.capresource_code IS 'Код';
COMMENT ON COLUMN capresource.capresource_name IS 'Наименование';
COMMENT ON COLUMN capresource.capresource_active IS 'Активность';
COMMENT ON COLUMN capresource.capresource_shortcut IS 'Горячая клавиша';
COMMENT ON COLUMN capresource.capresource_autor IS 'Автор';
COMMENT ON COLUMN capresource.capresource_date IS 'Дата создания';
COMMENT ON COLUMN capresource.capresource_remark IS 'Описание';
COMMENT ON COLUMN capresource.capresource_text IS 'Текст расчета';
COMMENT ON COLUMN capresource.capresource_blob IS 'Контейнер';
COMMENT ON COLUMN capresource.capresource_pcode IS 'ПКод расчета';
COMMENT ON COLUMN capresource.capresource_lastmodify IS 'Дата последней модификации';
COMMENT ON COLUMN capresource.capresource_build IS 'Версия сборки';
COMMENT ON COLUMN capresource.capresource_texthash IS 'Хеш код текста ресурса';
COMMENT ON COLUMN capresource.capresource_blobhash IS 'Хеш код блоба ресерса';
COMMENT ON COLUMN capresource.capresource_image IS 'Иконка ресурса';
COMMENT ON COLUMN capresource.capresource_jscode IS 'Код ресурса';
COMMENT ON COLUMN capresource.capresource_define IS 'Определение';
COMMENT ON COLUMN capresource.capresource_datebeg IS 'Дата начала';
COMMENT ON COLUMN capresource.capresource_dateend IS 'Дата окончания';
COMMENT ON COLUMN capresource.capresource_path IS 'Путь размещения в меню';
CREATE SEQUENCE capresource_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
