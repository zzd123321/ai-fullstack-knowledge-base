package com.example.user.controller;

import com.example.common.model.Result;
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
    public Result<UserVO> create(@Valid @RequestBody UserCreateRequest request) {
        return Result.success(userService.create(request));
    }

    @GetMapping
    public Result<List<UserVO>> list() {
        return Result.success(userService.listAll());
    }

    @GetMapping("/{id}")
    public Result<UserVO> detail(@PathVariable("id") Long id) {
        return Result.success(userService.getDetail(id));
    }

    @PutMapping("/{id}")
    public Result<UserVO> update(@PathVariable("id") Long id,
                                 @Valid @RequestBody UserCreateRequest request) {
        return Result.success(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        return Result.success(userService.delete(id));
    }
}