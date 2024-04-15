package com.sangeng.service.impl;

import com.sangeng.domain.LoginUser;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.User;
import com.sangeng.service.LoginServcie;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginServcie {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    //登录
    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //后续authenticationManager会调用UserDetailsServiceImpl的loadUserByUsername方法(进一步确定认证是否通过)

        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回

        //获取SecurityContextHolder中的用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //获取用户id
        String userid = loginUser.getUser().getId().toString();
        //生成jwt
        String jwt = JwtUtil.createJWT(userid);
        //把jwt存入map
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);

        //把完整的用户信息存入redis  userid作为key
        redisCache.setCacheObject("login:"+userid,loginUser);

        return new ResponseResult(200,"登录成功",map);
    }


    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        //删除redis中的值
        //为什么要删除redis中的值？
        //因为redis中存储的是用户的完整信息，如果不删除，用户注销后，redis中的信息还在，别人可以拿到这个信息进行操作
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"注销成功");
    }

}
