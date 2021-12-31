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
VALUES ('August'),
       ('September'),
       ('October'),
       ('November'),
       ('December');

INSERT INTO dishes (name, price, registered, restaurant_id)
VALUES ('roast pork', 250, now(), 100004),
       ('fish and chips', 350, now(), 100004),
       ('roast vegetables', 130, '2021-11-11', 100005),
       ('roast turkey', 210, '2021-11-11', 100005),
       ('tomato soup', 150, '2021-11-11', 100006),
       ('pizza', 180, '2021-11-11', 100006),
       ('pasta', 175, '2021-11-11', 100006);

INSERT INTO votes (restaurant_id, user_id, registered)
VALUES (100004, 100001, now()),
       (100004, 100001, '2021-11-11'),
       (100004, 100002, now()),
       (100006, 100003, now());