package ru.mariknv86.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseDto {
    private boolean result;
    @JsonProperty("user")
    private UserLoginResponse userLoginResponse;

}
