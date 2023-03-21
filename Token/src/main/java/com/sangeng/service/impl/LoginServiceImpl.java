package com.sangeng.service.impl;

import com.sangeng.domain.LoginUser;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.User;
import com.sangeng.service.LoginService;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager authenticationManager 进行用户认证

        UsernamePasswordAuthenticationToken authenticateToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
//        authenticate是接口,UsernamePasswordAuthenticationToken是实现类
        Authentication authenticate = authenticationManager.authenticate(authenticateToken);

        //如果认证没通过给出对应的提示
        if (ObjectUtils.isEmpty(authenticate)){
            throw  new RuntimeException("登录失败");
        }

        //认证通过了。使用userid生成一个jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        //把完整的用户信息存入redis userid为key
        redisCache.setCacheObject("login:"+ id,loginUser);
        //把token响应给前端
        Map<String , String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登录成功",map);
    }
}
