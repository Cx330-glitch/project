package com.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.entity.History;
import com.example.common.vo.Result;
import com.example.service.HistoryService;
import com.example.common.entity.History;
import com.example.common.vo.Result;
import com.example.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 系统 - 日志表(CloudHistory)表控制层
 *
 * @author Array老师
 * @since 2020-03-29 19:56:29
 */
@RestController
@RequestMapping("/rest/history")
@Api(produces = "系统管理 - 日志表接口")
public class HistoryController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private HistoryService cloudHistoryService;



    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */

    @PostMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取系统管理 - 日志表列表分页", httpMethod = "POST", response = Result.class)
    public Result selectAll(@RequestBody JSONObject jsonObject) {
        if (jsonObject != null && !jsonObject.equals("")) {
            int pageNum = jsonObject.getInteger("pageNum");
            int pageRow = jsonObject.getInteger("pageRow");

            IPage<History> iPage = new Page<History>(pageNum, pageRow);
            IPage<History> page = cloudHistoryService.page(iPage,new QueryWrapper<History>().lambda().orderByDesc(History::getCreateTime));
            return Result.createBySuccess("查询成功！", page);

        } else {
            return null;
        }
    }



}