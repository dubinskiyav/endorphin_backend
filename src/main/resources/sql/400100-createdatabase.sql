-- Создать пользователя SYSDBA средствами DBeaver
CREATE ROLE "SYSDBA" WITH
	LOGIN
	SUPERUSER
	CREATEDB
	CREATEROLE
	INHERIT
	REPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'masterkey';

CREATE DATABASE endorphin
    WITH 
    OWNER = "SYSDBA"
    TEMPLATE = template0
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE endorphin
    IS 'Базовые сущности системы Endorphin';

-- Обязательно переконнектиться к новой бд!!!!!!!!!!
-- Создание схемы
CREATE SCHEMA dav;
-- Устанавливаем путь к схеме
SET search_path TO dav,public; -- Работает не только для текущего сеанса
-- Проверяем
SHOW search_path;
ALTER ROLE "SYSDBA" SET search_path TO dav
