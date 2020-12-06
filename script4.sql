SELECT avg(compensation_from)                   AS avg_min_salary,
       avg(compensation_to)                     AS avg_max_salary,
       avg(compensation_to - compensation_from) AS avg_range_salary
FROM vacancy WHERE compensation_gross
