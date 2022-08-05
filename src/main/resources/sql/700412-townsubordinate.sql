/* Тип подчиненности города */
CREATE TABLE townsubordinate (
	townsubordinate_id	INTEGER NOT NULL,
	townsubordinate_name	VARCHAR(50) NOT NULL,
	CONSTRAINT townsubordinate_pk PRIMARY KEY (townsubordinate_id),
	CONSTRAINT townsubordinate_ak1 UNIQUE (townsubordinate_name)
);
COMMENT ON TABLE townsubordinate IS 'Тип подчиненности города';
COMMENT ON COLUMN townsubordinate.townsubordinate_id IS 'Тип подчиненности города_ИД';
COMMENT ON COLUMN townsubordinate.townsubordinate_name IS 'Наименование';
CREATE SEQUENCE townsubordinate_id_gen AS INTEGER START WITH 7 INCREMENT BY 1;
ALTER SEQUENCE townsubordinate_id_gen OWNED BY townsubordinate.townsubordinate_id;

INSERT INTO townsubordinate (townsubordinate_id,townsubordinate_name) VALUES
(1,'Без подчиненности'),
(2,'Столица региона'),
(3,'Регионального подчинения'),
(4,'Районный центр'),
(5,'Районного подчинения'),
(6,'Городского подчинения');
