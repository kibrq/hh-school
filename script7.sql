WITH vacancy_response AS (
  SELECT v.vacancy_id,
       v.creation_date AS vacancy_date,
       r.response_id,
       r.area_id,
       r.creation_date AS response_date
  FROM vacancy v
    LEFT JOIN response r ON v.vacancy_id = r.vacancy_id
), area_delta AS (
  SELECT area_id,
         EXTRACT (day from response_date::timestamp - vacancy_date::timestamp) AS delta
  FROM (SELECT vacancy_id, vacancy_date, response_id, area_id, response_date,
             ROW_NUMBER() OVER (PARTITION BY vacancy_id ORDER BY response_date) rank
      FROM vacancy_response) vr
  WHERE vr.rank = 1
)
SELECT a.area_id,
       MIN(delta) AS min_diff,
       MAX(delta) AS max_diff
FROM area a
  LEFT JOIN area_delta ad ON a.area_id = ad.area_id
GROUP BY a.area_id
ORDER BY a.area_id


