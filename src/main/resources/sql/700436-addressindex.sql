/* Индекс Адреса */
CREATE TABLE addressindex (
	addresss_id int4 NOT NULL,
	addressindex_text varchar(255) NOT NULL,
	CONSTRAINT addressindex_pk PRIMARY KEY (addresss_id),
	CONSTRAINT addressindex_fk1 FOREIGN KEY (addresss_id) REFERENCES address(address_id)
);
CREATE INDEX addressindextext ON addressindex USING btree (addressindex_text);

COMMENT ON TABLE addressindex IS 'Адрес';
COMMENT ON COLUMN addressindex.addresss_id IS 'Адрес ИД';
COMMENT ON COLUMN addressindex.addressindex_text IS 'Индекс';
