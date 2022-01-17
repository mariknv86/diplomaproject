package ru.mariknv86.blog.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CaptchaResponseDto {

    private String secret;

    private String image;

}
