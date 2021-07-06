package ru.mariknv86.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private int id;

    private long timestamp;

    private UserForPostDto user;

    private String title;

    private String announce;

    private int likeCount;

    private int dislikeCount;

    private int commentCount;

    private int viewCount;

}
