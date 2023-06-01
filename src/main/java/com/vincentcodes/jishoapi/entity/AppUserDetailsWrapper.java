package com.vincentcodes.jishoapi.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Wrap my original {@link AppUser}. This is the principal of
 * {@link Authentication}
 */
public class AppUserDetailsWrapper extends User implements AppUserObtainable {
    private final AppUser appUser;

    public AppUserDetailsWrapper(String username, String password, Collection<? extends GrantedAuthority> authorities, AppUser user) {
        super(username, password, authorities);
        this.appUser = user;
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
