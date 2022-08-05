SELECT m0.*,
    dr.*,
    dt.documenttransit_color,
    dt.documenttransit_name,
    cc.capclass_name
FROM testrequest m0
    inner join documentreal dr on dr.documentreal_id=m0.testrequest_id
    inner join capclass cc on cc.capclass_id=m0.capclass_id
    left outer join documenttransit dt on dr.documentreal_status=dt.documenttransit_id
    /*FROM_PLACEHOLDER*/
WHERE 1=1 /*WHERE_PLACEHOLDER*/