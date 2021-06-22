package ru.mariknv86.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mariknv86.blog.dto.GlobalSettingsDto;
import ru.mariknv86.blog.service.GlobalSettingsService;

@RestController
@RequestMapping("api")
public class SettingsController {

    @Autowired
    private GlobalSettingsService globalSettingsService;

    @GetMapping("/settings")
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok(globalSettingsService.getGlobalSettings());
    }

    @PutMapping("/settings")
    public ResponseEntity<?> putSettings(@RequestBody GlobalSettingsDto globalSettingsDto) {
        globalSettingsService.setGlobalSettings(globalSettingsDto);
        return ResponseEntity.ok(globalSettingsDto);
    }

}
