package ru.mariknv86.blog.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mariknv86.blog.model.Post;
import ru.mariknv86.blog.model.enums.ModerationStatus;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    /**
     * Запрос для api/post/popular
     */
    @Query(
        value =
            "select posts.* "
                + "from posts "
                + "left join post_comments "
                + "on posts.id = post_comments.post_id "
                + "where posts.is_active = 1 "
                + "and posts.moderation_status = 'ACCEPTED'"
                + "and posts.time <= now()"
                + "group by posts.id "
                + "order by count(post_comments.post_id) desc",
        nativeQuery = true)
    List<Post> mostPopularPosts(Pageable pageable);

    /**
     * Запрос для api/post/best
     */
    @Query(
        value =
            "SELECT posts.* "
                + "FROM posts "
                + "left join post_votes "
                + "on posts.id = post_votes.post_id "
                + "where posts.is_active = 1 and posts.moderation_status = 'ACCEPTED' "
                + "and posts.time <= now() and post_votes.value = 1 "
                + "group by posts.id "
                + "order by count(post_votes.post_id) desc",
        nativeQuery = true)
    List<Post> bestPosts(Pageable pageable);

    /**
     * запрос общего количества постов для запроса api/post
     */
    @Query(
        value =
            "SELECT count(*) FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = :moderation_status "
                + "AND time <= now()",
        nativeQuery = true)
    int getAllPostsCount (@Param("moderation_status") String moderationStatus);

    /**
     * Запрос для api/post/recent
     */
    List<Post> findAllByModerationStatusAndIsActiveOrderByTimeDesc(
        ModerationStatus moderationStatus, Byte active, Pageable pageable);

    /**
     * Запрос для api/post/early
     */
    List<Post> findAllByModerationStatusAndIsActiveOrderByTimeAsc(
        ModerationStatus moderationStatus, Byte active, Pageable pageable);

}
