CREATE TABLE subject (
	subject_id INTEGER NOT NULL,
	cluster_id INTEGER NOT NULL,
	subjecttype_id INTEGER NOT NULL,
	subject_name varchar(255) NOT NULL,
	subject_datebeg timestamp NOT NULL,
	subject_dateend timestamp NOT NULL,
	subject_code varchar(30) NOT NULL,
	subject_description varchar(2000) NULL,
	subject_status INTEGER NOT NULL,
	proguser_id INTEGER NOT NULL,
	subject_datecreate timestamp NOT NULL,
	lastproguser_id INTEGER NOT NULL,
	subject_datemodify timestamp NOT NULL,
	subject_remark varchar(255) NULL,
	rootsubject_id INTEGER NOT NULL,
	parent_id INTEGER NOT NULL,
	CONSTRAINT subject_pk PRIMARY KEY (subject_id),
	CONSTRAINT subject_fk1 FOREIGN KEY (cluster_id) REFERENCES clusterr(cluster_id),
	CONSTRAINT subject_fk2 FOREIGN KEY (subjecttype_id) REFERENCES subjecttype(subjecttype_id),
	CONSTRAINT subject_fk3 FOREIGN KEY (proguser_id) REFERENCES proguser(proguser_id),
	CONSTRAINT subject_fk4 FOREIGN KEY (lastproguser_id) REFERENCES proguser(proguser_id),
	CONSTRAINT subject_fk5 FOREIGN KEY (rootsubject_id) REFERENCES subject(subject_id),
	CONSTRAINT subject_fk6 FOREIGN KEY (parent_id) REFERENCES subject(subject_id)
);
CREATE INDEX subject_if1 ON subject USING btree (cluster_id);
CREATE INDEX subject_if2 ON subject USING btree (subjecttype_id);
CREATE INDEX subject_if3 ON subject USING btree (proguser_id);
CREATE INDEX subject_if4 ON subject USING btree (lastproguser_id);
CREATE INDEX subject_if5 ON subject USING btree (rootsubject_id);
COMMENT ON TABLE subject IS 'Объект аналитического учета';

COMMENT ON COLUMN subject.subject_id IS 'Объект аналитического учета ИД';
COMMENT ON COLUMN subject.subject_name IS 'Наименование';
COMMENT ON COLUMN subject.subject_code IS 'Код сортировки';

CREATE SEQUENCE subject_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE subject_id_gen OWNED BY subject.subject_id;

INSERT INTO subject VALUES (-1,-1,1,'Корневой уровень','01.01.1900','01.01.2099','',NULL,0,1,now(),1,now(),null,-1,-1);
INSERT INTO subject VALUES (-2,-1,2,'Не задан',        '01.01.1900','01.01.2099','',NULL,0,1,now(),1,now(),null,-2,-1);

CREATE OR REPLACE VIEW ft_subject AS
SELECT subject_id,
       CONCAT(
       subject_name,                               ' ',
       TO_CHAR(subject_datebeg, 'DD.MM.YYYY'),     ' ',
       TO_CHAR(subject_dateend, 'DD.MM.YYYY'),     ' ',
       subject_code,                               ' ',
       subject_description,                        ' ',
       TO_CHAR(subject_datemodify, 'DD.MM.YYYY')
       ) AS fulltext
FROM   subject;