-- Database intended only for tests
-- Set syntax to PostgreSQL
SET DATABASE SQL SYNTAX PGS TRUE;
-- CREATE SCHEMA IF NOT EXISTS PROD;
-- SET SCHEMA 'PROD';

CREATE TABLE IF NOT EXISTS computation_history
(
    id          BIGSERIAL PRIMARY KEY,
    input       TEXT NOT NULL,
    output      TEXT NOT NULL,
    author_ip   TEXT DEFAULT NULL,
    computed_at_utc TIMESTAMP DEFAULT NOW() NOT NULL
);

