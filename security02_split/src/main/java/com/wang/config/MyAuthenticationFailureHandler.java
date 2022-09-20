package com.wang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("msg","认证失败，,用户名或密码错误");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().print(new ObjectMapper().writeValueAsString(map));
    }
}
