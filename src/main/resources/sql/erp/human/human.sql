/* Человек */
CREATE TABLE human (
	human_id	          INTEGER NOT NULL,
	human_surname	      VARCHAR(255),
	human_name	        VARCHAR(255),
	human_patronymic	  VARCHAR(255),
	humangender_id    	INTEGER NOT NULL,
	human_birthday      DATE NOT NULL,
	human_inn  	        VARCHAR(255),
	human_note	        VARCHAR(255),
	CONSTRAINT human_pk PRIMARY KEY (human_id),
	CONSTRAINT human_fk_humangender FOREIGN KEY (humangender_id) REFERENCES humangender
);
COMMENT ON TABLE human IS 'Человек';
COMMENT ON COLUMN human.human_id IS 'Человек ИД';
COMMENT ON COLUMN human.human_surname IS 'Фамилия';
COMMENT ON COLUMN human.human_name IS 'Имя';
COMMENT ON COLUMN human.human_patronymic IS 'Отчество';
COMMENT ON COLUMN human.humangender_id IS 'Пол ИД';
COMMENT ON COLUMN human.human_birthday IS 'Дата рождения';
COMMENT ON COLUMN human.human_inn IS 'ИНН';
COMMENT ON COLUMN human.human_note IS 'Примечание';
CREATE SEQUENCE human_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE human_id_gen OWNED BY human.human_id;
