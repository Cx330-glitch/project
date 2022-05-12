package com.example.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.entity.Menu;
import com.example.common.entity.Role;
import com.example.common.entity.RoleMenu;
import com.example.mapper.MenuMapper;
import com.example.mapper.RoleMapper;
import com.example.mapper.RoleMenuMapper;
import com.example.set.GlobalConstants;
import com.example.set.IgnoreUrlConfig;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *  用户鉴权通过过滤器进行，过滤
 */
@Component
public class FilterInvocationSecurityMetadataSource implements org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource {

    @Resource
    MenuMapper cloudMenuMapper;
    @Resource
    RoleMenuMapper cloudRoleMenuMapper;
    @Resource
    RoleMapper cloudRoleMapper;
    @Resource
    IgnoreUrlConfig ignoreUrlConfig;

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        for (String ignoreUrl : ignoreUrlConfig.getNocheck().getIgnoreUrls()) {
            if (ignoreUrl.equals(requestUrl)){
                return null;
            }
        }
        if (requestUrl.contains(GlobalConstants.SECURITY_LONGIN_URL)){
            return null;
        }
        List<Menu> permissionList = cloudMenuMapper.selectList(null);
        // 从数据库中进行动态获取，也可以从其他接口项目获取
        for (Menu permission : permissionList) {
            if ((permission.getMenuPath()).equals(requestUrl)) {
                List<RoleMenu> permissions = cloudRoleMenuMapper.selectList(
                        new QueryWrapper<RoleMenu>().eq(GlobalConstants.SECURITY_MENU_ID, permission.getId()));
                // 地址和权限对应处理
                List<String> roles = new LinkedList<>();
                if (!CollectionUtils.isEmpty(permissions)){
                    permissions.forEach( e -> {
                        Long roleId = e.getRId();
                        Role role = cloudRoleMapper.selectById(roleId);
                        roles.add(role.getRoleEn());
                    });
                }
                // 角色-权限信息的集合处理
                return SecurityConfig.createList(roles.toArray(new String[roles.size()]));
            }
        }
        return SecurityConfig.createList(GlobalConstants.SECURITY_ROLE_LOGIN);
    }


}
