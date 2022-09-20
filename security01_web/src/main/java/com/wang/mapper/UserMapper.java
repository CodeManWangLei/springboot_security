package com.wang.mapper;
import com.wang.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User loadUserByUsername(String username);
}
