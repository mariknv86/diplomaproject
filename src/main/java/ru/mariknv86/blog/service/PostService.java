package ru.mariknv86.blog.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.dto.response.PostDto;
import ru.mariknv86.blog.dto.response.PostListDto;
import ru.mariknv86.blog.dto.response.UserForPostDto;
import ru.mariknv86.blog.model.Post;
import ru.mariknv86.blog.model.enums.ModerationStatus;
import ru.mariknv86.blog.model.enums.SortMode;
import ru.mariknv86.blog.repository.PostRepository;

@Service
@AllArgsConstructor
public class PostService {

    //лимит анонса
    final int ANNOUNCE_LIMIT = 150;
    final String ANNOUNCE_TAIL = "...";

    private final PostRepository postRepository;

    public PostListDto getPosts(int offset, int limit, String mode) {
        int count = postRepository.getAllPostsCount("ACCEPTED");
        SortMode sortMode = SortMode.valueOf(mode.toUpperCase());
        Pageable pageable = PageRequest.of(offset / limit, limit);
        List<Post> posts;

        switch (sortMode) {
            case RECENT:
                posts = postRepository.findAllByModerationStatusAndIsActiveOrderByTimeDesc(
                    ModerationStatus.ACCEPTED, (byte) 1, pageable
                );
                break;
            case EARLY:
                posts = postRepository.findAllByModerationStatusAndIsActiveOrderByTimeAsc(
                    ModerationStatus.ACCEPTED, (byte) 1, pageable
                );
                break;
            case POPULAR:
                posts = postRepository.getMostPopularPosts(pageable);
                break;
            case BEST:
                posts = postRepository.getBestPosts(pageable);
                break;
            default:
                return null;
        }

        return PostListDto.builder()
            .count(count)
            .posts(convertToPostDto(posts))
            .build();

    }

    private List<PostDto> convertToPostDto(List<Post> posts) {
        List<PostDto> resultList = new ArrayList<>();

        for(Post post : posts) {
            int userId = post.getUser().getId();
            String userName = post.getUser().getName();
            int postId = post.getId();
            String announce = post.getText().length() > ANNOUNCE_LIMIT ?
                post.getText().substring(0, ANNOUNCE_LIMIT - ANNOUNCE_TAIL.length() - 1) :
                post.getText()
                .concat("...");

            UserForPostDto userInfo = UserForPostDto.builder()
                .id(userId)
                .name(userName)
                .build();

            PostDto postDto = PostDto.builder()
                .id(postId)
                .timestamp(post.getTime().atZone(ZoneId.systemDefault()).toEpochSecond())
                .user(userInfo)
                .title(post.getTitle())
                .announce(announce)
                .likeCount(
                    (int)
                        (post.getVotes().stream().filter(
                            vote -> vote.getValue() == 1)
                            .count())
                )
                .dislikeCount(
                    (int)
                        (post.getVotes().stream().filter(
                            vote -> vote.getValue() == -1)
                            .count())

                )
                .commentCount(post.getComments().size())
                .viewCount(post.getViewCount())
                .build();

            resultList.add(postDto);
        }
        return resultList;
    }



}
