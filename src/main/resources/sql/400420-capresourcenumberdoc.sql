/* Документ ресурса */
CREATE TABLE capresourcenumberdoc (
	capresourcenumberdoc_id	INTEGER NOT NULL,
	capresource_id	INTEGER NOT NULL,
	document_id	INTEGER NOT NULL,
	CONSTRAINT capresourcenumberdoc_pk PRIMARY KEY (capresourcenumberdoc_id),
	CONSTRAINT capresourcenumberdoc_ak1 UNIQUE (document_id, capresource_id)
);
ALTER TABLE capresourcenumberdoc ADD
	CONSTRAINT capresourcenumberdoc_fk1 FOREIGN KEY (document_id) REFERENCES document;
ALTER TABLE capresourcenumberdoc ADD
	CONSTRAINT capresourcenumberdoc_fk2 FOREIGN KEY (capresource_id) REFERENCES capresource;
CREATE INDEX capresourcenumberdoc_if1 ON capresourcenumberdoc (document_id);
CREATE INDEX capresourcenumberdoc_if2 ON capresourcenumberdoc (capresource_id);
COMMENT ON TABLE capresourcenumberdoc IS 'Документ ресурса';
COMMENT ON COLUMN capresourcenumberdoc.capresourcenumberdoc_id IS 'Документ ресурса_ИД';
COMMENT ON COLUMN capresourcenumberdoc.capresource_id IS 'Ресурс ИД';
COMMENT ON COLUMN capresourcenumberdoc.document_id IS 'Тип документа ИД';
CREATE SEQUENCE capresourcenumberdoc_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capresourcenumberdoc_id_gen OWNED BY capresourcenumberdoc.capresourcenumberdoc_id;