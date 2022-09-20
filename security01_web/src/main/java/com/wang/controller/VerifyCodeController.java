package com.wang.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class VerifyCodeController {

    public Producer producer;

    @Autowired
    public VerifyCodeController(Producer producer) {
        this.producer = producer;
    }

    @RequestMapping("/verifyCode")
    public void verifyCodeImage(HttpServletResponse response, HttpSession session) throws IOException {
        String verifyCode = producer.createText();
        session.setAttribute("kaptcha",verifyCode);
        BufferedImage bi = producer.createImage(verifyCode);
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(bi,"jpg",os);
    }
}
