package ru.mariknv86.blog.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.dto.request.CommentRequestDto;
import ru.mariknv86.blog.dto.response.ResultsResponseDto;
import ru.mariknv86.blog.service.CommentService;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiCommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    public ResultsResponseDto<Integer> addComment(
        @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.addComment(commentRequestDto);
    }



}
