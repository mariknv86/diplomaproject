ALTER TABLE `blog`.`posts`
ADD CONSTRAINT `fk_post_moderator_id`
        FOREIGN KEY (`moderator_id`)
        REFERENCES `blog`.`users` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE `blog`.`posts`
ADD CONSTRAINT `fk_post_user_id`
        FOREIGN KEY (`user_id`)
        REFERENCES `blog`.`users` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX `fk_post_moderator_id_idx` ON `blog`.`posts` (`moderator_id` ASC) VISIBLE;
CREATE INDEX `fk_post_user_id_idx` ON `blog`.`posts` (`user_id` ASC) VISIBLE;

ALTER TABLE `blog`.`post_votes`
ADD CONSTRAINT `fk_vote_user_id`
        FOREIGN KEY (`user_id`)
        REFERENCES `blog`.`users` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE `blog`.`post_votes`
ADD CONSTRAINT `fk_vote_post_id`
        FOREIGN KEY (`post_id`)
        REFERENCES `blog`.`posts` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX `fk_vote_user_id_idx` ON `blog`.`post_votes` (`user_id` ASC) VISIBLE;
CREATE INDEX `fk_vote_post_id_idx` ON `blog`.`post_votes` (`post_id` ASC) VISIBLE;

ALTER TABLE `blog`.`tag2post`
ADD CONSTRAINT `fk_post_id`
        FOREIGN KEY (`post_id`)
        REFERENCES `blog`.`posts` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE `blog`.`tag2post`
ADD CONSTRAINT `fk_tag_id`
        FOREIGN KEY (`tag_id`)
        REFERENCES `blog`.`tags` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX `fk_post_id_idx` ON `blog`.`tag2post` (`post_id` ASC) VISIBLE;
CREATE INDEX `fk_tag_id_idx` ON `blog`.`tag2post` (`tag_id` ASC) VISIBLE;

ALTER TABLE `blog`.`post_comments`
ADD CONSTRAINT `fk_comments_post_id`
        FOREIGN KEY (`post_id`)
        REFERENCES `blog`.`posts` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE `blog`.`post_comments`
ADD CONSTRAINT `fk_user_id`
        FOREIGN KEY (`user_id`)
        REFERENCES `blog`.`users` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX `fk_post_id_idx` ON `blog`.`post_comments` (`post_id` ASC) VISIBLE;
CREATE INDEX `fk_user_id_idx` ON `blog`.`post_comments` (`user_id` ASC) VISIBLE;

