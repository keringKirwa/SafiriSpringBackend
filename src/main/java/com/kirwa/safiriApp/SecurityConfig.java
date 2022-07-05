
package com.kirwa.safiriApp;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*TODO: in all applications , the error equired a bean of type ... which was not provided is an
*  error associates with annotations. @EnableWebSec was one of them.*/

@EnableWebSecurity
public class SecurityConfig {
    private static final String[] WHITE_LIST_URLS = {
            "/users*",
            "/h2-console/login.do"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(WHITE_LIST_URLS).permitAll();
        return http.build();
    }
}
