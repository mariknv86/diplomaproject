package ru.mariknv86.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentPartDto {

    private int id;

    private long timestamp;

    private String text;

    private UserPartInfoDto user;

}
