package com.vincentcodes.jishoapi.config.security;

import com.vincentcodes.jishoapi.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JsonAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    SimpleUserDetailsService realCredRetrievalService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AppUser clientAuthInfo = (AppUser) authentication.getPrincipal();
        if(clientAuthInfo.getName() == null
        || clientAuthInfo.getPass() == null)
            throw new BadCredentialsException("No username / password found from request");

        UserDetails realCredentials = realCredRetrievalService.loadUserByUsername(clientAuthInfo.getName());
        if(!passwordEncoder.matches(clientAuthInfo.getPass(), realCredentials.getPassword()))
            throw new BadCredentialsException("Invalid name or password");

        // ignore authorizing GrantAuthorities (or roles)
        return new UsernamePasswordAuthenticationToken(realCredentials, realCredentials.getPassword(), realCredentials.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
