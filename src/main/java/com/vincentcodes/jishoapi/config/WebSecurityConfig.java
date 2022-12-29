package com.vincentcodes.jishoapi.config;

import com.vincentcodes.jishoapi.config.consts.ApiEndpoints;
import com.vincentcodes.jishoapi.config.security.JsonAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * <p>
 * NEW: Migration from {@code WebSecurityConfigurerAdapter} to Component-based configuration
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
        config.setAllowedOrigins(List.of(allowedOrigins));
        config.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // For CORS, see https://stackoverflow.com/questions/54276986/spring-boot-security-no-access-control-allow-origin-header-when-setting-allow
    //           CrossOrigin works when the "Origin" header exists in the request.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{
        // Do not create session on 401
        // https://stackoverflow.com/questions/70769950/prevent-unauthorized-http-requests-redirected-to-error-from-setting-session-coo
        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
        httpSessionRequestCache.setCreateSessionAllowed(false);

        // ".and().cors()" override all CrossOrigin policies (for production use only)
        // ".and().csrf().disable()" for the moment
        // ".anyRequest().authenticated()" any request must be authenticated
        http.httpBasic()
                .and().cors()
                .and().csrf().disable()
                .requestCache().requestCache(httpSessionRequestCache)
                .and().sessionManagement()
                    .maximumSessions(2).and()
                .and().authorizeRequests()
                    .antMatchers(ApiEndpoints.Public.PATH_MATCHERS.toArray(new String[0]))
                    .permitAll()
                .anyRequest().authenticated()
                .and().logout()
                    .logoutRequestMatcher(ApiEndpoints.LOGOUT)
                .and().addFilter(new JsonAuthenticationFilter(ApiEndpoints.LOGIN, authenticationManager));
        //http.addFilterAfter(new AppSessionAuthFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

}
