CREATE TABLE subcluster (
	subject_id INTEGER NOT NULL,
	cluster_id INTEGER NOT NULL,
	CONSTRAINT subcluster_ak1 UNIQUE (cluster_id),
	CONSTRAINT subcluster_pk PRIMARY KEY (subject_id)
);
ALTER TABLE subcluster ADD CONSTRAINT subcluster_fk1 FOREIGN KEY (subject_id) REFERENCES subject(subject_id);
ALTER TABLE subcluster ADD CONSTRAINT subcluster_fk2 FOREIGN KEY (cluster_id) REFERENCES clusterr(cluster_id);
CREATE INDEX subcluster_if1 ON subcluster USING btree (subject_id);
CREATE INDEX subcluster_if2 ON subcluster USING btree (cluster_id);

COMMENT ON TABLE subcluster IS 'Суб кластер Объект аналитического учета';

COMMENT ON COLUMN subcluster.subject_id IS 'Объект ИД';
COMMENT ON COLUMN subcluster.cluster_id IS 'Кластер ИД';

INSERT INTO subcluster VALUES (-1,-1);
