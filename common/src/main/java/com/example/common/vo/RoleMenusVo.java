package com.example.common.vo;

import com.example.common.entity.RoleMenu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoleMenusVo extends RoleMenu {
    private List<CloudMenuVo> yunMenus;
    private  List<String> children;
}
