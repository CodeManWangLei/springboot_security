package com.wang.mapper;

import com.wang.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
// 在github修改了一下，试一试在本地能否拉取
@Mapper
public interface RoleMapper {
    List<Role> getRolesByUid(int uid);
}
