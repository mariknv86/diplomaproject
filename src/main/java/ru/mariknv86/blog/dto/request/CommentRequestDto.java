package ru.mariknv86.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    @JsonProperty(value = "parent_id")
    private String parentId;

    @JsonProperty(value = "post_id")
    private int postId;

    @Size(max = 200, message = "Текст публикации слишком короткий")
    private String text;

}
