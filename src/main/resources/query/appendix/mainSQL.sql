SELECT m0.*,
    pu.proguser_name,
    cc.capclass_name
FROM appendix m0
    INNER JOIN docrealappendix dra ON m0.appendix_id = dra.appendix_id
    INNER JOIN proguser pu ON m0.proguser_id = pu.proguser_id
    INNER JOIN capclass cc ON m0.capclass_id = cc.capclass_id /*FROM_PLACEHOLDER*/
WHERE 1=1
    AND dra.documentreal_id = :documentRealId /*WHERE_PLACEHOLDER*/
/*ORDERBY_PLACEHOLDER*/