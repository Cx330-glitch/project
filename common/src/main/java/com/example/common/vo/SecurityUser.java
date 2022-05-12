package com.example.common.vo;

import com.example.common.entity.Role;
import com.example.common.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 *  获取登录用户的详情信息
 * 对应的变量就可以加上transient关键字。这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化。
 */
@Data
@Slf4j
public class SecurityUser implements UserDetails, Serializable {
    /**
     * 当前login用户
     */
    private transient User loginUser;
    /**
     * 角色
     */
    private transient List<Role> roleList;
    /**
     * 角色代号
     */
    private transient String role_en;

    /**
     * 状态:0正常  1禁用
     */
    private String isLock;

    public SecurityUser() { }

    public SecurityUser(User user) {
        if (user != null) {
            this.loginUser = user;
        }
    }

    public SecurityUser(User user, List<Role> roleList) {
        if (user != null) {
            this.loginUser = user;
            this.roleList = roleList;
            if (!CollectionUtils.isEmpty(roleList)){
                StringJoiner roleCodes = new StringJoiner(",", "[", "]");
                roleList.forEach(e -> roleCodes.add(e.getRoleEn()));
                this.role_en = roleCodes.toString();
            }
        }
    }

    /**
     * 当前登录用户的角色合集
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(this.roleList)) {
            for (Role role : this.roleList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleEn());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginUser.getPassWord();
    }

    @Override
    public String getUsername() {
        return loginUser.getAccountName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
