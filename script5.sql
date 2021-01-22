WITH vacancy_count AS (
  SELECT v.employer_id        AS id,
         COUNT(r.response_id) AS cnt
  FROM vacancy v
    LEFT JOIN response r ON v.vacancy_id = r.vacancy_id
  GROUP BY v.vacancy_id
)
SELECT e.employer_name
FROM employer e
  LEFT JOIN vacancy_count vc ON vc.id = e.employer_id
GROUP BY e.employer_id
ORDER BY MAX(vc.cnt) DESC NULLS LAST
LIMIT 5;
