/* Авторизационный токен пользователя */
CREATE TABLE proguserauth (
	proguserauth_id	INTEGER NOT NULL,
	proguser_id	INTEGER NOT NULL,
	proguserauth_datecreate	DATE NOT NULL,
	proguserauth_lastquery	DATE NOT NULL,
	proguserauth_dateend	DATE,
	proguserauth_token	VARCHAR(128) NOT NULL,
	CONSTRAINT proguserauth_pk PRIMARY KEY (proguserauth_id)
);
ALTER TABLE proguserauth ADD
	CONSTRAINT proguserauth_fk1 FOREIGN KEY (proguser_id) REFERENCES proguser;
CREATE INDEX proguserauth_if1 ON proguserauth (proguser_id);
COMMENT ON TABLE proguserauth IS 'Авторизационный токен пользователя';
COMMENT ON COLUMN proguserauth.proguserauth_id IS 'Авторизационный токен пользователя ИД';
COMMENT ON COLUMN proguserauth.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN proguserauth.proguserauth_datecreate IS 'Дата и время создания';
COMMENT ON COLUMN proguserauth.proguserauth_lastquery IS 'Дата и время  последнего запроса';
COMMENT ON COLUMN proguserauth.proguserauth_dateend IS 'Дата и время  окончания действия';
COMMENT ON COLUMN proguserauth.proguserauth_token IS 'Токен';
CREATE SEQUENCE proguserauth_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE proguserauth_id_gen OWNED BY proguserauth.proguserauth_id;