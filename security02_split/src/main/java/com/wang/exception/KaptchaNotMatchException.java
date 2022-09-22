package com.wang.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class KaptchaNotMatchException extends UsernameNotFoundException {
    public KaptchaNotMatchException(String msg) {
        super(msg);
    }

    public KaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
