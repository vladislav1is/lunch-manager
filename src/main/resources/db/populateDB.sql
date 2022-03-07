DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM dishes;
DELETE
FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 100001;

INSERT INTO users (name, email, password, registered)
VALUES ('Alex', 'user1@yandex.ru', '{noop}123456', '2021-11-02 21:45:00'),
       ('Bob', 'admin@gmail.com', '{noop}admin', '2021-11-02 21:45:00'),
       ('Elvis', 'user@yandex.ru', '{noop}password', '2021-11-02 21:45:00');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100001),
       ('ADMIN', 100001),
       ('USER', 100002),
       ('ADMIN', 100002),
       ('USER', 100003);

INSERT INTO restaurants (name)
VALUES ('Якитория'),
       ('Додо Пицца'),
       ('McDonalds'),
       ('Теремок'),
       ('Starbucks');

INSERT INTO dishes (name, price, registered, restaurant_id)
VALUES ('Калифорния', 55700, now(), 100004),
       ('Ролл Лосось-карамель', 49900, now(), 100004),
       ('Карбонара', 71900, '2021-11-11', 100005),
       ('Пепперони', 64900, '2021-11-11', 100005),
       ('Картофель Фри', 12800, '2021-11-11', 100006),
       ('Двойной Чизбургер', 12500, '2021-11-11', 100006),
       ('Чикенбургер', 5300, '2021-11-11', 100006);

INSERT INTO votes (restaurant_id, user_id, vote_date)
VALUES (100004, 100001, now()),
       (100004, 100001, '2021-11-11'),
       (100004, 100002, now());