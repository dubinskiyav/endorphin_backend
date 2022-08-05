/* Учетная запись пользователя */
CREATE TABLE progusercredential (
	progusercredential_id	INTEGER NOT NULL,
	progusercredential_password	VARCHAR(128) NOT NULL,
	proguser_id	INTEGER NOT NULL,
	progusercredential_type	INTEGER NOT NULL,
	progusercredential_lockflag	INTEGER NOT NULL,
	progusercredential_tempflag	INTEGER NOT NULL,
	CONSTRAINT progusercredential_pk PRIMARY KEY (progusercredential_id),
	CONSTRAINT progusercredential_ak1 UNIQUE (proguser_id, progusercredential_type, progusercredential_tempflag)
);
ALTER TABLE progusercredential ADD
	CONSTRAINT progusercredential_fk1 FOREIGN KEY (proguser_id) REFERENCES proguser;
ALTER TABLE progusercredential ADD
	CONSTRAINT progusercredential_fk2 FOREIGN KEY (progusercredential_type) REFERENCES capcode;
CREATE INDEX progusercredential_if1 ON progusercredential (proguser_id);
CREATE INDEX progusercredential_if2 ON progusercredential (progusercredential_type);
COMMENT ON TABLE progusercredential IS 'Учетная запись пользователя';
COMMENT ON COLUMN progusercredential.progusercredential_id IS 'Учетная запись пользователя ИД';
COMMENT ON COLUMN progusercredential.progusercredential_password IS 'Хэш пароля';
COMMENT ON COLUMN progusercredential.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN progusercredential.progusercredential_type IS 'Тип аутентификации ИД';
COMMENT ON COLUMN progusercredential.progusercredential_lockflag IS 'Флаг блокировки';
COMMENT ON COLUMN progusercredential.progusercredential_tempflag IS 'Флаг временная запись';
CREATE SEQUENCE progusercredential_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE progusercredential_id_gen OWNED BY progusercredential.progusercredential_id;