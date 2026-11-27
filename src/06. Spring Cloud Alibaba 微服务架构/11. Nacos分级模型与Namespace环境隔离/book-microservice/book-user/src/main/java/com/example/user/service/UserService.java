package com.example.user.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.user.model.dto.UserCreateRequest;
import com.example.user.model.User;
import com.example.user.service.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {

    UserVO create(UserCreateRequest request);

    UserVO getDetail(Long id);

    List<UserVO> listAll();

    UserVO update(Long id, UserCreateRequest request);

    boolean delete(Long id);
}