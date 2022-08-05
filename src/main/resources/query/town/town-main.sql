SELECT *,
    tt.towntype_name,
    tt.towntype_code,
    ts.townsubordinate_name,
    c.country_name
FROM town m0
    INNER JOIN towntype tt ON m0.towntype_id = tt.towntype_id
    INNER JOIN country c ON m0.country_id = c.country_id
    INNER JOIN townsubordinate ts ON m0.townsubordinate_id = ts.townsubordinate_id /*FROM_PLACEHOLDER*/
WHERE 1=1 /*WHERE_PLACEHOLDER*/ 
/*ORDERBY_PLACEHOLDER*/
