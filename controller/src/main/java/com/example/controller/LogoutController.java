package com.example.controller;


import com.example.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *  首页
 */
@RestController
@Api(produces = "登录和登出")
public class LogoutController {
    /**
     * http://127.0.0.1:9000/logout
     * @return
     */
    @RequestMapping(value = "/logout")
    @ApiOperation(value = "登出系统",  response = Result.class)
    public Result logout() {
        return Result.createBySuccess("登录系统成功", null);
    }

    /**
     * http://127.0.0.1:9000/login?logout
     * @return
     */
    @RequestMapping(value = "/login")
    @ApiOperation(value = "登录系统", response = Result.class)
    public Result login() {
        return Result.createBySuccess("登录系统成功", null);
    }

//    @RequestMapping(value = "/sql",headers = {"Content-Type:text/html;charset=utf-8"})
//    @ApiOperation(value = "sql监控", response = CloudResult.class)
//    public ModelAndView druid() {
//         ModelAndView mv  = new ModelAndView();
//        mv.setViewName("/druid/index.html");
//           return  mv;
//    }


}
