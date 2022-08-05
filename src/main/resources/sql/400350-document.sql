/* Тип документа */
CREATE TABLE document (
	document_id	INTEGER NOT NULL,
	document_name	VARCHAR(50) NOT NULL,
	document_shortname	VARCHAR(10),
	document_historyflag	INTEGER NOT NULL,
	document_lockflag	INTEGER NOT NULL,
	CONSTRAINT document_pk PRIMARY KEY (document_id),
	CONSTRAINT document_ak1 UNIQUE (document_name)
);
COMMENT ON TABLE document IS 'Тип документа';
COMMENT ON COLUMN document.document_id IS 'Тип документа ИД';
COMMENT ON COLUMN document.document_name IS 'Наименование';
COMMENT ON COLUMN document.document_shortname IS 'Краткое наименование';
COMMENT ON COLUMN document.document_historyflag IS 'Флаг истории';
COMMENT ON COLUMN document.document_lockflag IS 'Флаг блокировки';
CREATE SEQUENCE document_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE document_id_gen OWNED BY document.document_id;
