package com.example.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.common.entity.User;
import com.example.common.util.ResponseUtils;
import com.example.common.vo.Result;
import com.example.common.vo.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *  认证处理 成功跳转
 *
 */
@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException, ServletException {


        User user = new User();
        SecurityUser securityUser = ((SecurityUser) auth.getPrincipal());
        user.setLoginToken(securityUser.getLoginUser().getLoginToken());
        Map map = new HashMap();
        map.put("result","success");
        map.put("loginToken",securityUser.getLoginUser().getLoginToken());
        ResponseUtils.out(response, Result.createByCodeSuccess(200,"登录成功",map));
    }
}
