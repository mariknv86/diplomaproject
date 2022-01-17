package ru.mariknv86.blog.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnePostDto {

    private int id;

    private long timestamp;

    private boolean active;

    private UserForPostDto user;

    private String title;

    private String text;

    private long likeCount;

    private long dislikeCount;

    private int viewCount;

    private List<CommentPartDto> comments;

    private List<String> tags;

}
