package com.example.hw12spring_security_acl.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/api/book")).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(antMatcher("/api/book/*/delete")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(antMatcher("/api/comment")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(antMatcher("/api/dic/genres")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(antMatcher("/api/dic/authors")).hasAuthority("ROLE_ADMIN")
                        .anyRequest().hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(Customizer.withDefaults())
                .build();
    }
}