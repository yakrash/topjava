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
VALUES (100001, '01-04-21 12:22:00', 'Lanch', 800),
       (100001, '01-04-21 12:25:00', 'Breakfast', 500),
       (100001, '01-04-21 12:29:00', 'Dinner', 100),
       (100001, '01-04-21 12:44:00', 'Lanch', 900);