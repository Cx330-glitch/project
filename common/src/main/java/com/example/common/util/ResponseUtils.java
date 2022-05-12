package com.example.common.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.example.common.vo.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 封装的输出工具类
 */
@Slf4j
public class ResponseUtils {
    public static void out(ServletResponse response, Result cloudResult) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(cloudResult));
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
