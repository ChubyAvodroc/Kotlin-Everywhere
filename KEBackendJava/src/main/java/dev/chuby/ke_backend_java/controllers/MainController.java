package dev.chuby.ke_backend_java.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainGreeting() {
        return "Welcome to Kotlin Everywhere Mexico Tour ¯\\_(ツ)_/¯";
    }
}
