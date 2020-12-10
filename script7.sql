SELECT v.area_id,
       max(EXTRACT (day from r.creation_date::timestamp - v.creation_date::timestamp) ) AS max_diff,
       min(EXTRACT (day from r.creation_date::timestamp - v.creation_date::timestamp) ) AS min_diff
FROM vacancy v
  LEFT JOIN response r ON r.vacancy_id = v.vacancy_id
GROUP BY v.area_id;
