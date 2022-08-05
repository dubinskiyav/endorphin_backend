SELECT m0.*,
       t.town_name,
       /* capclass_id=79  Идентификационный номер налогоплательщика */
       (SELECT cc.companycode_code FROM companycode cc WHERE cc.capclass_id=79 AND cc.company_id=m0.company_id) company_inn,
       /* capclass_id=84  Код причины постановки на налоговый учет */
       (SELECT cc.companycode_code FROM companycode cc WHERE cc.capclass_id=84 AND cc.company_id=m0.company_id) company_kpp
FROM company m0
     INNER JOIN town t ON t.town_id=m0.town_id
    /*FROM_PLACEHOLDER*/
WHERE 1=1
/*WHERE_PLACEHOLDER*/