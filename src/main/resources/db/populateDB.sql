DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM dishes;
DELETE
FROM votes;

ALTER SEQUENCE users_global_seq RESTART WITH 100001;

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

ALTER SEQUENCE restaurants_global_seq RESTART WITH 100001;

INSERT INTO restaurants (name)
VALUES ('August'),
       ('September'),
       ('October'),
       ('November'),
       ('December');

ALTER SEQUENCE dishs_global_seq RESTART WITH 100001;

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('roast pork', 250, 100001),
       ('fish and chips', 350, 100001),
       ('roast vegetables', 130, 100002),
       ('roast turkey', 210, 100002),
       ('tomato soup', 150, 100003),
       ('pizza', 180, 100003),
       ('pasta', 175, 100003);

ALTER SEQUENCE votes_global_seq RESTART WITH 100001;

INSERT INTO votes (restaurant_id, user_id)
VALUES (100001, 100001),
       (100004, 100002),
       (100003, 100003);