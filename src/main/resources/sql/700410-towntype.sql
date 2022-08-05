/* Тип населенного пункта */
CREATE TABLE towntype (
	towntype_id	INTEGER NOT NULL,
	towntype_name	VARCHAR(50) NOT NULL,
	towntype_code	VARCHAR(20) NOT NULL,
	CONSTRAINT towntype_pk PRIMARY KEY (towntype_id),
	CONSTRAINT towntype_ak1 UNIQUE (towntype_name),
	CONSTRAINT towntype_ak2 UNIQUE (towntype_code)
);
COMMENT ON TABLE towntype IS 'Тип населенного пункта';
COMMENT ON COLUMN towntype.towntype_id IS 'Тип населенного пункта ИД';
COMMENT ON COLUMN towntype.towntype_name IS 'Наименование';
COMMENT ON COLUMN towntype.towntype_code IS 'Код';
CREATE SEQUENCE towntype_id_gen AS INTEGER START WITH 100000 INCREMENT BY 1;
ALTER SEQUENCE towntype_id_gen OWNED BY towntype.towntype_id;

INSERT INTO towntype (towntype_id,towntype_name,towntype_code) VALUES
(1,'ГОРОД','Г'),
(2,'СЕЛО','С'),
(3,'ДЕРЕВНЯ','Д'),
(4,'АУЛ','АУЛ'),
(126,'ВОЛОСТЬ','ВОЛОСТЬ'),
(127,'ДАЧНЫЙ ПОСЕЛОК','ДП'),
(128,'КУРОРТНЫЙ ПОСЕЛОК','КП'),
(129,'ПОЧТОВОЕ ОТДЕЛЕНИЕ','П/О'),
(130,'ПОСЕЛОК ГОРОДСКОГО ТИПА','ПГТ'),
(131,'РАБОЧИЙ ПОСЕЛОК','РП');

INSERT INTO towntype (towntype_id,towntype_name,towntype_code) VALUES
(132,'СЕЛЬСКАЯ АДМИНИСТРАЦИЯ','С/А'),
(133,'СЕЛЬСКОЕ МУНИЦИПАЛЬНОЕ ОБРАЗО','С/МО'),
(134,'СЕЛЬСКИЙ ОКРУГ','С/О'),
(135,'СЕЛЬСКОЕ ПОСЕЛЕНИЕ','С/П'),
(136,'СЕЛЬСОВЕТ','С/С'),
(137,'ТЕРРИТОРИЯ','ТЕР'),
(139,'АРБАН','АРБАН'),
(141,'ВЫСЕЛКИ(ОК)','ВЫСЕЛ'),
(142,'ГОРОДОК','ГОРОДОК'),
(144,'ЖЕЛЕЗНОДОРОЖНАЯ БУДКА','Ж/Д Б-КА');

INSERT INTO towntype (towntype_id,towntype_name,towntype_code) VALUES
(145,'ЖЕЛЕЗНОДОРОЖНАЯ КАЗАРМА','Ж/Д_КАЗАРМ'),
(146,'Ж/Д ОСТАНОВ. (ОБГОННЫЙ) ПУНКТ','Ж/Д_ОП'),
(147,'ЖЕЛЕЗНОДОРОЖНАЯ ПЛАТФОРМА','Ж/Д_ПЛАТФ'),
(148,'ЖЕЛЕЗНОДОРОЖНЫЙ ПОСТ','Ж/Д_ПОСТ'),
(149,'ЖЕЛЕЗНОДОРОЖНЫЙ РАЗЪЕЗД','Ж/Д_РЗД'),
(150,'ЖЕЛЕЗНОДОРОЖНАЯ СТАНЦИЯ','Ж/Д_СТ'),
(151,'ЗАИМКА','ЗАИМКА'),
(152,'КАЗАРМА','КАЗАРМА'),
(153,'КВАРТАЛ','КВ-Л'),
(154,'КОРДОН','КОРДОН');

INSERT INTO towntype (towntype_id,towntype_name,towntype_code) VALUES
(156,'ЛЕСПРОМХОЗ','ЛПХ'),
(157,'МЕСТЕЧКО','М'),
(158,'МИКРОРАЙОН','МКР'),
(159,'НАСЕЛЕННЫЙ ПУНКТ','НП'),
(160,'ОСТРОВ','ОСТРОВ'),
(161,'ПОСЕЛОК','П'),
(163,'ПЛАНИРОВОЧНЫЙ РАЙОН','П/Р'),
(164,'ПОСЕЛОК И(ПРИ) СТАНЦИЯ(И)','П/СТ'),
(166,'ПОГОСТ','ПОГОСТ'),
(167,'ПОЧИНОК','ПОЧИНОК');

INSERT INTO towntype (towntype_id,towntype_name,towntype_code) VALUES
(168,'ПРОМЫШЛЕННАЯ ЗОНА','ПРОМЗОНА'),
(169,'РАЗЪЕЗД','РЗД'),
(171,'СЛОБОДА','СЛ'),
(172,'САДОВОЕ НЕКОМ-Е ТОВАРИЩЕСТВО','СНТ'),
(173,'СТАНЦИЯ','СТ'),
(174,'СТАНИЦА','СТ-ЦА'),
(176,'УЛУС','У'),
(177,'ХУТОР','Х'),
(178,'АВТОДОРОГА','АВТОДОРОГА'),
(179,'МАССИВ','МАССИВ');

INSERT INTO towntype (towntype_id,towntype_name,towntype_code) VALUES
(180,'ЖИЛОЙ РАЙОН','ЖИЛРАЙОН'),
(181,'ЖИЛАЯ ЗОНА','ЖИЛЗОНА'),
(184,'МЕСТНОСТЬ','МЕСТНОСТЬ'),
(205,'ГОРОДСКОЙ ПОСЕЛОК','ГП'),
(138,'ААЛ','ААЛ');
