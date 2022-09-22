package com.wang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/test")
    public String login(){
        return "login ok";
    }


    @RequestMapping("/hello")
    public void helleRedis(){
    }
}
