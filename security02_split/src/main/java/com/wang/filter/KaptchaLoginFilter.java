package com.wang.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.config.RedisUtil;
import com.wang.exception.KaptchaNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class KaptchaLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String FORM_KAPTCHA_KEY = "kaptcha";
    private String kaptchaParameter = FORM_KAPTCHA_KEY;

    @Autowired
    private RedisUtil redisUtil;

    public String getKaptchaParameter() {
        return kaptchaParameter;
    }

    public void setKaptchaParameter(String kaptchaParameter) {
        this.kaptchaParameter = kaptchaParameter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        try {
            Map<String,String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = userInfo.get(getUsernameParameter());
            String password = userInfo.get(getPasswordParameter());
            String verifyCode = userInfo.get(getKaptchaParameter());
//            String sessionCode = (String)request.getSession().getAttribute("kaptcha");
            String sessionCode = (String) redisUtil.get("kaptcha");
            if (!ObjectUtils.isEmpty(verifyCode) && !ObjectUtils.isEmpty(sessionCode)&& sessionCode.equalsIgnoreCase(verifyCode)){
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);
                System.out.println(authRequest);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        throw new KaptchaNotMatchException("验证码不匹配");
    }
}
