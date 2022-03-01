package ru.mariknv86.blog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mariknv86.blog.model.PostVote;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {

    @Query(value = "SELECT v FROM PostVote v WHERE v.post.id = :postId AND v.user.id = :userId")
    Optional<PostVote> findPostVoteByPostIdAndUserId(int postId, int userId);

}
