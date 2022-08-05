/* Сотрудник */
CREATE TABLE worker (
	worker_id	INTEGER NOT NULL,
	worker_tabnumber	VARCHAR(15) NOT NULL,
	worker_familyname	VARCHAR(30) NOT NULL,
	worker_firstname	VARCHAR(30) NOT NULL,
	worker_surname	VARCHAR(30),
	worker_sex	INTEGER NOT NULL,
	worker_birthday	DATE,
	worker_email	VARCHAR(50),
	worker_homephone	VARCHAR(50),
	worker_workphone	VARCHAR(50),
	worker_contactphone	VARCHAR(50),
	worker_remark	VARCHAR(255),
	CONSTRAINT worker_pk PRIMARY KEY (worker_id),
	CONSTRAINT worker_ak1 UNIQUE (worker_tabnumber)
);
COMMENT ON TABLE worker IS 'Сотрудник';
COMMENT ON COLUMN worker.worker_id IS 'Сотрудник ИД';
COMMENT ON COLUMN worker.worker_tabnumber IS 'Табельный номер';
COMMENT ON COLUMN worker.worker_familyname IS 'Фамилия';
COMMENT ON COLUMN worker.worker_firstname IS 'Имя';
COMMENT ON COLUMN worker.worker_surname IS 'Отчество';
COMMENT ON COLUMN worker.worker_sex IS 'Пол';
COMMENT ON COLUMN worker.worker_birthday IS 'Дата рождения';
COMMENT ON COLUMN worker.worker_email IS 'Электронная почта';
COMMENT ON COLUMN worker.worker_homephone IS 'Домашний телефон';
COMMENT ON COLUMN worker.worker_workphone IS 'Рабочий телефон';
COMMENT ON COLUMN worker.worker_contactphone IS 'Контактный телефон';
COMMENT ON COLUMN worker.worker_remark IS 'Примечание';
CREATE SEQUENCE worker_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE worker_id_gen OWNED BY worker.worker_id;