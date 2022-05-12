package com.example.auth;


import com.example.common.util.ResponseUtils;
import com.example.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未登录拦截
 */
@Slf4j
@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException  authenticationException) {
        if (authenticationException!=null){
            ResponseUtils.out(response, Result.createByErrorCodeMessage(401,authenticationException.getMessage()));
        } else {
            ResponseUtils.out(response, Result.createByErrorCodeMessage(-1,"token无效或者格式不对"));


        }
    }

}
