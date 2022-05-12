package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.entity.Role;
import com.example.common.entity.User;
import com.example.common.entity.UserRole;
import com.example.common.vo.SecurityUser;
import com.example.mapper.RoleMapper;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 认证用户
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper cloudUserMapper;  //用户mapper
    @Autowired
    private RoleMapper cloudRoleMapper; // 查询角色
    @Autowired
    private UserRoleMapper cloudUserRoleMapper; //查询用户和角色对应的关系 ，是否含有该角色

    /***
     * 根据账号获取用户信息
     * @param accountName:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        List<User> userList = cloudUserMapper.selectList(new QueryWrapper<User>().eq("account_name", accountName));
        User user;
        // 判断用户是否存在
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名数据库未查到，请注册后再试！");
        }
        // 返回UserDetails实现类
        return new SecurityUser(user, getUserAndRole(user.getId()));
    }

    /***
     * 根据token获取用户权限
     *
     * @param token:
     */
    public SecurityUser checkAccountByToken(String token) {
        User user = null;
        List<User> loginList = cloudUserMapper.selectList(new QueryWrapper<User>().eq("login_token", token));
        if (!CollectionUtils.isEmpty(loginList)) {
            user = loginList.get(0);
        }
        return user != null ? new SecurityUser(user, getUserAndRole(user.getId())) : null;
    }

    /**
     *
     * 角色信息获取存储
     */
    private List<Role> getUserAndRole(Long  userId) {
        List<UserRole> userRoles = cloudUserRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
        List<Role> roleList = new LinkedList<>();
        for (UserRole userRole : userRoles) {
            Role role = cloudRoleMapper.selectById(userRole.getRoleId());
            roleList.add(role);
        }
        return roleList;
    }

}
