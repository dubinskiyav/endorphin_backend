SELECT m0.*,
    rt.resourcetype_name,
    r.capresourcerole_id
FROM capresource m0 /*FROM_PLACEHOLDER*/
    INNER JOIN resourcetype rt ON rt.resourcetype_id= m0.resourcetype_id
    LEFT OUTER JOIN capresourcerole r ON r.capresource_id=m0.capresource_id AND r.capresourcerole_restrictflag = 0 AND r.accessrole_id=:accessRoleId
WHERE 1=1
    AND (:resourceTypeId = 0 or rt.resourcetype_id=:resourceTypeId)
    AND m0.resourcetrantype_id=:type
    /*WHERE_PLACEHOLDER*/
    /*ORDERBY_PLACEHOLDER*/