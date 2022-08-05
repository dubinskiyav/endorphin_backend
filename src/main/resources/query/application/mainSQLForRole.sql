SELECT m0.*,
    ar.applicationrole_id
FROM application m0
    LEFT OUTER JOIN applicationrole ar ON ar.application_id=m0.application_id AND ar.accessrole_id=:accessRoleId
    /*FROM_PLACEHOLDER*/
WHERE m0.application_type = :type /*WHERE_PLACEHOLDER*/
    /*ORDERBY_PLACEHOLDER*/