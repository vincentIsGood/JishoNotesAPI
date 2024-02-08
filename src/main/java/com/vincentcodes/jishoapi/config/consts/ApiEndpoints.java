package com.vincentcodes.jishoapi.config.consts;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

public final class ApiEndpoints {
    public final static AntPathRequestMatcher LOGIN = new AntPathRequestMatcher("/jishonotes/auth/login", "POST");
    public final static AntPathRequestMatcher LOGOUT = new AntPathRequestMatcher("/jishonotes/auth/logout", "POST");

    // Spring built-in paths
    // Ref: https://docs.spring.io/spring-security/site/docs/5.0.7.RELEASE/reference/html/oauth2login-advanced.html
    // String OAUTH2_INTERNAL = "/oauth2/authorization/<client>"
    // String OAUTH2_CALLBACK = "/login/oauth2/code/<client>"

    // Special OAuth2 paths (modified the above paths)
    // "/jishonotes/oauth2/authorization/<client>"
    public final static String OAUTH2_INTERNAL = "/jishonotes/oauth2/authorization";
    public final static String OAUTH2_CALLBACK = "/jishonotes/login/oauth2/code/*";

    public final static String OAUTH2_GENERAL_LOGIN_URL = "/jishonotes/login/oauth2";
    public final static String OAUTH2_AUTH_SUCCESS_URL = "/jishonotes/oauth2/success/redirect";

    public final static class Public{
        public final static List<String> PATH_MATCHERS = List.of(
                "/public/**",
                "/jishonotes/v2/notes/**",
                "/jishonotes/v2/entries/**",
                "/jishonotes/v1/analyze/**",
                "/jishonotes/v1/news/**",
                "/jishonotes/v1/users/loginstatus",
                OAUTH2_GENERAL_LOGIN_URL
        );

        public final static List<String> SWAGGER_MATCHERS = List.of(
                // https://stackoverflow.com/questions/47425048/why-does-springfox-swagger2-ui-tell-me-unable-to-infer-base-url
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v2/api-docs"
        );
    }

    // Actively used (ie. not deprecated) endpoints
    //public final static class AuthRequired {
    //    public final static List<String> PATH_MATCHERS = List.of(
    //            "/jishonotes/v1/users/**",
    //            "/jishonotes/v1/flashcards/**",
    //            "/jishonotes/v1/gpt/**",
    //            "/jishonotes/v1/games/**"
    //    );
    //}
}
