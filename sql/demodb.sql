create DATABASE demodb;
\c demodb;

CREATE TABLE IF NOT EXISTS jobs (
    id BIGSERIAL PRIMARY KEY,
    title text NOT NULL,
    url text NOT NULL,
    company text NOT NULL
);