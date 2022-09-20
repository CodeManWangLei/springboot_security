package com.wang.config;

import com.wang.mapper.RoleMapper;
import com.wang.mapper.UserMapper;
import com.wang.pojo.Role;
import com.wang.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public MyUserDetailService(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) throw new UsernameNotFoundException("没有找到用户名");
        List<Role> roles = roleMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }
}
