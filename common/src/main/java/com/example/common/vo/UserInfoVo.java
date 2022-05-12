package com.example.common.vo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.common.entity.Role;
import com.example.common.entity.User;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserInfoVo extends User implements Serializable {

    private Set<String> menuList;
    private Set<String> permissionList;
    @TableField(exist = false)
    private Role role;
    private Set<String> roles = Sets.newHashSet();

    private Set<MenuVo> menus = Sets.newHashSet();



}
