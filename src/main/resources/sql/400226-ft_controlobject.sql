CREATE OR REPLACE VIEW ft_controlobject AS
SELECT controlobject_id,
       CONCAT(
       controlobject_name,                               ' ',
       controlobject_url
       ) AS fulltext
FROM   controlobject
;