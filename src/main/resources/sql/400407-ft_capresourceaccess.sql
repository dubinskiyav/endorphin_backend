CREATE OR REPLACE VIEW ft_capresourceaccess AS
SELECT R.capresource_id,
       CONCAT(
       R.capresource_name,                                       ' ',
       R.capresource_code,                                       ' ',
       T.resourcetype_name
       ) AS fulltext
FROM   capresource R
       INNER JOIN resourcetype T ON T.resourcetype_id = R.resourcetype_id
       INNER JOIN proguser PU ON PU.proguser_id = R.proguser_id
;
