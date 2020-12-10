SELECT position_name, area_id, employer_name
FROM vacancy v
  INNER JOIN employer e ON e.employer_id = v.employer_id AND v.compensation_from  IS NULL AND
                                                             v.compensation_to    IS NULL AND
                                                             v.compensation_gross IS NULL
ORDER BY creation_date DESC
LIMIT 10;
