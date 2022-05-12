package com.example.service.log;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.entity.History;
import com.example.common.entity.User;
import com.example.common.util.IpUtils;
import com.example.common.vo.Result;
import com.example.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志处理
 */
@Aspect
@Configuration
@Slf4j
public class HistoryAspect {

    @Resource
    UserMapper cloudUserMapper;

    @Pointcut("execution(* com.example.controller.*Controller.*(..)))")
    public void HistoryAspect() {
    }

    @Around(value = "HistoryAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        // token
        String url = request.getRequestURL().toString();
        String ip = IpUtils.getIpAdrress(request);
        String token = request.getHeader("auth-token");

        // AOP反射机制获取织入点处
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 进行反射和方法的获取
        Method method = signature.getMethod();
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        String methodName = "";
        if (apiOperation != null) {
            methodName = apiOperation.value();
        }
        long startTime = System.currentTimeMillis();
        Result result = (Result) joinPoint.proceed(joinPoint.getArgs());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        History cloudHistory = new History();
        cloudHistory.setServiceName(methodName);
        cloudHistory.setServiceUrl(url);
        cloudHistory.setRequestIp(ip);
        cloudHistory.setCreateTime(new Date());
        cloudHistory.setUpdateTime(new Date());
        if (token == null) {
            cloudHistory.setUserId(0L);
        } else {
            User selectOne = cloudUserMapper.selectOne(new QueryWrapper<User>().eq("login_token", token));
            if (selectOne != null) {
                cloudHistory.setUserId(selectOne.getId());
            }
        }
        cloudHistory.setRunStatus((int) result.getStatus());
        cloudHistory.setConsumingTime(totalTime + " ms");
        cloudHistory.insert();
        return result;
    }

}