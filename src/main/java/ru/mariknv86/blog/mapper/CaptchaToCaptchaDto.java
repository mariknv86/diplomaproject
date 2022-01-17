package ru.mariknv86.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mariknv86.blog.dto.response.CaptchaResponseDto;
import ru.mariknv86.blog.model.CaptchaCode;

@Mapper
public interface CaptchaToCaptchaDto {

    @Mapping(source = "secretCode", target = "secret")
    CaptchaResponseDto map(CaptchaCode captchaCode);

}
