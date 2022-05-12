package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.entity.Menu;
import com.example.common.vo.CloudMenuVo;
import com.example.mapper.MenuMapper;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统-菜单表 (Menu)表服务实现类
 *
 */
@Service("MenuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper cloudMenuMapper;

//    @Override
//    public List<CloudMenu> listTreeMenu() {
//        return cloudMenuMapper.selectList(null);
//    }

    @Override
    public List<CloudMenuVo> getAllMenusByElTree() {
        //查询根节点
        List<CloudMenuVo> yunMenusVoList = cloudMenuMapper.getMenuListById(0L);
        List<CloudMenuVo> yunMenusVoList1 = new ArrayList<>();
        for ( CloudMenuVo yunMenusVo:yunMenusVoList) {
            List<CloudMenuVo> permissionList = cloudMenuMapper.getMenuListById(yunMenusVo.getId());

            yunMenusVo.setMenuList(permissionList);
            yunMenusVoList1.add(yunMenusVo);
        }


        return yunMenusVoList1;
    }

    /**
     * 菜单的树形结构
     * @return
     */
    @Override
    public Map getMenusList() {
        //查询所有根节点下面的第一层次节点
        List<CloudMenuVo> yunMenusList = cloudMenuMapper.getMenuListById(0L);
        // 查询所有记录
        List<CloudMenuVo> menusVoList = cloudMenuMapper.getList();

        for (CloudMenuVo yunMenusVo:yunMenusList) {
            List<CloudMenuVo> children = new ArrayList<>();
            for (CloudMenuVo yunMenus:menusVoList) {
                if(yunMenus.getParentId().equals(yunMenusVo.getId())){
                    children.add(yunMenus);
                }
            }
            yunMenusVo.setChildren(children);
        }
        Map map = new HashMap();
        map.put("menuTree",yunMenusList);

        return map;
    }
}