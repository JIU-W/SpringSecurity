package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.User;

public interface LoginServcie {
    ResponseResult login(User user);

    ResponseResult logout();

}
