package ru.mariknv86.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.service.TagService;

@RestController
@RequestMapping(value = "/api/tag")
@RequiredArgsConstructor
public class ApiGeneralController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<?> getTags
        (@RequestParam(value = "query", defaultValue = "") String query) {
        return ResponseEntity.ok(tagService.getTags(query));

    }


}
