package ru.mariknv86.blog.mapper;

import org.mapstruct.Mapper;
import ru.mariknv86.blog.dto.request.UserRequestDto;
import ru.mariknv86.blog.model.User;

@Mapper
public interface UserDtoToUser {

    User map(UserRequestDto userRequestDto);

}
