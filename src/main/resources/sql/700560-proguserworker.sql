/* Связь пользователя с сотрудником */
CREATE TABLE proguserworker (
	proguser_id	INTEGER NOT NULL,
	worker_id	INTEGER NOT NULL,
	CONSTRAINT proguserworker_pk PRIMARY KEY (proguser_id)
);
ALTER TABLE proguserworker ADD
	CONSTRAINT proguserworker_fk1 FOREIGN KEY (proguser_id) REFERENCES proguser;
ALTER TABLE proguserworker ADD
	CONSTRAINT proguserworker_fk2 FOREIGN KEY (worker_id) REFERENCES worker;
CREATE INDEX proguserworker_if1 ON proguserworker (proguser_id);
CREATE INDEX proguserworker_if2 ON proguserworker (worker_id);
COMMENT ON TABLE proguserworker IS 'Связь пользователя с сотрудником';
COMMENT ON COLUMN proguserworker.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN proguserworker.worker_id IS 'Сотрудник ИД';