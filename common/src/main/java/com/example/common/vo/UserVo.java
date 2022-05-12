package com.example.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.common.entity.Role;
import com.example.common.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo extends User {


    private  Role cloudRole;
    private  Long roleId;
    private  String roleName;
}
