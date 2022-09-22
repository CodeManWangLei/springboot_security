package com.wang.controller;

import com.google.code.kaptcha.Producer;
import com.wang.config.RedisUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class VerifyCodeController {

    public Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    public VerifyCodeController(Producer producer, RedisUtil redisUtil) {
        this.producer = producer;
    }

    @RequestMapping("/verifyCode")
    public String verifyCodeImage(HttpSession session) throws IOException {
//        生成验证码
        String verifyCode = producer.createText();
//        将验证码存入到session中
//        session.setAttribute("kaptcha",verifyCode);
//        兼用redis实现
        redisUtil.set("kaptcha",verifyCode,30);
//        将验证码转化成图片
        BufferedImage bi = producer.createImage(verifyCode);
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(bi,"jpg",fos);
        System.out.println(redisUtil.get("kaptcha"));
        return Base64.encodeBase64String(fos.toByteArray());
    }
}
