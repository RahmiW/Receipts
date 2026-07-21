INSERT INTO app_user (user_name, bio, password) VALUES ('TheRock', 'It''s about grind', '$2a$10$PfXfwWOorzyT.z5yDskpueKicXHqVGoDfJsAwrn5nXX4KJe1RZ0qK');
INSERT INTO app_user (user_name, bio, password) VALUES ('John Cena', 'You can''t see me', '$2a$10$PfXfwWOorzyT.z5yDskpueKicXHqVGoDfJsAwrn5nXX4KJe1RZ0qK');
INSERT INTO app_user (user_name, bio, password) VALUES ('Kevin Hart', 'Shortest guy in the room', '$2a$10$PfXfwWOorzyT.z5yDskpueKicXHqVGoDfJsAwrn5nXX4KJe1RZ0qK');

INSERT INTO user_preferred_tags (user_id, preferred_tags) VALUES (1, 'NBA');
INSERT INTO user_preferred_tags (user_id, preferred_tags) VALUES (1, 'NFL');
INSERT INTO user_preferred_tags (user_id, preferred_tags) VALUES (2, 'MLB');
INSERT INTO user_preferred_tags (user_id, preferred_tags) VALUES (3, 'NBA');

INSERT INTO hot_take (content, author_id, creation_date, tag, resurface_count)
VALUES ('LeBron is officially better than MJ after last night.', 1, CURRENT_TIMESTAMP, 'NBA', 0);

INSERT INTO hot_take (content, author_id, creation_date, tag, resurface_count)
VALUES ('The Celtics are winning the next 3 championships. Guaranteed.', 2, CURRENT_TIMESTAMP, 'NBA', 0);

INSERT INTO hot_take (content, author_id, creation_date, tag, resurface_count)
VALUES ('Shaq would average 50 points in today''s NBA.', 3, CURRENT_TIMESTAMP, 'NBA', 0);

INSERT INTO comment (content, user_id, hottake_id, created_date)
VALUES ('You can''t even see the truth in that statement!', 2, 1, CURRENT_TIMESTAMP);

INSERT INTO comment (content, user_id, hottake_id, created_date)
VALUES ('Shaq, you''re just big. I''d cross you up!', 3, 3, CURRENT_TIMESTAMP);

INSERT INTO comment (content, user_id, hottake_id, created_date)
VALUES ('He would not lets be realistic', 1, 3, CURRENT_TIMESTAMP);
