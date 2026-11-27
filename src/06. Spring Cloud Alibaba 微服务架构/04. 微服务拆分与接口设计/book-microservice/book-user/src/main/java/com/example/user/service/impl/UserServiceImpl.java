package com.example.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.user.model.dto.UserCreateRequest;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.service.UserService;
import com.example.user.service.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserVO create(UserCreateRequest request) {
        User user = BeanUtil.copyProperties(request, User.class);
        this.save(user);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public UserVO getDetail(Long id) {
        User user = this.getById(id);
        if (user == null) {
            return null;
        }
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public List<UserVO> listAll() {
        return BeanUtil.copyToList(this.list(), UserVO.class);
    }

    @Override
    public UserVO update(Long id, UserCreateRequest request) {
        User user = this.getById(id);
        if (user == null) {
            return null;
        }
        BeanUtil.copyProperties(request, user);
        this.updateById(user);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }
}