package com.vincentcodes.jishoapi.config.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonAuthorizationHeaderWriter implements HeaderWriter {
    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if(response.containsHeader(HttpHeaders.WWW_AUTHENTICATE))
            response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "JSON");
    }
}
