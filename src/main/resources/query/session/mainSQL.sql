SELECT m0.*,
    pu.proguser_name,
    pu.proguser_status_id,
    cc.capcode_name proguser_status_display
FROM proguserauth m0
    INNER JOIN proguser pu on pu.proguser_id=m0.proguser_id
    INNER JOIN capcode cc on cc.capcode_id=pu.proguser_status_id
    /*FROM_PLACEHOLDER*/
WHERE 1=1 AND
    (
        :status = 0 OR
        (:status = 1 AND (m0.proguserauth_dateend is null OR m0.proguserauth_dateend > CURRENT_TIMESTAMP)) OR
        (:status = 2 AND m0.proguserauth_dateend is not null AND m0.proguserauth_dateend <= CURRENT_TIMESTAMP)
    ) AND
    (
        :proguserId = 0 OR m0.proguser_id=:proguserId
    )
    /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/