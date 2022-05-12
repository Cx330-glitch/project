package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.entity.Role;
import com.example.common.entity.RoleMenu;
import com.example.common.entity.User;
import com.example.common.entity.UserRole;
import com.example.common.vo.UserInfoVo;
import com.example.common.vo.UserVo;
import com.example.mapper.*;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper cloudUserMapper;
    @Resource
    private UserRoleMapper cloudUserRoleMapper;
    @Resource
    private RoleMapper  cloudRoleMapper;
    @Resource
    private RoleMenuMapper cloudRoleMenuMapper;
    @Resource
    private MenuMapper cloudMenuMapper;

    @Override
    public int saveUserAndRole(UserVo cloudUserVo) {
        // 插入CloudUser
        User cloudUser = new User();
        BeanUtils.copyProperties(cloudUserVo,cloudUser);
        //默认用户是启用的，非禁用
        cloudUser.setIsLock("0");
        // 加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = cloudUserVo.getPassWord();
        String  pass = bCryptPasswordEncoder.encode(pwd);
        cloudUser.setPassWord(pass);

        int i1 = cloudUserMapper.insert(cloudUser);


        // 插入CloudUserRole
        UserRole cloudUserRole = new UserRole();
        cloudUserRole.setRoleId(cloudUserVo.getRoleId());
        cloudUserRole.setUserId(cloudUser.getId());
        int i2 = cloudUserRoleMapper.insert(cloudUserRole);



        return i2;
    }

    @Override
    public int updateUserAndRole(UserVo cloudUserVo) {

        // 更新CloudUser
        User cloudUser = new User();
        BeanUtils.copyProperties(cloudUserVo,cloudUser);
        int i1= cloudUserMapper.updateById(cloudUser);

        // 更新role角色
        // 只需更新roleId
        UserRole cloudUserRole = cloudUserRoleMapper.selectOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, cloudUserVo.getId()));
        cloudUserRole.setRoleId(cloudUserVo.getRoleId());
        int i2 = cloudUserRoleMapper.updateById(cloudUserRole);

        return i2;
    }

    @Override
    public UserInfoVo getInfo(String token) {
        //获取登录者的用户信息
        User cloudUser = new User();
        cloudUser = cloudUserMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getLoginToken,token));
        // 复制到Vo中进行管理
        UserInfoVo cloudUserInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(cloudUser,cloudUserInfoVo);
        // 查询登录用户的角色
        UserRole userRole = cloudUserRoleMapper.selectOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, cloudUser.getId()));
        List<Role> cloudRoleList = cloudRoleMapper.selectList(new QueryWrapper<Role>().lambda().eq(Role::getId, userRole.getRoleId()));
        Set<String> roles = new HashSet<>();
        // 如果有多个角色可以扩展，此处用第一个角色演示
        if(cloudRoleList!=null&&cloudRoleList.size()>0){
            roles.add(cloudRoleList.get(0).getRoleEn());
            // 获取当前角色对应的有权限的菜单列表
            List<RoleMenu> cloudRoleMenuList = cloudRoleMenuMapper.selectList(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRId, cloudRoleList.get(0).getId()));

            Long rId = cloudRoleList.get(0).getId();
            //获取所有菜单
            Set<String>  getAllMenus= cloudMenuMapper.getAllMenus(rId);

            // 获取所有的授权
            Set<String>  getPermission = cloudMenuMapper.getPermission(rId);

            //vue前台处理
            cloudUserInfoVo.setMenuList(getAllMenus);
            cloudUserInfoVo.setPermissionList(getPermission);
        }

        return cloudUserInfoVo;
    }
}
