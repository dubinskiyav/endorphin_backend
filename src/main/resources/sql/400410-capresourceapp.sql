/* Приложение ресурса */
CREATE TABLE capresourceapp (
	capresourceapp_id	INTEGER NOT NULL,
	application_id	INTEGER NOT NULL,
	capresource_id	INTEGER NOT NULL,
	CONSTRAINT capresourceapp_pk PRIMARY KEY (capresourceapp_id),
	CONSTRAINT capresourceapp_ak1 UNIQUE (capresource_id, application_id)
);
ALTER TABLE capresourceapp ADD
	CONSTRAINT capresourceapp_fk1 FOREIGN KEY (capresource_id) REFERENCES capresource;
ALTER TABLE capresourceapp ADD
	CONSTRAINT capresourceapp_fk2 FOREIGN KEY (application_id) REFERENCES application;
CREATE INDEX capresourceapp_if1 ON capresourceapp (capresource_id);
CREATE INDEX capresourceapp_if2 ON capresourceapp (application_id);
COMMENT ON TABLE capresourceapp IS 'Приложение ресурса';
COMMENT ON COLUMN capresourceapp.capresourceapp_id IS 'Приложение ресурса ИД';
COMMENT ON COLUMN capresourceapp.application_id IS 'Приложение ИД';
COMMENT ON COLUMN capresourceapp.capresource_id IS 'Ресурс ИД';
CREATE SEQUENCE capresourceapp_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE capresourceapp_id_gen OWNED BY capresourceapp.capresourceapp_id;