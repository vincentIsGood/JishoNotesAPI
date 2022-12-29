package com.vincentcodes.jishoapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * What to use for AuthenticationManager
 * @link https://stackoverflow.com/questions/31826233/custom-authentication-manager-with-spring-security-and-java-configuration
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

}
