package ru.mariknv86.blog.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.model.Post;
import ru.mariknv86.blog.model.PostVote;
import ru.mariknv86.blog.model.User;
import ru.mariknv86.blog.repository.PostVoteRepository;

@Service
@AllArgsConstructor
public class PostVoteService {

    private final PostVoteRepository postVoteRepository;
    private final PostService postService;
    private final UserService userService;

    public boolean setLikeOrDislikeToPost(int postId, int likeOrDislike) {
        User user = userService.getCurrentUser();
        Optional<PostVote> postVoteOptional =
            postVoteRepository.findPostVoteByPostIdAndUserId(postId, user.getId());
        if(postVoteOptional.isEmpty()) {
            saveVote(postId, user, likeOrDislike);
            return true;
        }
        if(postVoteOptional.get().getValue() == likeOrDislike) {
            return false;
        }
        PostVote postVote = postVoteOptional.get();
        postVote.setValue( (byte) likeOrDislike);
        postVote.setTime(LocalDateTime.now());
        postVoteRepository.save(postVote);
        return true;
    }

    private void saveVote(int postId, User user, int likeOrDislike) {
        Post post = postService.getPostById(postId);
        PostVote postVote = PostVote.builder()
            .user(user)
            .post(post)
            .time(LocalDateTime.now())
            .value( (byte) likeOrDislike)
            .build();
        postVoteRepository.save(postVote);
    }


}
