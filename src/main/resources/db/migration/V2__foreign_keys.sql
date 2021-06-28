ALTER TABLE posts
ADD CONSTRAINT fk_post_moderator_id
        FOREIGN KEY (moderator_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE posts
ADD CONSTRAINT fk_post_user_id
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX fk_post_moderator_id_idx ON posts (moderator_id ASC) VISIBLE;
CREATE INDEX fk_post_user_id_idx ON posts (user_id ASC) VISIBLE;

ALTER TABLE post_votes
ADD CONSTRAINT fk_vote_user_id
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE post_votes
ADD CONSTRAINT fk_vote_post_id
        FOREIGN KEY (post_id)
        REFERENCES posts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX fk_vote_user_id_idx ON post_votes (user_id ASC) VISIBLE;
CREATE INDEX fk_vote_post_id_idx ON post_votes (post_id ASC) VISIBLE;

ALTER TABLE tag2post
ADD CONSTRAINT fk_post_id
        FOREIGN KEY (post_id)
        REFERENCES posts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE tag2post
ADD CONSTRAINT fk_tag_id
        FOREIGN KEY (tag_id)
        REFERENCES tags (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX fk_post_id_idx ON tag2post (post_id ASC) VISIBLE;
CREATE INDEX fk_tag_id_idx ON tag2post (tag_id ASC) VISIBLE;

ALTER TABLE post_comments
ADD CONSTRAINT fk_comments_post_id
        FOREIGN KEY (post_id)
        REFERENCES posts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE post_comments
ADD CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
CREATE INDEX fk_post_id_idx ON post_comments (post_id ASC) VISIBLE;
CREATE INDEX fk_user_id_idx ON post_comments (user_id ASC) VISIBLE;

