SELECT *
FROM   documentrealtransit DRT
    /*FROM_PLACEHOLDER*/
WHERE  DRT.documentreal_id = :documentrealId
    AND (DRT.documenttransit_id = :documenttransitId OR 0 = :documenttransitId)
    AND (DRT.proguser_id = :proguserId OR 0 = :proguserId)
    AND (DRT.documentrealtransit_flag = :documentrealtransitFlag OR -123 = :documentrealtransitFlag)
    AND (DRT.documentrealtransit_flag = 0 OR 0 = :showOnlySetStatus)
    /*WHERE_PLACEHOLDER*/