package com.example.common.vo;
import com.example.common.entity.Role;
import com.example.common.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class RoleVo extends Role {
    private List<User> cloudUserList;
    private List<RoleMenusVo> yunRoleMenus;
    private List<CloudMenuVo> yunMenusVo;
    private List<Long> checkedIds;
}
