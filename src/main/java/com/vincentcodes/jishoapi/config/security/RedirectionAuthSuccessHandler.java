package com.vincentcodes.jishoapi.config.security;

import com.vincentcodes.jishoapi.config.consts.ApiEndpoints;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * After the whole auth process, this is invoked
 */
public class RedirectionAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", ApiEndpoints.OAUTH2_AUTH_SUCCESS_URL);
    }
}
