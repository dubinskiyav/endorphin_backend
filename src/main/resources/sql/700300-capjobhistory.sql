/* История выполнения заданий */
CREATE TABLE capjobhistory (
	capjobhistory_id	INTEGER NOT NULL,
	capjob_id	INTEGER,
	capjobstate_id	INTEGER NOT NULL,
	capjobhistory_datebeg	TIMESTAMP NOT NULL,
	capjobhistory_dateend	TIMESTAMP,
	capjobhistory_errmsg	VARCHAR(2000),
	capjob_name	VARCHAR(255),
	capresource_id	INTEGER,
	CONSTRAINT capjobhistory_pk PRIMARY KEY (capjobhistory_id)
);
ALTER TABLE capjobhistory ADD
	CONSTRAINT capjobhistory_fk1 FOREIGN KEY (capjob_id) REFERENCES capjob;
ALTER TABLE capjobhistory ADD
	CONSTRAINT capjobhistory_fk2 FOREIGN KEY (capjobstate_id) REFERENCES capcode;
ALTER TABLE capjobhistory ADD
	CONSTRAINT capjobhistory_fk3 FOREIGN KEY (capresource_id) REFERENCES capresource;
CREATE INDEX capjobhistory_if1 ON capjobhistory (capjob_id);
CREATE INDEX capjobhistory_if2 ON capjobhistory (capjobstate_id);
CREATE INDEX capjobhistory_if3 ON capjobhistory (capresource_id);
COMMENT ON TABLE capjobhistory IS 'История выполнения заданий';
COMMENT ON COLUMN capjobhistory.capjobhistory_id IS 'История выполнения заданий ИД';
COMMENT ON COLUMN capjobhistory.capjob_id IS 'Задание ИД';
COMMENT ON COLUMN capjobhistory.capjobstate_id IS 'Состояние задания ИД';
COMMENT ON COLUMN capjobhistory.capjobhistory_datebeg IS 'Дата начала';
COMMENT ON COLUMN capjobhistory.capjobhistory_dateend IS 'Дата окончания';
COMMENT ON COLUMN capjobhistory.capjobhistory_errmsg IS 'Текст ошибки';
COMMENT ON COLUMN capjobhistory.capjob_name IS 'Наименование';
COMMENT ON COLUMN capjobhistory.capresource_id IS 'Ресурс ИД';
CREATE SEQUENCE capjobhistory_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capjobhistory_id_gen OWNED BY capjobhistory.capjobhistory_id;