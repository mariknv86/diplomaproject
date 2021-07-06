package ru.mariknv86.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.service.PostService;

@RestController
@RequestMapping(value = "/api/post")
@RequiredArgsConstructor
public class ApiPostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<?> getAllPosts(
        @RequestParam int offset, @RequestParam int limit, @RequestParam String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));

    }



}
