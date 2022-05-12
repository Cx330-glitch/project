package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.entity.Menu;
import com.example.common.vo.CloudMenuVo;

import java.util.List;
import java.util.Map;

/**
 * 系统-菜单表 (Menu)表服务接口
 *
 */
public interface MenuService extends IService<Menu> {

    List<CloudMenuVo> getAllMenusByElTree();
    Map getMenusList();
}