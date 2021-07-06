package ru.mariknv86.blog.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mariknv86.blog.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findAllByNameContaining(String query);

    @Query(nativeQuery = true, value = "select count(*) from tag2post where tag_id = :tagId")
    int getTagLinksCount(int tagId);

    @Query(
        value =
            "SELECT tag_id FROM tag2post "
                + "GROUP BY tag_id "
                + "ORDER BY COUNT(*) DESC "
                + "LIMIT 1",
        nativeQuery = true)
    int getMostPopularTagId();

}
