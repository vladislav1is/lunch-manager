DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS users_global_seq;
DROP SEQUENCE IF EXISTS restaurants_global_seq;
DROP SEQUENCE IF EXISTS dishs_global_seq;
DROP SEQUENCE IF EXISTS votes_global_seq;

CREATE SEQUENCE users_global_seq AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('users_global_seq'),
    name       VARCHAR(255)                      NOT NULL,
    email      VARCHAR(255)                      NOT NULL,
    password   VARCHAR(255)                      NOT NULL,
    enabled    BOOLEAN             DEFAULT TRUE  NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE SEQUENCE restaurants_global_seq AS INTEGER START WITH 100000;

CREATE TABLE restaurants
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('restaurants_global_seq'),
    name VARCHAR(255) NOT NULL,
    CONSTRAINT restaurant_name_idx UNIQUE (name)
);

CREATE SEQUENCE dishs_global_seq AS INTEGER START WITH 100000;

CREATE TABLE dishes
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('dishs_global_seq'),
    name          VARCHAR(255)                      NOT NULL,
    price         INTEGER                           NOT NULL,
    registered    DATE                DEFAULT now() NOT NULL,
    restaurant_id INTEGER                           NOT NULL,
    CONSTRAINT dish_name_idx UNIQUE (name, registered, restaurant_id),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);

CREATE SEQUENCE votes_global_seq AS INTEGER START WITH 100000;

CREATE TABLE votes
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('votes_global_seq'),
    user_id       INTEGER                           NOT NULL,
    restaurant_id INTEGER                           NOT NULL,
    registered    DATE                DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX user_unique_vote_idx ON VOTES (user_id, registered);