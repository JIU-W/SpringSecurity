package com.sangeng.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    //用户信息
    private User user;

    //用户权限信息
    private List<String> permissions;

    @JSONField(serialize = false)//不序列化
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    //获取用户的权限信息
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //把permissions中的String类型的权限信息封装成SimpleGrantedAuthority对象  权限集合的转换
        if(authorities != null){
            return authorities;
        }
        authorities = new ArrayList<>();
        for (String permission : permissions) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }
        return authorities;
    }

    //获取用户的密码
    public String getPassword() {
        return user.getPassword();
    }
    //获取用户的账号
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}