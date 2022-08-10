CREATE OR REPLACE VIEW ft_accessrolerole AS
SELECT ARR.accessrolerole_id,
       CONCAT(
       ARP.accessrole_name,                               ' ',
       ARP.accessrole_note,                               ' ',
       ARC.accessrole_name,                               ' ',
       ARC.accessrole_note
       ) AS fulltext
FROM   accessrolerole ARR,
       accessrole ARP,
       accessrole ARC
WHERE  ARP.accessrole_id = ARR.accessrole_id_parent
  AND  ARC.accessrole_id = ARR.accessrole_id_child
;