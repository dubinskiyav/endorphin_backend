SELECT * FROM (
SELECT ARR.accessrolerole_id,
       ARR.accessrole_id_parent,
       ARP.accessrole_name accessrole_name_parent,
       ARP.accessrole_note accessrole_note_parent,
       ARP.accessrole_visible accessrole_visible_parent,
       ARR.accessrole_id_child,
       ARC.accessrole_name accessrole_name_child,
       ARC.accessrole_note accessrole_note_child,
       ARC.accessrole_visible accessrole_visible_child
FROM   accessrolerole ARR,
       accessrole ARP,
       accessrole ARC
WHERE  ARP.accessrole_id = ARR.accessrole_id_parent
  AND  ARC.accessrole_id = ARR.accessrole_id_child
) T
       /*FullTextJoin*/
       /*FROM_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/
