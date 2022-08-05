/* Значения константы */
CREATE TABLE constantvalue (
	constantvalue_id	INTEGER NOT NULL,
	constant_id	INTEGER NOT NULL,
	constantvalue_datebeg	DATE NOT NULL,
	constantvalue_display	VARCHAR(255) NOT NULL,
  constantvalue_integer INTEGER,
  constantvalue_boolean INTEGER,
  constantvalue_date    DATE,
  constantvalue_float	DOUBLE PRECISION,
  constantvalue_money	DECIMAL(16,2),
  constantvalue_string	VARCHAR(255),
	CONSTRAINT constantvalue_pk PRIMARY KEY (constantvalue_id),
	CONSTRAINT constantvalue_ak1 UNIQUE (constantvalue_datebeg, constant_id)
);
ALTER TABLE constantvalue ADD
	CONSTRAINT constantvalue_fk1 FOREIGN KEY (constant_id) REFERENCES constant;
CREATE INDEX constantvalue_if1 ON constantvalue (constant_id);
COMMENT ON TABLE constantvalue IS 'Значения константы';
COMMENT ON COLUMN constantvalue.constantvalue_id IS 'Значение константы ИД';
COMMENT ON COLUMN constantvalue.constant_id IS 'Ресурс ИД';
COMMENT ON COLUMN constantvalue.constantvalue_datebeg IS 'Дата';
COMMENT ON COLUMN constantvalue.constantvalue_display IS 'Значение представление';
COMMENT ON COLUMN constantvalue.constantvalue_integer IS 'Значение целое';
COMMENT ON COLUMN constantvalue.constantvalue_boolean IS 'Значение логическое';
COMMENT ON COLUMN constantvalue.constantvalue_date IS 'Значение дата';
COMMENT ON COLUMN constantvalue.constantvalue_float IS 'Значение вещественное';
COMMENT ON COLUMN constantvalue.constantvalue_money IS 'Значение денежное';
COMMENT ON COLUMN constantvalue.constantvalue_string IS 'Значение строковое';
CREATE SEQUENCE constantvalue_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE constantvalue_id_gen OWNED BY constantvalue.constantvalue_id;
