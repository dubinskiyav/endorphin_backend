SELECT m0.*,
    u.*
FROM document m0
    LEFT OUTER JOIN uniquetypedocument ud ON ud.document_id=m0.document_id
    LEFT OUTER JOIN uniquetype u ON u.uniquetype_id=ud.uniquetype_id
    /*FROM_PLACEHOLDER*/
WHERE 1=1 /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/