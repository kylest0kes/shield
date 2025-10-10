package com.shield.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoController {
    @GetMapping("/yo")
    public String yo() {
        return "Yo from Shield Server!";
    }
}
