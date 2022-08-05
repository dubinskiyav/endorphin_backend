SELECT m0.*,
    p.subject_name parent_name
FROM subject m0
    INNER JOIN subject p ON p.subject_id=m0.parent_id /*FROM_PLACEHOLDER*/
WHERE m0.subjecttype_id>1 and :date BETWEEN m0.subject_datebeg AND m0.subject_dateend /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/