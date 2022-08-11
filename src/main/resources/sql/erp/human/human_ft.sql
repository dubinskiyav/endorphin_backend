CREATE OR REPLACE VIEW ft_human AS
SELECT H.human_id,
       CONCAT(
       H.human_surname,                               ' ',
       H.human_name,                               ' ',
       H.human_patronymic,                               ' ',
       H.human_inn,                               ' ',
       H.human_note,                               ' ',
       G.humangender_abbr,                               ' ',
       G.humangender_name
       ) AS fulltext
FROM   human H
       INNER JOIN humangender G ON G.humangender_id = H.humangender_id
;
