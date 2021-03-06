DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100001;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);

CREATE UNIQUE INDEX users_email_key ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT restaurants_name_idx UNIQUE (name)
);

CREATE TABLE dishes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name          VARCHAR(255)       NOT NULL,
    price         BIGINT             NOT NULL,
    registered    DATE DEFAULT now() NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    CONSTRAINT dishes_idx UNIQUE (name, registered, restaurant_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    user_id       INTEGER            NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    vote_date    DATE DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX votes_idx ON votes (user_id, vote_date);