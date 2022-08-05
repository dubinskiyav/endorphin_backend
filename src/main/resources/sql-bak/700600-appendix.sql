CREATE TABLE appendix(
	appendix_id INTEGER NOT NULL,
	capclass_id INTEGER NOT NULL,
	proguser_id INTEGER NOT NULL,
	appendix_name VARCHAR(100) NOT NULL,
	appendix_number VARCHAR(50),
	appendix_sednumber VARCHAR(50),
	appendix_date TIMESTAMP,
	appendix_remark VARCHAR(255),
	appendix_latitude DOUBLE PRECISION,
	appendix_longitude DOUBLE PRECISION,
	appendix_data bytea,
	appendix_ext VARCHAR(10),
	appendix_mime VARCHAR(255),
	appendix_repositoryid VARCHAR(255),
	appendix_datemodify TIMESTAMP NOT NULL,
	documentreal_id INTEGER,
    CONSTRAINT appendix_pk PRIMARY KEY (appendix_id)
);
CREATE SEQUENCE appendix_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE appendix_id_gen OWNED BY appendix.appendix_id;

CREATE VIEW ft_appendix
AS
SELECT
	appendix_id,
	CAST(
		trim(appendix_name)||' '||coalesce(appendix_number,'')||' '||coalesce(appendix_sednumber,'')||' '||coalesce(to_char(appendix_date, 'DD.MM.YYYY'),'')||' '||coalesce(appendix_remark,'')||' '||coalesce(appendix_ext,'')||' '||coalesce(appendix_mime,'')||' '||coalesce(appendix_repositoryid,'')||' '||trim(to_char(appendix_datemodify, 'DD.MM.YYYY'))
		AS varchar(1043)
	) AS fulltext
FROM appendix;

CREATE TABLE docrealappendix (
	docrealappendix_id int4 NOT NULL,
	documentreal_id int4 NOT NULL,
	appendix_id int4 NOT NULL,
	CONSTRAINT docrealappendix_ak1 UNIQUE (documentreal_id, appendix_id),
	CONSTRAINT docrealappendix_pk PRIMARY KEY (docrealappendix_id)
);
CREATE SEQUENCE docrealappendix_id_gen AS INTEGER START WITH 10 INCREMENT BY 1;
ALTER SEQUENCE docrealappendix_id_gen OWNED BY appendix.appendix_id;

INSERT INTO docrealappendix VALUES (1, 101, 1);
INSERT INTO docrealappendix VALUES (2, 102, 1);
INSERT INTO docrealappendix VALUES (3, 102, 2);
INSERT INTO docrealappendix VALUES (4, 101, 3);

CREATE TABLE proguserappendixtype (
	proguserappendixtype_id int4 NOT NULL,
	proguser_id int4 NOT NULL,
	appendixtype_id int4 NOT NULL,
	sqlaction_id int4 NOT NULL,
	CONSTRAINT proguserappendixtype_ak1 UNIQUE (proguser_id, appendixtype_id, sqlaction_id),
	CONSTRAINT proguserappendixtype_pk PRIMARY KEY (proguserappendixtype_id)
);

INSERT INTO proguserappendixtype VALUES (1, 1, 211, 1);
INSERT INTO proguserappendixtype VALUES (2, 1, 211, 2);
INSERT INTO proguserappendixtype VALUES (3, 1, 1268, 1);
INSERT INTO proguserappendixtype VALUES (4, 1, 1268, 2);