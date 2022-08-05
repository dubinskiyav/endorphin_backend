CREATE OR REPLACE VIEW ft_proguser AS
SELECT PU.proguser_id,
       CONCAT(
       proguser_name,                               ' ',
       proguser_fullname,                           ' ',
       proguserchannel_address
       ) AS fulltext
FROM   proguser PU
       LEFT JOIN proguserchannel PUC ON PUC.proguser_id = PU.proguser_id AND PUC.channelnotification_id = 9001 /*email*/
;
