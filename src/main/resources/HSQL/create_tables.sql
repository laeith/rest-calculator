-- Database intended only for tests
-- Set syntax to PostgreSQL
SET DATABASE SQL SYNTAX PGS TRUE;
-- CREATE SCHEMA IF NOT EXISTS PROD;
-- SET SCHEMA 'PROD';

CREATE TABLE IF NOT EXISTS computation_history
(
    id          BIGSERIAL PRIMARY KEY,
    input       TEXT,
    output      DOUBLE PRECISION,
    author_ip   TEXT,
    computed_at_utc TIMESTAMP DEFAULT NOW()
);

