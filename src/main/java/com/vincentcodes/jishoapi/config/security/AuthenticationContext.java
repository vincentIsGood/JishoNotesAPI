package com.vincentcodes.jishoapi.config.security;

import com.vincentcodes.jishoapi.entity.AppUserObtainable;
import com.vincentcodes.jishoapi.exception.InvalidOperation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @link https://www.baeldung.com/get-user-in-spring-security
 */
@Component
public class AuthenticationContext {
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isLoggedIn(){
        Authentication authentication = getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated())
            return false;
        return authentication.getPrincipal() instanceof AppUserObtainable;
    }

    public AppUserObtainable getLoggedInUserInfo(){
        Authentication authentication = getAuthentication();
        if(authentication.getPrincipal() instanceof AppUserObtainable)
            return (AppUserObtainable) authentication.getPrincipal();
        throw new InvalidOperation("User is not authorized");
    }

    public UUID getLoggedInUserId(){
        return getLoggedInUserInfo().getAppUser().getUserId();
    }
}
