package com.wang.mapper;
import com.wang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
// 命令行测试拉取
@Mapper
public interface UserMapper {
    User loadUserByUsername(String username);
}
