package ru.mariknv86.blog.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.mariknv86.blog.dto.request.PostRequestDto;
import ru.mariknv86.blog.model.Post;

@Mapper
public interface PostRequestToPost {

    @AfterMapping
    default void checkAndFixTime(@MappingTarget Post post) {
        if(post.getTime().isBefore(LocalDateTime.now())) {
            post.setTime(LocalDateTime.now());
        }
    }

    default LocalDateTime map(long value) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.systemDefault());
    }

    @Mapping(target = "time", source = "timestamp")
    @Mapping(target = "isActive", source = "active", defaultValue = "1")
    @Mapping(target = "moderationStatus", expression = "java(ru.mariknv86.blog.model.enums.ModerationStatus.NEW)")
    @Mapping(target = "viewCount", expression = "java(0)")
    Post mapNew(PostRequestDto postRequestDto);

    @Mapping(target = "time", source = "timestamp")
    @Mapping(target = "isActive", source = "active")
    @Mapping(target = "moderationStatus", expression = "java(ru.mariknv86.blog.model.enums.ModerationStatus.NEW)")
    Post mapEdit(PostRequestDto postRequestDto);

}
