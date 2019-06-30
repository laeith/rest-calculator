SET DATABASE SQL SYNTAX PGS TRUE;

SET TIME ZONE INTERVAL '+00:00' HOUR TO MINUTE;

INSERT INTO computation_history (input, output, author_ip, computed_at_utc)
VALUES ('5 + 3', '8', '127.0.0.1', '2019-05-29 12:52:28');
INSERT INTO computation_history (input, output, author_ip, computed_at_utc)
VALUES ('(5 + 3) * 2', '16', '127.0.0.1', '2019-05-20 12:52:28');
INSERT INTO computation_history (input, output, author_ip, computed_at_utc)
VALUES ('2 * (5 + 3) / 2', '8', '127.0.0.1', '2019-05-19 12:52:28');
