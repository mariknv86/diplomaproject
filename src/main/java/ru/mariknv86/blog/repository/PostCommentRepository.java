package ru.mariknv86.blog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mariknv86.blog.model.PostComment;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {

    Optional<PostComment> findById(int id);

    @Query(value = "SELECT COUNT(c) FROM PostComment c WHERE c.postId = :id")
    int findCommentsCountByPostId(int id);
}
