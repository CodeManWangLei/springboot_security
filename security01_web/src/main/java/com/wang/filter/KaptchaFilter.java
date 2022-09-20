package com.wang.filter;

import com.wang.exception.KaptchaNotMatchException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KaptchaFilter extends UsernamePasswordAuthenticationFilter {
    private static final String FORM_KAPTCHA_KEY = "kaptcha";
    private String kaptchaParameter = FORM_KAPTCHA_KEY;

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
        String verifyCode = request.getParameter(getKaptchaParameter());
        String sessionVerifyCode = (String) request.getSession().getAttribute("kaptcha");
        if (!ObjectUtils.isEmpty(verifyCode) && !ObjectUtils.isEmpty(sessionVerifyCode)&&sessionVerifyCode.equalsIgnoreCase(verifyCode)){
            return super.attemptAuthentication(request, response);
        }
        throw new KaptchaNotMatchException("验证码不匹配");
    }
}
