CREATE OR REPLACE VIEW ft_application AS
SELECT application_id,
       CONCAT(
       application_code,                               ' ',
       application_name
       ) AS fulltext
FROM   application;

