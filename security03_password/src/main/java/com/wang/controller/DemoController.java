package com.wang.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String demo(HttpServletResponse response) throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
        String encodePassword = bCryptPasswordEncoder.encode("123");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(encodePassword);
        return "index";
    }
}
