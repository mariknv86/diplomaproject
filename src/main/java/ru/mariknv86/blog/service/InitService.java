package ru.mariknv86.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.dto.response.InitResponseDto;

@Service
@AllArgsConstructor
public class InitService {

    public InitResponseDto getInitInfo() {
        return new InitResponseDto();
    }

}
