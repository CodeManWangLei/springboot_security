package com.wang.mapper;

import com.wang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User loadUserByUsername(String username);

    Boolean updatePassword(@Param("newPassword") String newPassword, @Param("username") String username);
}
