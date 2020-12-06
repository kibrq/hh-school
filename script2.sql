INSERT INTO employer(employer_name)
VALUES ('company1'),
       ('company2'),
       ('company3'),
       ('company4'),
       ('company5'),
       ('company6'),
       ('company7'),
       ('company8'),
       ('company9'),
       ('company10');

INSERT INTO cv(cv_text)
VALUES ('cv1'),
       ('cv2'),
       ('cv3'),
       ('cv4'),
       ('cv5'),
       ('cv6'),
       ('cv7'),
       ('cv8'),
       ('cv9'),
       ('cv10'),
       ('cv11');

INSERT INTO vacancy(employer_id, position_name, creation_date, area_id, compensation_from, compensation_to, compensation_gross)
VALUES (1, 'pos1', '2020-01-01', 78, 10, 20, false),
       (1, 'pos2', '2020-01-01', 77, 0, 30, true),
       (3, 'pos3', '2020-04-22', 77, 20, 30, false),
       (1, 'pos4', '2020-08-05', 78, null, 50, true),
       (1, 'pos5', '2020-05-01', 77, 20, 30, false),
       (3, 'pos6', '2019-03-08', 77, null, null, null),
       (9, 'pos7', '2021-02-28', 78, 20, null, false),
       (6, 'pos8', '2030-01-01', 79, null, null, null),
       (8, 'pos9', '1900-04-04', 79, 20, 30, false),
       (10,'pos10', '2001-03-05', 79, 0, null, false),
       (2,'pos11', '1876-09-09', 79, 20, 30, true),
       (2,'pos12', '1876-09-09', 79, 20, 30, true);

INSERT INTO response(vacancy_id, cv_id, creation_date)
VALUES (1, 2, '2020-03-05'),
       (1, 5, '2021-04-07'),
       (2, 5, '2020-11-11'),
       (3, 1, '2020-06-07'),
       (4, 8, '2020-09-10'),
       (2, 10, '2020-12-01'),
       (8, 8, '2030-02-02'),
       (2, 5, '2030-01-01'),
       (3, 10, '2020-05-01'),
       (7, 2, '2022-02-02'),
       (8, 2, '2032-02-02');
