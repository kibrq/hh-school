SELECT position_name, area_id, employer_name
FROM vacancy v
  INNER JOIN employer e ON e.employer_id = v.employer_id and v.compensation_from  is null and
                                                             v.compensation_to    is null and
                                                             v.compensation_gross is null
ORDER BY creation_date DESC
LIMIT 10;
