package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.User;
import com.sangeng.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @RequestMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
       return loginService.login(user);
    }
}
