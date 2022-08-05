SELECT m0.*
FROM capclass m0 /*FROM_PLACEHOLDER*/
WHERE 1=1 AND (:capClassTypeId = 0 or m0.capclasstype_id=:capClassTypeId)
    /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/