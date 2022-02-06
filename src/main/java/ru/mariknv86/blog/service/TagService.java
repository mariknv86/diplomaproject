package ru.mariknv86.blog.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.dto.response.TagDto;
import ru.mariknv86.blog.dto.response.TagsResponseDto;
import ru.mariknv86.blog.model.Tag;
import ru.mariknv86.blog.model.enums.ModerationStatus;
import ru.mariknv86.blog.repository.PostRepository;
import ru.mariknv86.blog.repository.TagRepository;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final PostRepository postRepository;

    public TagsResponseDto getTags(String query) {

        List<Tag> tagList;

        if(query.isEmpty()) {
            tagList = tagRepository.findAll();
        } else {
            tagList = tagRepository.findAllByNameContaining(query);
        }

        int mostPopularTagId = tagRepository.getMostPopularTagId();
        int mostPopularTagPostsCount = tagRepository.getTagLinksCount(mostPopularTagId);
        int allPostsCount = postRepository.getAllPostsCount(ModerationStatus.ACCEPTED.toString());
        float mostPopularTagWeight = (float) mostPopularTagPostsCount / allPostsCount;
        float normalizationRatio = 1 / mostPopularTagWeight;

        List<TagDto> responseTagList = tagList.stream()
            .map(tag -> {
                String name = tag.getName();
                float weight =
                    (float) tagRepository.
                        getTagLinksCount(tag.getId()) / allPostsCount * normalizationRatio;
                return new TagDto(name, weight);
            })
            .collect(Collectors.toList());
        return new TagsResponseDto(responseTagList);
    }

}
