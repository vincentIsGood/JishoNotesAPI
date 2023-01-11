package com.vincentcodes.jishoapi.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @link https://www.baeldung.com/get-user-in-spring-security
 */
@Component
public class AuthenticationContext {
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
