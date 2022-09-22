package com.wang.mapper;

import com.wang.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> getRolesByUid(int uid);
}
