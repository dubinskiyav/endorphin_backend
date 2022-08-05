CREATE TABLE documentappendixrole(
	documentappendixrole_id INTEGER NOT NULL,
	accessrole_id INTEGER NOT NULL,
	appendixtype_id INTEGER NOT NULL,
	document_id INTEGER NOT NULL,
	sqlaction_id INTEGER NOT NULL,
    CONSTRAINT documentappendixrole_pk PRIMARY KEY (documentappendixrole_id)
);
CREATE SEQUENCE documentappendixrole_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE documentappendixrole_id_gen OWNED BY documentappendixrole.documentappendixrole_id;
ALTER TABLE documentappendixrole ADD UNIQUE (accessrole_id,appendixtype_id,document_id,sqlaction_id);


