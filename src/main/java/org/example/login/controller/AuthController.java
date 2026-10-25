package org.example.login.controller;

import org.example.login.dto.AuthRequest;
import org.example.login.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody AuthRequest request) {
        userService.register(request.getUsername(), request.getPassword());
        return Map.of("code", 200, "message", "注册成功");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AuthRequest request) {
        userService.login(request.getUsername(), request.getPassword());
        return Map.of("code", 200, "message", "Success");
    }
}