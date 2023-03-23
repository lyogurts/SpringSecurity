package com.sangeng.filter;

import com.sangeng.domain.LoginUser;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token（前端request请求头中可能携带token）
        String token = httpServletRequest.getHeader("token");
        //如果没携带token放行，后面还有过滤器处理
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //解析token
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            new RuntimeException("token非法");
        }
        //从redis获取信息
        String key = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(key);
        if (ObjectUtils.isEmpty(loginUser)){
          throw   new RuntimeException("用户未登录");
        }
        //存入securityContextHolder
        SecurityContext context = SecurityContextHolder.getContext();
        //认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null, loginUser.getAuthorities());
        //放入存入securityContextHolder
        context.setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
