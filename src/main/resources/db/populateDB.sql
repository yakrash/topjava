DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100001, '2021-02-01T12:22:00', 'Lanch', 800),
       (100001, '2021-04-01T12:25:00', 'Breakfast', 500),
       (100000, '2021-04-01T12:25:00', 'Breakfast2', 1000),
       (100000, '2020-01-31T20:00', 'Ужин', 410),
       (100000, '2020-01-31T13:00', 'Обед', 500),
       (100000, '2020-01-31T10:00', 'Завтрак', 1000),
       (100000, '2020-01-31T00:00', 'Еда на граничное значение', 100),
       (100000, '2020-01-30T20:00', 'Ужин', 500),
       (100000, '2020-01-30T13:00', 'Обед', 1000),
       (100000, '2020-01-30T00:00', 'Завтрак', 500);
