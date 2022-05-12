package com.example.auth;

import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 自定义认证管理器
 */
@Component
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {

    private final AuthenticationProvider cloudAuthenticationProvider;

    public AuthenticationManager(AuthenticationProvider authenticationProvider) {
        this.cloudAuthenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = cloudAuthenticationProvider.authenticate(authentication);
        if (Objects.nonNull(result)) {
            return result;
        }
        throw new ProviderNotFoundException("权限验证失败，请确认是否数据库或者菜单授权!");
    }

}
