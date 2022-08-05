/* Оповещение */
CREATE TABLE notification (
	notification_id	INTEGER NOT NULL,
	proguser_id	INTEGER NOT NULL,
	channelnotification_id	INTEGER NOT NULL,
	eventnotification_id	INTEGER NOT NULL,
	notification_address	VARCHAR(125),
	notification_text	VARCHAR(2000) NOT NULL,
	notification_date	TIMESTAMP NOT NULL,
	notification_status	INTEGER NOT NULL,
	notification_datesend	TIMESTAMP,
	notification_subj	VARCHAR(255),
	notification_linkobj_id	INTEGER,
	notification_dateread	TIMESTAMP,
	notification_senderror	VARCHAR(255),
	notification_sendcounter int4 NOT NULL DEFAULT 0,
	CONSTRAINT notification_pk PRIMARY KEY (notification_id)
);
ALTER TABLE notification ADD
	CONSTRAINT notification_fk1 FOREIGN KEY (proguser_id) REFERENCES proguser;
ALTER TABLE notification ADD
	CONSTRAINT notification_fk2 FOREIGN KEY (channelnotification_id) REFERENCES capcode;
ALTER TABLE notification ADD
	CONSTRAINT notification_fk3 FOREIGN KEY (eventnotification_id) REFERENCES capcode;
CREATE INDEX notification_if1 ON notification (proguser_id);
CREATE INDEX notification_if2 ON notification (channelnotification_id);
CREATE INDEX notification_if3 ON notification (eventnotification_id);
COMMENT ON TABLE notification IS 'Оповещение';
COMMENT ON COLUMN notification.notification_id IS 'Опопвещение ИД';
COMMENT ON COLUMN notification.proguser_id IS 'Пользователь ИД';
COMMENT ON COLUMN notification.channelnotification_id IS 'Канал оповещения ИД';
COMMENT ON COLUMN notification.eventnotification_id IS 'Событие оповещения ИД';
COMMENT ON COLUMN notification.notification_address IS 'Адрес отправки';
COMMENT ON COLUMN notification.notification_text IS 'Текст сообщения';
COMMENT ON COLUMN notification.notification_date IS 'Дата оповещения';
COMMENT ON COLUMN notification.notification_status IS 'Статус';
COMMENT ON COLUMN notification.notification_datesend IS 'Дата отправки';
COMMENT ON COLUMN notification.notification_subj IS 'Тема письма';
COMMENT ON COLUMN notification.notification_linkobj_id IS 'Ссылка на связанную сущность';
COMMENT ON COLUMN notification.notification_dateread IS 'Дата прочтения';
COMMENT ON COLUMN notification.notification_senderror IS 'Ошибка отправки';
COMMENT ON COLUMN notification.notification_sendcounter IS 'Счетчик отправлений';
CREATE SEQUENCE notification_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE notification_id_gen OWNED BY notification.notification_id;