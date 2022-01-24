package ru.mariknv86.blog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mariknv86.blog.dto.response.MapDto;
import ru.mariknv86.blog.model.Post;
import ru.mariknv86.blog.model.enums.ModerationStatus;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    /**
     * Запрос для api/post/popular
     */
    @Query(
        value =
            "SELECT posts.* "
                + "FROM posts "
                + "LEFT JOIN post_comments "
                + "ON posts.id = post_comments.post_id "
                + "WHERE posts.is_active = 1 "
                + "AND posts.moderation_status = 'ACCEPTED'"
                + "AND posts.time <= NOW()"
                + "GROUP BY posts.id "
                + "ORDER BY count(post_comments.post_id) DESC",
        nativeQuery = true)
    List<Post> getMostPopularPosts(Pageable pageable);

    /**
     * Запрос для api/post/best
     */
    @Query(
        value =
            "SELECT posts.* "
                + "FROM posts "
                + "LEFT JOIN post_votes "
                + "ON posts.id = post_votes.post_id "
                + "WHERE posts.is_active = 1 AND posts.moderation_status = 'ACCEPTED' "
                + "AND posts.time <= NOW() AND post_votes.value = 1 "
                + "GROUP BY posts.id "
                + "ORDER BY COUNT(post_votes.post_id) DESC",
        nativeQuery = true)
    List<Post> getBestPosts(Pageable pageable);

    /**
     * запрос общего количества постов для запроса api/post
     */
    @Query(
        value =
            "SELECT count(*) FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = :status "
                + "AND time <= NOW()",
        nativeQuery = true)
    int getAllPostsCount (String status);

    /**
     * запрос общего количества постов для запроса api/post/my
     */
    @Query(
        value =
            "SELECT COUNT(*) FROM posts "
                + "WHERE is_active = :isActive "
                + "AND moderation_status = :moderationStatus "
                + "AND user_id = :userId",
        nativeQuery = true)
    int getMyPostCount(int userId, int isActive, String moderationStatus);

    /**
     * запрос общего постов для запроса api/post/my
     */
    @Query(
        value =
            "SELECT * FROM posts "
                + "WHERE is_active = :isActive "
                + "AND moderation_status = :moderationStatus "
                + "AND user_id = :userId",
        nativeQuery = true)
    List<Post> getMyPosts(int userId, int isActive, String moderationStatus, Pageable pageable);

    /**
     * запрос общего количества постов для запроса api/post/moderate
     */
    @Query(
        value =
            "SELECT count(*) FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = :status "
                + "AND moderator_id = :moderatorId",
        nativeQuery = true)
    int getModerationPostsCount (String status, int moderatorId);

    /**
     * запрос общего количества немодерированных постов для запроса api/post/moderate
     */
    @Query(
        value =
            "SELECT count(*) FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = 'NEW' "
                + "AND moderator_id is NULL",
        nativeQuery = true)
    int getNotModeratedPostsCount();

    /**
     * запрос общего количества немодерированных постов для запроса api/post/moderate
     */
    @Query(
        value =
            "SELECT * FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = 'NEW' "
                + "AND moderator_id is NULL",
        nativeQuery = true)
    List<Post> getNotModeratedPosts(Pageable pageable);

    /**
     * запрос постов для запроса api/post/moderate
     */
    @Query(
        value =
            "SELECT * FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = :status "
                + "AND moderator_id = :moderatorId",
        nativeQuery = true)
    List<Post> getModerationPosts (String status, int moderatorId, Pageable pageable);

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

    /**
     * Запрос для api/post/search
     */
    @Query(
        value =
            "SELECT * FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = 'ACCEPTED' "
                + "AND time <= now() "
                + "AND title LIKE %:query% OR text LIKE %:query%",
        nativeQuery = true)
    List<Post> searchByQuery(String query, Pageable pageable);

    /**
     * Запрос для api/post/byDate
     */
    @Query(
        value =
            "SELECT * FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = 'ACCEPTED' "
                + "AND time <= now() "
                + "AND DATE_FORMAT(time, '%Y-%m-%d') LIKE :date",
        nativeQuery = true)
    List<Post> geyByDate(String date, Pageable pageable);

    /**
     * Запрос для api/post/search, возвращает кол-во постов
     */
    @Query(
        value =
            "SELECT COUNT(*) FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = 'ACCEPTED' "
                + "AND time <= now() "
                + "AND title LIKE %:query% OR text LIKE %:query%",
        nativeQuery = true)
    int getCountByQuery(String query);

    /**
     * Запрос для api/post/byDate, возвращает кол-во постов
     */
    @Query(
        value =
            "SELECT COUNT(*) FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = 'ACCEPTED' "
                + "AND time <= now() "
                + "AND DATE_FORMAT(time, '%Y-%m-%d') LIKE :date",
        nativeQuery = true)
    int getCountByDate(String date);

    /**
     * Запрос для api/post/byTag, возвращает кол-во постов
     */
    @Query(
        value =
            "SELECT COUNT(*) FROM posts p "
                + "INNER JOIN tag2post t2p ON t2p.post_id = p.id "
                + "INNER JOIN tags t ON t.id = t2p.tag_id "
                + "WHERE p.is_active = 1 "
                + "AND p.moderation_status = 'ACCEPTED' "
                + "AND p.time <= now() "
                + "AND t.name LIKE %:tag%",
        nativeQuery = true)
    int getCountByTag(String tag);

    /**
     * Запрос для api/post/byTag, возвращает посты
     */
    @Query(
        value =
            "SELECT * FROM posts p "
                + "INNER JOIN tag2post t2p ON t2p.post_id = p.id "
                + "INNER JOIN tags t ON t.id = t2p.tag_id "
                + "WHERE p.is_active = 1 "
                + "AND p.moderation_status = 'ACCEPTED' "
                + "AND p.time <= now() "
                + "AND t.name LIKE %:tag%",
        nativeQuery = true)
    List<Post> getByTag(String tag, Pageable pageable);

    /**
     * Запрос для api/post/calendar, возвращает кол-во постов с группировкой по датам
     */
    @Query(
            "SELECT DATE_FORMAT(p.time, '%Y-%m-%d') AS key, COUNT(p) AS value FROM Post p "
                + "WHERE YEAR(p.time) = ?1 "
                + "AND p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' "
                + "AND p.time < CURRENT_DATE() "
                + "GROUP BY key ORDER BY DATE_FORMAT(p.time, '%Y-%m-%d') DESC"
        )
    List<MapDto> getPostCountGroupByDate(int year);

    /**
     * Запрос для api/post/calendar, возвращает года, за которые есть публикации
     */
    @Query(
        value =
            "SELECT DISTINCT YEAR(time) FROM posts "
                + "WHERE is_active = 1 "
                + "AND moderation_status = 'ACCEPTED' "
                + "AND time <= now() "
                + "ORDER BY YEAR(TIME)", nativeQuery = true)
    List<Integer> getYearsWithPostsPresent();

    /**
     * Запрос для api/post/{id}, возвращает post
     */
    Optional<Post> findById(Integer id);
}
