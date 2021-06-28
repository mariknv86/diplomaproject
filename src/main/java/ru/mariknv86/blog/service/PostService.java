package ru.mariknv86.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.repository.PostRepository;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;



}
