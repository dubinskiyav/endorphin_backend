SELECT
    m0.addresss_id address_id,
    m0.addresss_building address_building,
    m0.addresss_korpus address_korpus,
    m0.addresss_flat address_flat,
    s.street_id street_id,
    s.street_name street_name,
    t.town_id,
    t.town_name,
    c.country_id,
    c.country_name,
    trim(c.country_name||', '||t.town_name||', '||s.street_name||' '||coalesce(m0.addresss_building,'')||coalesce(' корп. '||m0.addresss_korpus,'')||coalesce(' - '||m0.addresss_flat,'')) as address_text
FROM address m0
    INNER JOIN street s ON s.street_id=m0.street_id
    INNER JOIN town t ON t.town_id=s.town_id
    INNER JOIN country c ON c.country_id=t.country_id
    /*FROM_PLACEHOLDER*/
WHERE m0.addresss_id=:addressId