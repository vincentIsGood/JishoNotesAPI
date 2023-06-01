package com.vincentcodes.jishoapi.config.consts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppRoles {
    public static final String NORMAL_USER = "NORMAL_USER";

    public static List<String> PERMITTED_ROLES = List.of(NORMAL_USER);

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities(AppRoles.PERMITTED_ROLES);
    }
    public static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : roles)
            authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
