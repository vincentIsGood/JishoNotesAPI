package com.vincentcodes.jishoapi.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentcodes.jishoapi.entity.AppUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Basic picture of what you need to do in order to authenticate a user using custom filters
 * @link https://qiita.com/mr-hisa-child/items/4d9a46e4af32252a8987
 */
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonAuthenticationFilter(RequestMatcher loginPathMatcher, AuthenticationManager authenticationManager){
        super();
        if(loginPathMatcher != null)
            super.setRequiresAuthenticationRequestMatcher(loginPathMatcher);
        if(authenticationManager != null)
            super.setAuthenticationManager(authenticationManager);
    }

    /**
     * Just in case bean is used later on
     */
    public JsonAuthenticationFilter(){
        super();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AppUser clientAuthInfo = mapper.readValue(request.getInputStream(), AppUser.class);
            UsernamePasswordAuthenticationToken clientAuthToken = new UsernamePasswordAuthenticationToken(clientAuthInfo, null);
            setDetails(request, clientAuthToken);
            return this.getAuthenticationManager().authenticate(clientAuthToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Cannot read request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //super.unsuccessfulAuthentication(request, response, failed);
        SecurityContextHolder.clearContext();
        getRememberMeServices().loginFail(request, response);
        response.setStatus(401);
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "JSON");
    }
}
