-- Creation of user table
CREATE TABLE IF NOT EXISTS users (
  id          SERIAL PRIMARY KEY,
  uuid        uuid default gen_random_uuid(),
  email       varchar(50),
  password    varchar(10) NOT NULL,
  name        varchar(100) NOT NULL,
  role        varchar(20) NOT NULL
);