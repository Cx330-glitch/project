package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.entity.User;
import com.example.common.vo.UserInfoVo;
import com.example.common.vo.UserVo;

/**
 * 用户的service接口
 */
public interface UserService extends IService<User> {
    int saveUserAndRole(UserVo cloudUserVo);

    int updateUserAndRole(UserVo cloudUserVo);

    UserInfoVo getInfo(String token);
}
