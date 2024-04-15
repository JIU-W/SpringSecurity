package com.sangeng.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('system:dept:index')")//拥有system:test:index权限才可以访问,这里的权限字符串要和数据库中的权限字符串一致
    //@PreAuthorize("hasAnyAuthority('admin','test','system:dept:list')")
    //@PreAuthorize("hasRole('system:dept:list')")
    //@PreAuthorize("hasAnyRole('admin','system:dept:list')")
    public String hello(){
        return "hello";
    }

}
