package com.vincentcodes.jishoapi.config.security;

import com.vincentcodes.jishoapi.config.consts.ApiEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * <p>
 * What type is used for AuthenticationManager
 * @link https://stackoverflow.com/questions/31826233/custom-authentication-manager-with-spring-security-and-java-configuration
 *
 * <p>
 * Customizing OAuth2 by code:
 * https://docs.spring.io/spring-security/reference/servlet/oauth2/client/core.html#oauth2Client-authorized-repo-service
 * https://docs.spring.io/spring-security/site/docs/5.0.7.RELEASE/reference/html/oauth2login-advanced.html#oauth2login-advanced-map-authorities-oauth2userservice
 *
 * @see JsonAuthenticationFilter
 * @see JsonAuthenticationProvider
 */
@Configuration
public class AuthConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //@Bean
    //public InMemoryUserDetailsManager authenticatedUsers(){
    //    UserDetails user = User.withUsername("user").password(passwordEncoder().encode("vws")).roles("USER").build();
    //    return new InMemoryUserDetailsManager(user);
    //}
    @Bean
    public SimpleUserDetailsService authenticatedUsers(){
        return new SimpleUserDetailsService();
    }

    @Bean
    public AuthenticationManager authenticationManager(JsonAuthenticationProvider authProvider) {
        return new ProviderManager(authProvider);
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter(AuthenticationManager authenticationManager){
        return new JsonAuthenticationFilter(ApiEndpoints.LOGIN, authenticationManager);
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserFetcher(){
        return new OAuth2UserFetcher();
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserOAuth2UserService(){
        return new OAuth2OidcUserFetcher();
    }

}
