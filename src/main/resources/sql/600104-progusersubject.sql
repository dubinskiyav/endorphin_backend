CREATE TABLE progusersubject (
	proguser_id INTEGER NOT NULL,
	subject_id INTEGER NOT NULL,
	CONSTRAINT progusersubject_pk PRIMARY KEY (proguser_id)
);
CREATE INDEX progusersubject_if1 ON progusersubject USING btree (proguser_id);
CREATE INDEX progusersubject_if2 ON progusersubject USING btree (subject_id);
ALTER TABLE progusersubject ADD CONSTRAINT progusersubject_fk1 FOREIGN KEY (proguser_id) REFERENCES proguser(proguser_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE progusersubject ADD CONSTRAINT progusersubject_fk2 FOREIGN KEY (subject_id) REFERENCES subject(subject_id);

COMMENT ON TABLE progusersubject IS 'Пользователь - Объект аналитического учета';

COMMENT ON COLUMN progusersubject.subject_id IS 'Объект ИД';
COMMENT ON COLUMN progusersubject.proguser_id IS 'Пользователь ИД';
