SELECT m0.*,
       cor.controlobjectrole_id
FROM   controlobject m0
       LEFT OUTER JOIN controlobjectrole cor ON
              cor.controlobject_id = m0.controlobject_id
          AND cor.sqlaction_id = :sqlactionId
          AND cor.accessrole_id = :accessRoleId
       /*FROM_PLACEHOLDER*/
WHERE 1=1 /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/