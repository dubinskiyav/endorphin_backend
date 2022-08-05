SELECT *
FROM   documentrealtransit DRT
    INNER JOIN proguser P ON P.proguser_id = DRT.proguser_id
    INNER JOIN documenttransit DT ON DT.documenttransit_id = DRT.documenttransit_id /*FROM_PLACEHOLDER*/
WHERE  DRT.documentreal_id = :documentrealId
    AND  (DRT.documenttransit_id = :documenttransitId OR 0 = :documenttransitId)
    AND  (DRT.proguser_id = :proguserId OR 0 = :proguserId)
    AND  (DRT.documentrealtransit_flag = :documentrealtransitFlag OR -123 = :documentrealtransitFlag)
    AND  (DRT.documentrealtransit_flag = 0 OR 0 = :showOnlySetStatus)
    /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/