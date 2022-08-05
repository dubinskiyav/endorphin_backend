CREATE OR REPLACE VIEW ft_accessrole AS
SELECT accessrole_id,
       CONCAT(
       accessrole_name,                               ' ',
       accessrole_note
       ) AS fulltext,
       accessrole_visible
FROM   accessrole;

