INSERT INTO app_user (user_name, bio, password) VALUES ('TheRock', 'It''s about grind', 'password123');
INSERT INTO app_user (user_name, bio, password) VALUES ('John Cena', 'You can''t see me', 'password123');
INSERT INTO app_user (user_name, bio, password) VALUES ('Kevin Hart', 'Shortest guy in the room', 'password123');

INSERT INTO hot_take (content, author_id, creation_date, tag, verdict, resolved_date)
VALUES ('LeBron is officially better than MJ after last night.', 1, CURRENT_TIMESTAMP, 'NBA', 'CORRECT', CURRENT_TIMESTAMP);

INSERT INTO hot_take (content, author_id, creation_date, tag, verdict)
VALUES ('LeBron is officially better than MJ after last night.', 1, CURRENT_TIMESTAMP, 'NBA', 'PENDING');

INSERT INTO hot_take (content, author_id, creation_date, tag, verdict)
VALUES ('The Celtics are winning the next 3 championships. Guaranteed.', 2, CURRENT_TIMESTAMP, 'NBA', 'PENDING');

INSERT INTO hot_take (content, author_id, creation_date, tag, verdict, resolved_date)
VALUES ('Shaq would average 50 points in today''s NBA.', 3, CURRENT_TIMESTAMP, 'NBA', 'INCORRECT', CURRENT_TIMESTAMP);

INSERT INTO comment (content, user_id, hottake_id, created_date)
VALUES ('You can''t even see the truth in that statement!', 2, 1, CURRENT_TIMESTAMP);

INSERT INTO comment (content, user_id, hottake_id, created_date)
VALUES ('Shaq, you''re just big. I''d cross you up!', 3, 3, CURRENT_TIMESTAMP);

INSERT INTO comment (content, user_id, hottake_id, created_date)
VALUES ('He would not lets be realistic', 1, 3, CURRENT_TIMESTAMP);
