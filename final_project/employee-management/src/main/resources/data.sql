
INSERT INTO "department"
VALUES ('ТОП', 'Топ-менедмент', 'Руководители компании', null),
       ('ОУП', 'Отдел управления персоналом', '', null),
       ('ОВР', 'Отдел внутренней разработки', '', null),
       ('БУХ', 'Бухгалтерия', '', null),
       ('АУД', 'Направление архитектуры и управления данными', '', null),
       ('МО', 'Отдел методологии', '', null),
       ('ОПП', 'Отдел поддержки пользователей', '', null);


INSERT INTO "office"
VALUES (1, 'г. Москва, Краснопресненская Набережная, д. 12, 9 подъезд', 150, 'Главный офис компании'),
       (2, 'г. г. Москва, Краснопресненская Набережная, д. 12, 7 подъезд', 50, 'Внутренняя разработка');


INSERT INTO "personal_info"
VALUES (1, 'Никишина Вероника', '1954-01-08', '2000-02-23', true),
       (2, 'Тарасова Софья', '1979-04-13', '2020-01-13', false),
       (3, 'Кравцов Илья', '1965-07-18', '2012-12-03', true),
       (4, 'Никитин Вячеслав', '1984-05-12', '2021-05-12', true),
       (5, 'Вавилов Артём', '1978-11-12', '2015-08-04', true),
       (6, 'Богомолов Борис', '1997-06-30', '20024-08-01', true),
       (7, 'Колпакова Ольга', '1995-06-12', '2023-07-14', false),
       (8, 'Васильев Глеб', '1991-05-23', '2016-02-07', true),
       (9, 'Мельников Никита', '1987-09-05', '2019-06-22', true),
       (10, 'Афанасьев Федор', '1983-10-07', '2021-06-21', false);


INSERT INTO "employee"
VALUES (1, 1, 'Генеральный директор', null, 'ТОП', 1, 1111, '66a52bbad153b0189d3d4af0'),
       (2, 2, 'Главный бухгалтер', 1, 'БУХ', 1, 1112, '66a52bbad153b0189d3d4af1'),
       (3, 3, 'Руководитель отдела методологи', 1, 'МО', 1, 2000, '66a52bbad153b0189d3d4af2'),
       (4, 4, 'Руководитель отдела внутренней разработки', 1, 'ОВР', 1, 3001, '66a52bbad153b0189d3d4af3'),
       (5, 5, 'Специалист по кадровой работе', 1, 'ОУП', 1, 4002, '66a52bbad153b0189d3d4af4'),
       (6, 6, 'Методолог проекта', 3, 'МО', 1, null, '66a52bbad153b0189d3d4af5'),
       (7, 7, 'Офис-менеджер', 5, 'ОУП', 1, 4004, '66a52bbad153b0189d3d4af6'),
       (8, 8, 'Руководитель службы поддержки', null, 'ОПП', 2, null, null),
       (9, 9, 'Специалист службы поддержки', null, 'ОПП', 2, 5000, null),
       (10, 10, 'Главный архитектор', 1, 'АУД', null, 1112, '66a52bbad153b0189d3d4af9');


UPDATE "department" SET manager_id = 1 WHERE code = 'ТОП';
UPDATE "department" SET manager_id = 5 WHERE code = 'ОУП';
UPDATE "department" SET manager_id = 4 WHERE code = 'ОВР';
UPDATE "department" SET manager_id = 2 WHERE code = 'БУХ';
UPDATE "department" SET manager_id = 10 WHERE code = 'АУД';
UPDATE "department" SET manager_id = 3 WHERE code = 'МО';
UPDATE "department" SET manager_id = 8 WHERE code = 'ОПП';

SELECT setval(pg_get_serial_sequence('employee', 'id'), MAX(id)) FROM employee;
SELECT setval(pg_get_serial_sequence('office', 'id'), MAX(id)) FROM office;
SELECT setval(pg_get_serial_sequence('personal_info', 'id'), MAX(id)) FROM personal_info;