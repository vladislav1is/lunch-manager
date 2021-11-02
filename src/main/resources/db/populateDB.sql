DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM dishes;
DELETE
FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 100001;

INSERT INTO users (name, email, password)
VALUES ('Alex', '@yandex.ru', '1234'),
       ('Bob', '@mail.ru', '1234'),
       ('Elvis', '@gmail.com', '1234');

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

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('roast pork', 250, 100004),
       ('fish and chips', 350, 100004),
       ('roast vegetables', 130, 100005),
       ('roast turkey', 210, 100005),
       ('tomato soup', 150, 100006),
       ('pizza', 180, 100006),
       ('pasta', 175, 100006);

INSERT INTO votes (restaurant_id, user_id)
VALUES (100004, 100001),
       (100007, 100002),
       (100006, 100003);