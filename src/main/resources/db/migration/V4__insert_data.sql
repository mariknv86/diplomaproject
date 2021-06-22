--USERS INITIAL DATA
INSERT INTO `blog`.`users`
(`is_moderator`, `reg_time`, `name`, `email`, `password`, `code`, `photo`)
VALUES
(1, '2021-06-20', 'admin', 'blogadmin@gmail.com', '111', '222', '');

INSERT INTO `blog`.`users`
(`is_moderator`, `reg_time`, `name`, `email`, `password`, `code`, `photo`)
VALUES
(0, '2021-06-20', 'bolWAN', 'barabaka@mail.ru', '111', '222', '');

INSERT INTO `blog`.`users`
(`is_moderator`, `reg_time`, `name`, `email`, `password`, `code`, `photo`)
VALUES
(0, '2021-06-20', 'lamer', 'kukareku@rambler.ru', '111', '222', '');

--TAGS INITIAL DATA
INSERT INTO `blog`.`tags` (`name`) VALUES ('java');
INSERT INTO `blog`.`tags` (`name`) VALUES ('spring');
INSERT INTO `blog`.`tags` (`name`) VALUES ('it');
INSERT INTO `blog`.`tags` (`name`) VALUES ('жиза');
INSERT INTO `blog`.`tags` (`name`) VALUES ('разное');
INSERT INTO `blog`.`tags` (`name`) VALUES ('блог');

--POSTS INITIAL DATA
INSERT INTO `blog`.`posts`
(`is_active`, `moderation_status`, `user_id`, `time`, `title`, `text`)
VALUES
(1, 'NEW', 1, '2021-06-20 10:10:10', 'Первый пост!', 'Это первый пост блога!');

INSERT INTO `blog`.`posts`
(`is_active`, `moderation_status`, `user_id`, `time`, `title`, `text`)
VALUES
(1, 'NEW', 2, '2021-06-20 12:12:12', 'Внимание!', '... Спасибо за внимание!');

--TAG2POST INITIAL DATA
INSERT INTO `blog`.`tag2post` (`post_id`, `tag_id`) VALUES (1, 6);
INSERT INTO `blog`.`tag2post` (`post_id`, `tag_id`) VALUES (2, 5);