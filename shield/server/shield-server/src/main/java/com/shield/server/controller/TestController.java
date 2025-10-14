package com.shield.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // secure route to test authentication required
    @GetMapping("/hello")
    public String hello(Principal principal) {
        return "Hello, " + principal.getName() + ", you are legit! That's cool :)";
    }

    // public route to test no authentication required
    @GetMapping("/public/topics")
    public String publicTopics() {
        return "This is a public endpoint accessible without authentication.";
    }
    
}
