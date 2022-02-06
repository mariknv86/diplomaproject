package ru.mariknv86.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.dto.response.CalendarDto;
import ru.mariknv86.blog.dto.response.CommentPartDto;
import ru.mariknv86.blog.dto.response.MapDto;
import ru.mariknv86.blog.dto.response.OnePostDto;
import ru.mariknv86.blog.dto.response.PostDto;
import ru.mariknv86.blog.dto.response.PostListDto;
import ru.mariknv86.blog.dto.response.UserForPostDto;
import ru.mariknv86.blog.dto.response.UserPartInfoDto;
import ru.mariknv86.blog.model.Post;
import ru.mariknv86.blog.model.PostComment;
import ru.mariknv86.blog.model.Tag;
import ru.mariknv86.blog.model.User;
import ru.mariknv86.blog.model.enums.ModerationStatus;
import ru.mariknv86.blog.model.enums.SortMode;
import ru.mariknv86.blog.repository.PostRepository;
import ru.mariknv86.blog.utils.Utils;

@Service
@AllArgsConstructor
public class PostService {

    //лимит анонса
    final int ANNOUNCE_LIMIT = 150;
    final String ANNOUNCE_TAIL = "...";

    private final PostRepository postRepository;
    private final UserService userService;

    public PostListDto getPosts(int offset, int limit, String mode) {
        int count = postRepository.getAllPostsCount(ModerationStatus.ACCEPTED.toString());
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

    public PostListDto getMyPosts(int offset, int limit, String status) {
        final String statusInactive = "inactive";
        final String statusPending = "pending";
        final String statusDeclined = "declined";
        final String statusPublished = "published";
        Pageable pageable = PageRequest.of(offset / limit, limit);
        int isActive = 1;
        String moderationStatus = "%";
        int userId = userService.getCurrentUser().getId();
        if(status.equals(statusInactive)) {
            isActive = 0;
        }

        switch (status) {
            case statusPending: {
                moderationStatus = ModerationStatus.NEW.toString();
                break;
            }
            case statusDeclined: {
                moderationStatus = ModerationStatus.DECLINED.toString();
                break;
            }
            case statusPublished: {
                moderationStatus = ModerationStatus.ACCEPTED.toString();
                break;
            }


        }

        int count = postRepository.getMyPostCount(userId, isActive, moderationStatus);
        List<Post> posts = postRepository.getMyPosts(userId, isActive, moderationStatus, pageable);

        return PostListDto.builder()
            .count(count)
            .posts(convertToPostDto(posts))
            .build();

    }

    public PostListDto getPostsForModeration(int offset, int limit, String status) {
        int moderatorId = userService.getCurrentUser().getId();
        Pageable pageable = PageRequest.of(offset / limit, limit);

        List<Post> posts = null;

        if(ModerationStatus.valueOf(status.toUpperCase()) == ModerationStatus.NEW) {
            posts = postRepository.getNotModeratedPosts(pageable);
        } else {
            posts = postRepository.getModerationPosts(status.toUpperCase(),
                (byte) moderatorId, pageable);
        }

        int count = posts != null ? posts.size() : 0;

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
                .timestamp(Utils.convertLocalDateTimeToMillis(post.getTime()))
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


    public PostListDto searchByQuery(int offset, int limit, String query) {
        if(query.isEmpty()) {
            return getPosts(offset, limit, SortMode.BEST.toString());
        } else {
            int count = postRepository.getCountByQuery(query);
            Pageable pageable = PageRequest.of(offset / limit, limit);
            List<Post> posts = postRepository.searchByQuery(query, pageable);

            return PostListDto.builder()
                .count(count)
                .posts(convertToPostDto(posts))
                .build();
        }
    }

    public PostListDto getPostsByDate(int offset, int limit, String date) {
        if(date.isEmpty()) {
            throw new IllegalArgumentException("Date not set");
        }
        int count = postRepository.getCountByDate(date);
        Pageable pageable = PageRequest.of(offset / limit, limit);
        List<Post> posts = postRepository.geyByDate(date, pageable);

        return PostListDto.builder()
            .count(count)
            .posts(convertToPostDto(posts))
            .build();

    }

    public PostListDto getPostsByTag(int offset, int limit, String tag) {
        if(tag.isEmpty()) {
            throw new IllegalArgumentException("Date not set");
        }
        int count = postRepository.getCountByTag(tag);
        Pageable pageable = PageRequest.of(offset / limit, limit);
        List<Post> posts = postRepository.getByTag(tag, pageable);

        return PostListDto.builder()
            .count(count)
            .posts(convertToPostDto(posts))
            .build();

    }

    public CalendarDto getCalendar(int year) {
        if (String.valueOf(year).length() !=4 || year == 0) {
            year = LocalDateTime.now().getYear();
        }

        List<Integer> years = postRepository.getYearsWithPostsPresent();
        List<MapDto> calendarDates = postRepository.getPostCountGroupByDate(year);
        Map<String, Integer> postsMap = calendarDates.stream()
            .collect(Collectors.toMap(MapDto::getKey,
                MapDto::getValue));

        return CalendarDto.builder()
            .years(years)
            .posts(postsMap)
            .build();
    }

    public OnePostDto getPostById(int id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post not found !"));

        User user = post.getUser();
        UserForPostDto userForPostDto = UserForPostDto.builder()
            .id(user.getId())
            .name(user.getName())
            .build();
        post.incrementUserView();

        List<PostComment> comments = post.getComments();
        List<CommentPartDto> commentParts = new ArrayList<>();
        for(PostComment comment : comments) {
            UserPartInfoDto userPartInfo = UserPartInfoDto.builder()
                .id(comment.getUserId().getId())
                .name(comment.getUserId().getName())
                .photo(comment.getUserId().getPhoto())
                .build();

            CommentPartDto commentPart = CommentPartDto.builder()
                .id(comment.getId())
                .timestamp(Utils.convertLocalDateTimeToMillis(comment.getTime()))
                .text(comment.getText())
                .user(userPartInfo)
                .build();
            commentParts.add(commentPart);
        }

        List<String> tagsList = post.getTagList().stream()
            .map(Tag::getName).collect(Collectors.toList());

        return OnePostDto.builder()
            .active(post.getIsActive() == 1)
            .timestamp(Utils.convertLocalDateTimeToMillis(post.getTime()))
            .user(userForPostDto)
            .title(post.getTitle())
            .likeCount(post.getLikeCount())
            .dislikeCount(post.getDislikeCount())
            .viewCount(post.getViewCount())
            .comments(commentParts)
            .tags(tagsList)
            .build();
    }
}
