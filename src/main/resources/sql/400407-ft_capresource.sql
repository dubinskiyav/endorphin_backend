CREATE OR REPLACE VIEW ft_capresource AS
SELECT R.capresource_id,
       CONCAT(
       R.capresource_name,                                       ' ',
       R.capresource_code,                                       ' ',
       TO_CHAR(R.capresource_lastmodify, 'DD.MM.YYYY HH24:MI'),  ' ',
       PU.proguser_name
       ) AS fulltext
FROM   capresource R
       INNER JOIN resourcetype T ON T.resourcetype_id = R.resourcetype_id
       INNER JOIN proguser PU ON PU.proguser_id = R.proguser_id
;