package ru.mariknv86.blog.dto.request;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class PostRequestDto {

    private long timestamp;

    private byte active;

    @Size(max = 256, message = "Заголовок не установлен")
    private String title;

    private String[] tags;

    @Size(min = 50, message = "Текст публикации слишком короткий")
    private String text;

}
