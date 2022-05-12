package com.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.entity.Role;
import com.example.common.entity.User;
import com.example.common.entity.UserRole;
import com.example.common.vo.Result;
import com.example.common.vo.UserInfoVo;
import com.example.common.vo.UserVo;
import com.example.service.RoleService;
import com.example.service.UserRoleService;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/rest/user")
@Api(value="用户controller",tags={"用户操作接口"})
public class UserController extends ApiController {

    @Autowired
    private UserService cloudUserService;
    @Autowired
    private UserRoleService cloudUserRoleService;
    @Autowired
    private RoleService cloudRoleService;
    /**
     * 用户列表
     */
    @RequestMapping("/list")
    @ApiOperation(value ="用户列表不分页", httpMethod = "POST",produces = "用户列表不分页")
    public Result getList() {

        List<User> cloudUserList = cloudUserService.list();
        return  Result.createByCodeSuccess(1,"用户列表查询成功",cloudUserList);

    }

    /**
     * page分页例子，前端vue传值，json格式
     */
    @PostMapping("/page")
    @ApiOperation(value = "用户列表分页查询",httpMethod = "POST",produces = "用户分页")
    public Result page(@RequestBody JSONObject jsonObject){
        //获取当前页
        if(jsonObject!=null&&!jsonObject.equals("")) {
            //每页多少条
            int pageNum = jsonObject.getInteger("pageNum");
            int pageRow = jsonObject.getInteger("pageRow");
            //模糊查询的字段，如accountName
            String accoutName = jsonObject.getString("accoutName");

            IPage<User> iPage= new Page<>(pageNum,pageRow);
            IPage<User> page;
            if(accoutName==null||accoutName.equals("")){
                page = cloudUserService.page(iPage);
            }else {
                page = cloudUserService.page(iPage,new QueryWrapper<User>().lambda().like(User::getAccountName,accoutName));
            }

            //查询所有用户的信息 封装在page中
            //查询当前用户的角色，权限菜单
            // 根据userId查询对于的RoleId和Role信息
            List<User> cloudUserList = page.getRecords();
            List list = new ArrayList();
            for(User cloudUser:cloudUserList) {
                //查询roleId
                UserRole userRole = cloudUserRoleService.getOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, cloudUser.getId()));
                //根据roleId查询role角色信息
                Role cloudRole = cloudRoleService.getById(userRole.getRoleId());
                UserVo cloudUserVo = new UserVo();
                BeanUtils.copyProperties(cloudUser,cloudUserVo);
                cloudUserVo.setRoleName(cloudRole.getRoleCn());
                cloudUserVo.setRoleId(cloudRole.getId());
                cloudUserVo.setCloudRole(cloudRole);
                list.add(cloudUserVo);
            }
            page.setRecords(list);
            return  Result.createByCodeSuccess(1,"执行成功！",page);
        } else {
            return null;
        }


    }

    /**
     * 用户表的插入
     */
    @PostMapping("/save")
    @ApiOperation(value = "用户插入",httpMethod = "POST",produces = "用户新增")
    public  Result saveUserAndRole(@RequestBody UserVo cloudUserVo){
        int i = 0;
        if(cloudUserVo!=null&&!cloudUserVo.equals("")){
            i = cloudUserService.saveUserAndRole(cloudUserVo);
        }
        if(i>0) {
            return  Result.createByCodeSuccess(1,"执行成功！",i);
        } else {
            return  Result.createByError();
        }

    }

    /**
     * 用户的更新
     */

    @PostMapping("/update")
    @ApiOperation(value = "用户更新",httpMethod = "POST",produces = "用户更新")
    public  Result updateUserAndRole(@RequestBody UserVo cloudUserVo){
        int i = 0;
        if(cloudUserVo!=null&&!cloudUserVo.equals("")){
            i = cloudUserService.updateUserAndRole(cloudUserVo);
        }
        if(i>0) {
            return  Result.createByCodeSuccess(1,"执行成功！",i);
        } else {
            return  Result.createByError();
        }

    }

    /**
     * 用户的删除
     */

    @PostMapping("/delete")
    @ApiOperation(value = "用户删除",httpMethod = "POST",produces = "用户删除")
    public  Result delete(@RequestBody   UserVo cloudUserVo){
        if(cloudUserVo!=null&&!cloudUserVo.equals("")) {
            // 删除角色
            UserRole userRole = cloudUserRoleService.getOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, cloudUserVo.getId()));
            cloudUserRoleService.removeById(userRole.getId());

            // 删除用户
            boolean removeById = cloudUserService.removeById(cloudUserVo.getId());
            if (removeById) {
                return Result.createByCodeSuccess(1, "执行成功！", removeById);
            } else {
                return Result.createByError();
            }
        } else {
            return  Result.createByError();
        }

    }

    /**
     * 获取当前用户的登录信息
     */
    @PostMapping("/getInfo")
    @ApiOperation(value = "获取当前登录用户的信息",httpMethod = "POST",produces = "获取当前登录用户的信息")
    public  Result getInfo(@RequestHeader(name = "auth-token") String token){
        // 获取当前登录者的用户名，密码，性别，电话..... 角色，对应能访问的菜单，创建时间等
        UserInfoVo cloudUserInfoVo =  cloudUserService.getInfo(token);
        return Result.createByCodeSuccess(1, "执行成功！",cloudUserInfoVo);

    }
}
