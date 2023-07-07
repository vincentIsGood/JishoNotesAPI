package com.vincentcodes.jishoapi.config;

import com.vincentcodes.jishoapi.config.consts.ApiEndpoints;
import com.vincentcodes.jishoapi.config.security.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Web security for this application is done here.
 *
 * <p>
 * NEW: Migration from {@code WebSecurityConfigurerAdapter} to Component-based configuration
 * <p>
 * OLD: https://github.com/spring-projects/spring-data-examples/tree/master/rest/security
 *
 * <p>
 * Spring Security - How to Fix WebSecurityConfigurerAdapter Deprecated
 * @link https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 *
 * <p>
 * Standard Spring User Schema: (not used, because we don't have role-based authorization)
 * @link https://docs.spring.io/spring-security/reference/servlet/appendix/database-schema.html
 *
 * @see com.vincentcodes.jishoapi.config.security.AuthConfig
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Value("${spring.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList(allowedOrigins));
        config.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public List<String> publicPaths(@Value("${spring.use-swagger-ui}") boolean useSwaggerUi){
        return new ArrayList<>() {
            {
                addAll(ApiEndpoints.Public.PATH_MATCHERS);
                if(useSwaggerUi)
                    addAll(ApiEndpoints.Public.SWAGGER_MATCHERS);
            }
        };
    }

    // For CORS, see https://stackoverflow.com/questions/54276986/spring-boot-security-no-access-control-allow-origin-header-when-setting-allow
    //           CrossOrigin works when the "Origin" header exists in the request.
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JsonAuthenticationFilter jsonAuthenticationFilter,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService,
            OAuth2UserService<OidcUserRequest, OidcUser> oidcUserOAuth2UserService,
            @Qualifier("publicPaths") List<String> publicPaths) throws Exception{
        // Do not create session on 401
        // https://stackoverflow.com/questions/70769950/prevent-unauthorized-http-requests-redirected-to-error-from-setting-session-coo
        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
        httpSessionRequestCache.setCreateSessionAllowed(false);

        // ".and().cors()" enable cors with our CorsConfigurationSource
        // ".and().csrf().disable()" for the moment
        // ".anyRequest().authenticated()" any request must be authenticated
        http.headers()
                .addHeaderWriter(new JsonAuthorizationHeaderWriter())
            .and().httpBasic()
                .and().exceptionHandling()
                    .authenticationEntryPoint(new NoRedirectionAuthEntryPoint())
                .and().cors()
                .and().csrf().disable()
                .requestCache().requestCache(httpSessionRequestCache)
                .and().sessionManagement()
                    .maximumSessions(2).and()
                .and().authorizeRequests()
                    .antMatchers(publicPaths.toArray(new String[0]))
                    .permitAll()
                .anyRequest().authenticated()
                .and().logout()
                    .logoutRequestMatcher(ApiEndpoints.LOGOUT)
                .and().addFilter(jsonAuthenticationFilter)
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri(ApiEndpoints.OAUTH2_INTERNAL)
                        .and()
                    .redirectionEndpoint()
                        .baseUri(ApiEndpoints.OAUTH2_CALLBACK)
                        .and()
                    .userInfoEndpoint()
                        .userService(oAuth2UserService)
                        .oidcUserService(oidcUserOAuth2UserService)
                        .and()
                    .successHandler(new RedirectionAuthSuccessHandler());
        return http.build();
    }

}
