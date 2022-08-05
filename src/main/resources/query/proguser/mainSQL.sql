SELECT m0.*,
    PUC.proguserchannel_address,
    CC.capcode_name proguser_status_display
FROM proguser m0
    INNER JOIN capcode CC ON CC.capcode_id=m0.proguser_status_id
    LEFT OUTER JOIN proguserchannel PUC ON PUC.proguser_id = m0.proguser_id AND PUC.channelnotification_id = 9001
    LEFT OUTER JOIN proguserrole PUR ON m0.proguser_id = PUR.proguser_id
/*FROM_PLACEHOLDER*/
WHERE 1=1
    /*ROLE_FILTER*/ /*STATUS_FILTER*/
    /*WHERE_PLACEHOLDER*/
    /*ORDERBY_PLACEHOLDER*/