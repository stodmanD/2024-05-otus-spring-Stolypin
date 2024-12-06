package com.example.employeemanagement.security;

import com.example.employeemanagement.security.handler.CustomAccessDeniedHandler;
import com.example.employeemanagement.security.handler.RestAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UidmWebSecurityConfigurer {

    private static final RequestMatcher ADMIN_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/employee/**")
            , new AntPathRequestMatcher("/office/**")
            , new AntPathRequestMatcher("/personal_info/**")
            , new AntPathRequestMatcher("/department/**")
    );

    @Bean
    public SecurityFilterChain uidmFilterChain(HttpSecurity http, UidmAuthenticationFilter uidmFilter) throws Exception {

        http
                .csrf(csrf -> csrf.requireCsrfProtectionMatcher(new ModdedRequestMatcher()))

                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(ADMIN_URLS).hasAnyAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterAfter(uidmFilter, BasicAuthenticationFilter.class)
                .httpBasic(basic -> basic.authenticationEntryPoint(authenticationEntryPoint()))
                .exceptionHandling(handler -> handler.accessDeniedHandler(accessDeniedHandler()))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();

    }

    @Bean
    RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    static class ModdedRequestMatcher implements RequestMatcher {

        private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS|DELETE|POST|PUT|PATCH)$");

        private final Pattern allowedEndpoints = Pattern.compile("^(api/|arm/|admin/)$");


        @Override
        public boolean matches(HttpServletRequest request) {
            String uri = request.getRequestURI().replaceFirst(request.getContextPath(), "");
            return !allowedEndpoints.matcher(uri).matches() && !allowedMethods.matcher(request.getMethod()).matches();
        }
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}