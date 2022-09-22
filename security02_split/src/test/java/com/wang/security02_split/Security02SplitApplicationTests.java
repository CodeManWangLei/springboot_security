package com.wang.security02_split;

import com.wang.config.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Security02SplitApplicationTests {

    private final RedisUtil redisUtil;

    @Autowired
    public Security02SplitApplicationTests(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Test
    void contextLoads() {
        redisUtil.set("kaptcha","hello");
        System.out.println(redisUtil.get("kaptcha"));
    }

}
