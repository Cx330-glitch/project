package com.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.entity.*;
import com.example.common.vo.CloudMenuVo;
import com.example.common.vo.RoleVo;
import com.example.mapper.*;
import com.example.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统-角色表 (Role)表服务实现类
 *

 */
@Transactional
@Service("RoleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private  RoleMapper cloudRoleMapper;
    @Resource
    private MenuMapper cloudMenuMapper;
    @Resource
    private UserMapper cloudUserMapper;
    @Resource
    private UserRoleMapper cloudUserRoleMapper;
    @Resource
    private RoleMenuMapper cloudRoleMenuMapper;

    /**
     * 查询用户和角色的接口
     * @return
     */
    @Override
    public Map getRoleAndUserList() {
        //查询
        // 所有role的数据
        List<RoleVo> yunRoleVoList = cloudRoleMapper.getList();
        for (RoleVo yunRolevo:yunRoleVoList) {

            //获取该权限的所有用户
            long roleId  = yunRolevo.getId();
            List<UserRole> cloudUserRoleList = cloudUserRoleMapper.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getRoleId, roleId));
            List<User> cloudUserList=new ArrayList<>();
            for (UserRole cloudUserRole:cloudUserRoleList) {
                User cloudUser = cloudUserMapper.selectById(cloudUserRole.getUserId());
                cloudUserList.add(cloudUser);
            }
            yunRolevo.setCloudUserList(cloudUserList);


            //菜单获取根节点   为了前端vue 复选框 ☒  √ 做准备 checkIdsList
            List<CloudMenuVo> menusList =  cloudMenuMapper.getMenuListById(0L);
            List<Long> checkIdsList = new ArrayList<>();
            List<CloudMenuVo> menusVoList = new ArrayList<>();
            for (CloudMenuVo yunMenusVo:menusList) {
                //获取权限和前端页面的 复选框回显做准备
                List<CloudMenuVo>  menuList = cloudMenuMapper.getMenuList(yunMenusVo.getId(),yunRolevo.getId());
                if(menuList!=null&&!menuList.equals("")){
                    for (Menu yunMenus:menuList) {
                        checkIdsList.add(yunMenus.getId());
                    }
                }
                yunMenusVo.setMenuList(menuList);

                menusVoList.add(yunMenusVo);
            }
            yunRolevo.setCheckedIds(checkIdsList);
            yunRolevo.setYunMenusVo(menusVoList);


        }
        Map map = new HashMap();
        map.put("list",yunRoleVoList);
        return map;
    }
    /**
     * 设置菜单权限，尤其是获取id
     * @param jsonObject
     * @return
     */
    @Override
    public int setMenus(JSONObject jsonObject) {
        int count =0;
        //vue的 tree的特性 "[100,101,102]"
        String ids = jsonObject.getString("ids");
        Long roleId = jsonObject.getLong("roleId");

        String[] checkIds = ids.substring(1,ids.length()-1).split(",");
        //为了保证选中的一致性
        cloudRoleMenuMapper.delete(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRId,roleId));
        for (String id:checkIds) {
            if(id!=null&&!id.equals("")){
                Long checkId = Long.valueOf(id.trim()).longValue();
                RoleMenu yunRoleMenus = new RoleMenu();
                yunRoleMenus.setRId(roleId);
                yunRoleMenus.setMId(checkId);
                int i = cloudRoleMenuMapper.insert(yunRoleMenus);
                count = count+i;
            }
        }

        return count;
    }
}