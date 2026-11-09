//package com.example.webfluxdemo.controller;
//
//import com.example.webfluxdemo.model.User;
//import com.example.webfluxdemo.service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // 查询所有用户
//    @GetMapping
//    public Flux<User> getAllUsers() {
//        return userService.findAll();
//    }
//
//    // 根据 ID 查询单个用户
//    @GetMapping("/{id}")
//    public Mono<User> getUser(@PathVariable Long id) {
//        return userService.findById(id);
//    }
//
//    // 创建用户
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<User> createUser(@RequestBody User user) {
//        return userService.save(user);
//    }
//
//    // 更新用户
//    @PutMapping("/{id}")
//    public Mono<User> updateUser(@PathVariable Long id, @RequestBody User user) {
//        user.setId(id);
//        return userService.update(user);
//    }
//
//    // 删除用户
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Mono<Void> deleteUser(@PathVariable Long id) {
//        return userService.deleteById(id);
//    }
//}
