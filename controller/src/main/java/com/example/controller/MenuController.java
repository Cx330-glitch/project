package com.example.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.entity.Menu;
import com.example.common.vo.MenuVo;
import com.example.common.vo.Result;
import com.example.service.MenuService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统-菜单表 (CloudMenu)表控制层
 *
 * @author Array老师
 * @since 2020-03-21 15:19:29
 */
@RestController
@RequestMapping("/rest/menus")
@Api(produces = "系统-菜单表表接口")
public class MenuController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService cloudMenuService;


    @PostMapping(value = "/list")
    @ApiOperation(value = "获取菜单树", httpMethod = "POST", response = Result.class)
    public Result list(){
        Map map  = cloudMenuService.getMenusList();
        return  Result.createBySuccess("执行成功！",map);
    }

    /**
     * 新增数据
     *
     * @param cloudMenu 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存菜单 ", httpMethod = "POST", response = Result.class)
    public Result insert(@RequestBody Menu cloudMenu) {
        return Result.createBySuccess(this.cloudMenuService.save(cloudMenu));
    }

    /**
     * 修改数据
     *
     * @param cloudMenu 实体对象
     * @return 修改结果
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "保存菜单 ", httpMethod = "POST", response = Result.class)
    public Result update(@RequestBody Menu cloudMenu) {
        return Result.createBySuccess(this.cloudMenuService.updateById(cloudMenu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除菜单", httpMethod = "POST", response = Result.class)
    public Result delete(@RequestParam("idList") List<Long> idList) {

        for (Long id:idList) {
            // 如果该菜单下存在子菜单，提示先删除子菜单
            List<Menu> menuList = cloudMenuService.list(new QueryWrapper<Menu>().eq("parent_id", id));
            if (!CollectionUtils.isEmpty(menuList)){
                return Result.createByErrorMessage("请先删除子菜单！再重试！");
            }
            boolean removeById = cloudMenuService.removeById(id);

        }
        return Result.createByCodeSuccess(1,"删除成功",true);
    }

}