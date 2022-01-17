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
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiCalendarController {

    @Autowired
    private PostService postService;

    @GetMapping("/calendar")
    public ResponseEntity<?> getCalendar
    (@RequestParam(value = "year", defaultValue = "") int year) {
        return ResponseEntity.ok(postService.getCalendar(year));
    }

}
