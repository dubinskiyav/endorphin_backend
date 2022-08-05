SELECT m0.*,
       t.town_name,
       (SELECT cc.companycode_kpp FROM companycode cc WHERE cc.company_id=m0.company_id) company_kpp
FROM company m0
     INNER JOIN town t ON t.town_id=m0.town_id
    /*FROM_PLACEHOLDER*/
WHERE 1=1
/*WHERE_PLACEHOLDER*/