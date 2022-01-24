package ru.mariknv86.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.dto.response.PostListDto;
import ru.mariknv86.blog.service.PostService;

@RestController
@RequestMapping(value = "/api/post")
@RequiredArgsConstructor
public class ApiPostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<?> getAllPosts(
        @RequestParam(defaultValue = "0", required = false) int offset,
        @RequestParam(defaultValue = "20", required = false) int limit,
        @RequestParam(defaultValue = "recent", required = false) String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByQuery(
        @RequestParam(defaultValue = "0", required = false) int offset,
        @RequestParam(defaultValue = "20", required = false) int limit,
        @RequestParam String query) {
        return ResponseEntity.ok(postService.searchByQuery(offset, limit, query));
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> getByDate(
        @RequestParam(defaultValue = "0", required = false) int offset,
        @RequestParam(defaultValue = "20", required = false) int limit,
        @RequestParam String date) {
        return ResponseEntity.ok(postService.getPostsByDate(offset, limit, date));
    }

    @GetMapping("/byTag")
    public ResponseEntity<?> getByTag(
        @RequestParam(defaultValue = "0", required = false) int offset,
        @RequestParam(defaultValue = "20", required = false) int limit,
        @RequestParam String tag) {
        return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/moderation")
    public ResponseEntity<?> getPostsForModerate(
        @RequestParam int offset,
        @RequestParam int limit,
        @RequestParam String status) {
        PostListDto posts = postService.getPostsForModeration(offset, limit, status);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyPosts(
        @RequestParam int offset,
        @RequestParam int limit,
        @RequestParam String status) {
        PostListDto posts = postService.getMyPosts(offset, limit, status);
        return ResponseEntity.ok(posts);

    }

}
