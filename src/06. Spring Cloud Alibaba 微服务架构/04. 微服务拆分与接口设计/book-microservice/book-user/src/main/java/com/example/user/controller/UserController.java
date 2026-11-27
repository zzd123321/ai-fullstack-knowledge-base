package com.example.user.controller;

import com.example.user.model.dto.UserCreateRequest;
import com.example.user.service.UserService;
import com.example.user.service.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserVO create(@Valid @RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    @GetMapping
    public List<UserVO> list() {
        return userService.listAll();
    }

    @GetMapping("/{id}")
    public UserVO detail(@PathVariable("id") Long id) {
        return userService.getDetail(id);
    }

    @PutMapping("/{id}")
    public UserVO update(@PathVariable("id") Long id,
                         @Valid @RequestBody UserCreateRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }
}