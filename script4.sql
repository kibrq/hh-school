WITH compensation_list AS (
  SELECT CASE WHEN compensation_gross
              THEN compensation_from * 0.87
              ELSE compensation_from
         END AS comp_from,
         CASE WHEN compensation_gross
              THEN compensation_to * 0.87
              ELSE compensation_to
         END AS comp_to
   FROM vacancy
)
SELECT avg(c.comp_from)             AS avg_min_salary,
       avg(c.comp_to)               AS avg_max_salary,
       avg(c.comp_to - c.comp_from) AS avg_range_salary
FROM compensation_list c;
