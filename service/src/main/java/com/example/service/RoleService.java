package com.example.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.entity.Role;

import java.util.Map;

public interface RoleService extends IService<Role> {
    /**
     * 查询用户和角色的接口
     * @return
     */
    Map getRoleAndUserList();

    /**
     * 设置菜单权限，尤其是获取id
     * @param jsonObject
     * @return
     */
    int setMenus(JSONObject jsonObject);
}
