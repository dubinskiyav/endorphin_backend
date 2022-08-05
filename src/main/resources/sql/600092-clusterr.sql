CREATE TABLE clusterr (
	cluster_id INTEGER NOT NULL,
	CONSTRAINT clusterr_pk PRIMARY KEY (cluster_id)
);
COMMENT ON TABLE clusterr IS 'Уровень аналитики';
COMMENT ON COLUMN clusterr.cluster_id IS 'Уровень аналитики ИД';

CREATE SEQUENCE cluster_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE cluster_id_gen OWNED BY clusterr.cluster_id;

INSERT INTO clusterr VALUES (-1);

