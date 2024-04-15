package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sangeng.domain.LoginUser;
import com.sangeng.domain.Menu;
import com.sangeng.domain.User;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        //查询不到数据就抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        //通过数据库，查询对应的权限信息
        List<String> list = menuMapper.selectPermsByUserId(user.getId());

        //把数据封装成UserDetails返回
        return new LoginUser(user,list);

    }

}
