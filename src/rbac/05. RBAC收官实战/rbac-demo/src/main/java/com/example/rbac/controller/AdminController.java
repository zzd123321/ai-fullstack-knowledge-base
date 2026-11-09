package com.example.rbac.controller;

import com.example.rbac.dto.ApiResponse;
import com.example.rbac.entity.User;
import com.example.rbac.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:read')")
    public ApiResponse<List<User>> getUsers() {
        return ApiResponse.success(userService.findAll());
    }
}
