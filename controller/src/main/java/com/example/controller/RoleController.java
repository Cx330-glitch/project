package com.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.example.common.entity.Role;
import com.example.common.vo.CloudMenuVo;
import com.example.common.vo.Result;
import com.example.service.MenuService;
import com.example.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 系统-角色表 (CloudRole)表控制层
 *
 * @author Array老师
 * @since 2020-03-21 14:52:29
 */
@RestController
@RequestMapping("/rest/role")
@Api(produces = "系统-角色表 接口")

public class RoleController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService cloudRoleService;

    @Resource
    private MenuService cloudMenuService;

    /**
     * 不分页查询所有数据： 获取角色里面有多少个用户是该角色   admin  3人（张三，李四，王五）
     *
     * @return 所有数据
     */
    @PostMapping(value = "/list")
    @ApiOperation(value = "角色表 列表不分页", httpMethod = "POST", response = Result.class)
    public Result list() {
        Map map = cloudRoleService.getRoleAndUserList();
        return  Result.createBySuccess("查询成功！",map);
    }

    /**
     * 查询不分页数据
     */
    @RequestMapping(value = "/commonList")
    @ApiOperation(value = "角色表 列表 角色本身不含其它", httpMethod = "POST", response = Result.class)
    public Result getList(){
        List<Role> list = cloudRoleService.list();
        return  Result.createByCodeSuccess(1,"查询成功",list);
    }


    @PostMapping(value = "/getAllMenus")
    @ApiOperation(value = "角色表 列表 菜单", httpMethod = "POST", response = Result.class)
    public Result getAllMenus(){
        List<CloudMenuVo> cloudMenusVo = cloudMenuService.getAllMenusByElTree();
        return  Result.createByCodeSuccess(1,"查询成功",cloudMenusVo);
    }

    /**
     * 分配菜单权限
     */
    @RequestMapping("/setMenus")
    @ApiOperation(value = "获取系统管理-角色表 列表", httpMethod = "POST", response = Result.class)
    public Result setMenus(@RequestBody JSONObject jsonObject){
        int i = cloudRoleService.setMenus(jsonObject);
        if(i>0){
            return  Result.createBySuccess("执行成功！",i);
        }
        return  Result.createByError();
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "获取角色信息", httpMethod = "POST", response = Result.class)
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.createBySuccess(this.cloudRoleService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存角色", httpMethod = "POST", response = Result.class)
    public Result insert(@RequestBody  JSONObject jsonObject){
        if(jsonObject!=null&&!jsonObject.equals("")) {

            // 角色的名称
            String roleName = jsonObject.getString("roleName");
            // 0 启动 1 禁用
            String is_enable = jsonObject.getString("isEnable");
            //角色的描述
            String describeText = jsonObject.getString("describeText");
            Role yunRole1 = new Role();
            yunRole1.setRoleCn(roleName);
            yunRole1.setDescribeText(describeText);
            if(is_enable!=null&&!is_enable.equals("")){
                if(is_enable.equals("true")) {
                    yunRole1.setIsEnable("0");//表示启用
                } else {
                    yunRole1.setIsEnable("1");//表示禁用
                }
            }
            boolean saveOrUpdate = cloudRoleService.saveOrUpdate(yunRole1);
            return  Result.createBySuccess("执行成功！",saveOrUpdate);

        }

        return Result.createByError();
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "更新角色", httpMethod = "POST", response = Result.class)
    public Result update(@RequestBody JSONObject jsonObject) {
        if(jsonObject!=null&&!jsonObject.equals("")) {

            String roleName = jsonObject.getString("roleCn");
            Long id = jsonObject.getLong("id");
            String is_enable = jsonObject.getString("isEnable");
            String describeText = jsonObject.getString("describeText");
            Role yunRole1 = new Role();
            yunRole1.setId(id);
            yunRole1.setRoleCn(roleName);
            yunRole1.setDescribeText(describeText);
            if(is_enable!=null&&!is_enable.equals("")){
                if(is_enable.equals("true")) {
                    yunRole1.setIsEnable("0");//表示启用
                } else {
                    yunRole1.setIsEnable("1");//表示禁用
                }
            }
            boolean saveOrUpdate = cloudRoleService.saveOrUpdate(yunRole1);
            return  Result.createBySuccess("执行成功！",saveOrUpdate);

        }

        return Result.createByError();
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除角色 ", httpMethod = "POST", response = Result.class)
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.createBySuccess(this.cloudRoleService.removeByIds(idList));
    }
}