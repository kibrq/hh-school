WITH emp_list AS (
  SELECT e.employer_name,
         count(v.vacancy_id) AS cnt
  FROM employer e
    LEFT JOIN vacancy v ON v.employer_id = e.employer_id
  GROUP BY e.employer_name
)
SELECT percentile_cont(0.5) WITHIN GROUP (ORDER BY emp_list.cnt)
FROM emp_list

