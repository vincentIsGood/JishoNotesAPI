package com.vincentcodes.jishoapi.config.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Will not redirect an unauthenticated request
 *
 * @see https://stackoverflow.com/questions/55345833/spring-security-oauth-how-to-disable-login-page
 */
public class NoRedirectionAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "JSON");
    }
}
