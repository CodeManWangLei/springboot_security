package com.wang.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    
    @RequestMapping("/getUserInfo")
    public Map<String,Object> getUserInfo() throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String,Object> map = new HashMap<>();
        map.put("username",authentication.getPrincipal());
        map.put("authorities",authentication.getAuthorities());
        map.put("credentials",authentication.getCredentials());
        return map;
    }
}
