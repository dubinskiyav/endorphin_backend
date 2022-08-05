/* Календарь */
CREATE TABLE cal (
	cal_id	INTEGER NOT NULL,
	yearcal_id	INTEGER NOT NULL,
	monthcal_id	INTEGER NOT NULL,
	daycal_id	INTEGER NOT NULL,
	dayofweek_id	INTEGER NOT NULL,
	weeknumber_id	INTEGER NOT NULL,
	cal_value	DATE NOT NULL,
	cal_notation	VARCHAR(10) NOT NULL,
	CONSTRAINT cal_pk PRIMARY KEY (cal_id),
	CONSTRAINT cal_ak1 UNIQUE (yearcal_id, monthcal_id, daycal_id),
	CONSTRAINT cal_ak2 UNIQUE (cal_value),
	CONSTRAINT cal_ak3 UNIQUE (cal_notation)
);
ALTER TABLE cal ADD
	CONSTRAINT cal_fk1 FOREIGN KEY (yearcal_id) REFERENCES yearcal;
ALTER TABLE cal ADD
	CONSTRAINT cal_fk2 FOREIGN KEY (monthcal_id) REFERENCES monthcal;
ALTER TABLE cal ADD
	CONSTRAINT cal_fk3 FOREIGN KEY (daycal_id) REFERENCES daycal;
ALTER TABLE cal ADD
	CONSTRAINT cal_fk4 FOREIGN KEY (dayofweek_id) REFERENCES dayofweek;
ALTER TABLE cal ADD
	CONSTRAINT cal_fk5 FOREIGN KEY (weeknumber_id) REFERENCES weeknumber;
CREATE INDEX cal_if1 ON cal (yearcal_id);
CREATE INDEX cal_if2 ON cal (monthcal_id);
CREATE INDEX cal_if3 ON cal (daycal_id);
CREATE INDEX cal_if4 ON cal (dayofweek_id);
CREATE INDEX cal_if5 ON cal (weeknumber_id);
COMMENT ON TABLE cal IS 'Календарь';
COMMENT ON COLUMN cal.cal_id IS 'Календарь_ИД';
COMMENT ON COLUMN cal.yearcal_id IS 'Год календаря_ид';
COMMENT ON COLUMN cal.monthcal_id IS 'Месяц календаря_ИД';
COMMENT ON COLUMN cal.daycal_id IS 'День календаря_ИД';
COMMENT ON COLUMN cal.dayofweek_id IS 'День недели календаря_ИД';
COMMENT ON COLUMN cal.weeknumber_id IS 'Номер недели_ИД';
COMMENT ON COLUMN cal.cal_value IS 'Значение';
COMMENT ON COLUMN cal.cal_notation IS 'Обозначение';
CREATE SEQUENCE cal_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE cal_id_gen OWNED BY cal.cal_id;