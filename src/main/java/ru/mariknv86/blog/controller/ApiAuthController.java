package ru.mariknv86.blog.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.dto.response.ResultFalseDto;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class ApiAuthController {

    @GetMapping("/check")
    public ResponseEntity<?> check() {
        return ResponseEntity.ok(new ResultFalseDto());
    }

}
