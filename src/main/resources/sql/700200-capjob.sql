/* Задание */
CREATE TABLE capjob (
	capjob_id	INTEGER NOT NULL,
	capresource_id	INTEGER NOT NULL,
	capjobtype_id	INTEGER NOT NULL,
	capjob_name	VARCHAR(255) NOT NULL,
	capjob_flagactive	INTEGER NOT NULL,
	capjob_failiterationcount	INTEGER NOT NULL,
	capjob_failiterationinterval	INTEGER,
	capjob_remark	VARCHAR(512),
	period_id	INTEGER NOT NULL,
	capjob_interval	INTEGER NOT NULL,
	capjob_datebeg	TIMESTAMP NOT NULL,
	capjob_dateend	TIMESTAMP,
	capjob_iterationcount	INTEGER,
	capjob_days	VARCHAR(128),
	daycal_id	INTEGER,
	capjob_weeknumber	INTEGER,
	dayofweek_id	INTEGER,
	monthcal_id	INTEGER,
	CONSTRAINT capjob_pk PRIMARY KEY (capjob_id),
	CONSTRAINT capjob_ak1 UNIQUE (capjob_name)
);
ALTER TABLE capjob ADD
	CONSTRAINT capjob_fk1 FOREIGN KEY (capresource_id) REFERENCES capresource;
ALTER TABLE capjob ADD
	CONSTRAINT capjob_fk2 FOREIGN KEY (capjobtype_id) REFERENCES capclass;
ALTER TABLE capjob ADD
	CONSTRAINT capjob_fk3 FOREIGN KEY (period_id) REFERENCES capcode;
ALTER TABLE capjob ADD
	CONSTRAINT capjob_fk4 FOREIGN KEY (daycal_id) REFERENCES daycal;
ALTER TABLE capjob ADD
	CONSTRAINT capjob_fk5 FOREIGN KEY (dayofweek_id) REFERENCES dayofweek;
ALTER TABLE capjob ADD
	CONSTRAINT capjob_fk6 FOREIGN KEY (monthcal_id) REFERENCES monthcal;
CREATE INDEX capjob_if1 ON capjob (capresource_id);
CREATE INDEX capjob_if2 ON capjob (capjobtype_id);
CREATE INDEX capjob_if3 ON capjob (period_id);
CREATE INDEX capjob_if4 ON capjob (daycal_id);
CREATE INDEX capjob_if5 ON capjob (dayofweek_id);
CREATE INDEX capjob_if6 ON capjob (monthcal_id);
COMMENT ON TABLE capjob IS 'Задание';
COMMENT ON COLUMN capjob.capjob_id IS 'Задание ИД';
COMMENT ON COLUMN capjob.capresource_id IS 'Ресурс ИД';
COMMENT ON COLUMN capjob.capjobtype_id IS 'Тип ИД';
COMMENT ON COLUMN capjob.capjob_name IS 'Наименование';
COMMENT ON COLUMN capjob.capjob_flagactive IS 'Активность';
COMMENT ON COLUMN capjob.capjob_failiterationcount IS 'Количество повторов при аварийном завершении';
COMMENT ON COLUMN capjob.capjob_failiterationinterval IS 'Интервал повтора при аварийном завершении';
COMMENT ON COLUMN capjob.capjob_remark IS 'Примечание';
COMMENT ON COLUMN capjob.period_id IS 'Периодичность ИД';
COMMENT ON COLUMN capjob.capjob_interval IS 'Периодичность';
COMMENT ON COLUMN capjob.capjob_datebeg IS 'Дата начала';
COMMENT ON COLUMN capjob.capjob_dateend IS 'Дата окончания';
COMMENT ON COLUMN capjob.capjob_iterationcount IS 'Количество повторений';
COMMENT ON COLUMN capjob.capjob_days IS 'Дни Месяцы';
COMMENT ON COLUMN capjob.daycal_id IS 'День календаря_ИД';
COMMENT ON COLUMN capjob.capjob_weeknumber IS 'Неделя';
COMMENT ON COLUMN capjob.dayofweek_id IS 'День недели календаря ИД';
COMMENT ON COLUMN capjob.monthcal_id IS 'Месяц календаря_ИД';
CREATE SEQUENCE capjob_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capjob_id_gen OWNED BY capjob.capjob_id;