/* Канал оповещения пользователя */
CREATE TABLE proguserchannel (
	proguserchannel_id	INTEGER NOT NULL,
	proguser_id	INTEGER NOT NULL,
	channelnotification_id	INTEGER NOT NULL,
	proguserchannel_address	VARCHAR(125),
	CONSTRAINT proguserchannel_pk PRIMARY KEY (proguserchannel_id),
	CONSTRAINT proguserchannel_ak1 UNIQUE (proguser_id, channelnotification_id)
);
ALTER TABLE proguserchannel ADD
	CONSTRAINT proguserchannel_fk1 FOREIGN KEY (channelnotification_id) REFERENCES capcode;
ALTER TABLE proguserchannel ADD
	CONSTRAINT proguserchannel_fk2 FOREIGN KEY (proguser_id) REFERENCES proguser;
CREATE INDEX proguserchannel_if1 ON proguserchannel (channelnotification_id);
CREATE INDEX proguserchannel_if2 ON proguserchannel (proguser_id);
COMMENT ON TABLE proguserchannel IS 'Канал оповещения пользователя';
COMMENT ON COLUMN proguserchannel.proguserchannel_id IS 'Канал оповещения пользователя';
COMMENT ON COLUMN proguserchannel.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN proguserchannel.channelnotification_id IS 'Канал оповещения ИД';
COMMENT ON COLUMN proguserchannel.proguserchannel_address IS 'Адресат';
CREATE SEQUENCE proguserchannel_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE proguserchannel_id_gen OWNED BY proguserchannel.proguserchannel_id;