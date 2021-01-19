WITH area_vacancy AS (
  SELECT a.area_id,
         v.vacancy_id,
         v.creation_date
  FROM vacancy v
    CROSS JOIN area a
), area_vacancy_response AS (
  SELECT av.area_id,
         av.creation_date,
         min(r.creation_date) AS first_response
  FROM area_vacancy av
    LEFT JOIN response r ON r.area_id = av.area_id AND r.vacancy_id = av.vacancy_id
  GROUP BY av.area_id, av.vacancy_id, av.creation_date
)
SELECT avr.area_id,
       max(EXTRACT (day from avr.first_response::timestamp - avr.creation_date::timestamp) ) AS max_diff,
       min(EXTRACT (day from avr.first_response::timestamp - avr.creation_date::timestamp) ) AS min_diff
FROM area_vacancy_response avr
GROUP BY avr.area_id;

