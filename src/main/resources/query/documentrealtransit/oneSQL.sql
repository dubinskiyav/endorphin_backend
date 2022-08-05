SELECT *
FROM   documentrealtransit DRT
    INNER JOIN proguser P ON P.proguser_id = DRT.proguser_id
    INNER JOIN documenttransit DT ON DT.documenttransit_id = DRT.documenttransit_id /*FROM_PLACEHOLDER*/
WHERE  1 = 1 /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/