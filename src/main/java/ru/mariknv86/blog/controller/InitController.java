package ru.mariknv86.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.dto.response.InitResponseDto;
import ru.mariknv86.blog.service.InitService;

@RestController
@RequestMapping("api")
public class InitController {

    @Autowired
    private InitService initService;

    @GetMapping("/init")
    @ResponseStatus(HttpStatus.OK)
    public InitResponseDto init() {
        return initService.getInitInfo();
    }

}
