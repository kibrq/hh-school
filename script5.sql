SELECT e.employer_name
FROM vacancy v
  LEFT JOIN response r ON v.vacancy_id = r.vacancy_id
  INNER JOIN employer e ON e.employer_id = v.employer_id
GROUP BY e.employer_name
ORDER BY count(response_id) DESC, e.employer_name
LIMIT 5;
