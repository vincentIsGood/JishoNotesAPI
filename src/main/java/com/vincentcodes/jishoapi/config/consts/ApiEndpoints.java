package com.vincentcodes.jishoapi.config.consts;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

public final class ApiEndpoints {
    public final static AntPathRequestMatcher LOGIN = new AntPathRequestMatcher("/jishonotes/auth/login", "POST");
    public final static AntPathRequestMatcher LOGOUT = new AntPathRequestMatcher("/jishonotes/auth/logout", "POST");

    public final static class Public{
        public final static List<String> PATH_MATCHERS = List.of(
                "/jishonotes/v2/notes/**",
                "/jishonotes/v2/entries/**",
                "/jishonotes/v1/analyze/**",
                "jishonotes/v1/news/**",
                "/jishonotes/v1/users/loginstatus"
        );
    }

    // Actively used (ie. not deprecated) endpoints
    public final static class AuthRequired {
        public final static List<String> PATH_MATCHERS = List.of(
                "/jishonotes/v1/users/**",
                "/jishonotes/v1/games/**",
                "/jishonotes/v1/flashcards/**"
        );
    }
}
