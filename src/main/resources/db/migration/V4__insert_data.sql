INSERT INTO users
(is_moderator, reg_time, name, email, password, code)
VALUES
(1, '2021-06-20', 'admin', 'blogadmin@gmail.com', '111', '222');

INSERT INTO users
(is_moderator, reg_time, name, email, password, code)
VALUES
(0, '2021-06-20', 'bolWAN', 'barabaka@mail.ru', '111', '222');

INSERT INTO users
(is_moderator, reg_time, name, email, password, code)
VALUES
(0, '2021-06-20', 'lamer', 'kukareku@rambler.ru', '111', '222');

INSERT INTO tags (name) VALUES ('java');
INSERT INTO tags (name) VALUES ('spring');
INSERT INTO tags (name) VALUES ('it');
INSERT INTO tags (name) VALUES ('жиза');
INSERT INTO tags (name) VALUES ('разное');
INSERT INTO tags (name) VALUES ('блог');


INSERT INTO posts (is_active, moderation_status, user_id, time, title, text)
VALUES (1, 'NEW', 1, '2021-06-20 10:10:10', 'Первый пост!', 'Это первый пост блога!');

INSERT INTO posts (is_active, moderation_status, user_id, time, title, text)
VALUES (1, 'NEW', 2, '2021-06-20 12:12:12', 'Внимание!', '... Спасибо за внимание!');

INSERT INTO posts (is_active, moderation_status, user_id, time, title, text)
VALUES (2, 'DECLINED', 2, '2021-06-20 12:12:12', 'Отклоненный пост!', 'Его не утвердили!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер один!', 'Пост один!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер два!', 'Пост два!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер три!', 'Пост три!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер четыре!', 'Пост четыре!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер пять!', 'Пост пять!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер шесть!', 'Пост шесть!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер семь!', 'Пост семь!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер восемь!', 'Пост восемь!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер девять!', 'Пост девять!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер десять!', 'Пост десять!');

INSERT INTO posts (is_active, moderation_status, moderator_id, user_id, time, title, text)
VALUES (1, 'ACCEPTED', 1, 2, '2021-06-20 12:12:12', 'Пост номер одиннадцать!', 'Пост одиннадцать!');


INSERT INTO tag2post (post_id, tag_id) VALUES (1, 6);
INSERT INTO tag2post (post_id, tag_id) VALUES (2, 5);
INSERT INTO tag2post (post_id, tag_id) VALUES (4, 3);
INSERT INTO tag2post (post_id, tag_id) VALUES (5, 4);
INSERT INTO tag2post (post_id, tag_id) VALUES (6, 5);
INSERT INTO tag2post (post_id, tag_id) VALUES (7, 6);
INSERT INTO tag2post (post_id, tag_id) VALUES (8, 3);
INSERT INTO tag2post (post_id, tag_id) VALUES (9, 4);
INSERT INTO tag2post (post_id, tag_id) VALUES (10, 5);
INSERT INTO tag2post (post_id, tag_id) VALUES (11, 6);
INSERT INTO tag2post (post_id, tag_id) VALUES (12, 4);
INSERT INTO tag2post (post_id, tag_id) VALUES (12, 5);
INSERT INTO tag2post (post_id, tag_id) VALUES (12, 6);
INSERT INTO tag2post (post_id, tag_id) VALUES (13, 5);
INSERT INTO tag2post (post_id, tag_id) VALUES (14, 3);