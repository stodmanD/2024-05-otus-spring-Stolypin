package com.example.accountprovider.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class UidmAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isBearerToken(authHeader)) {

            var authToken = extractToken(authHeader);
            boolean auth = authToken.equals("7f1f4913-ef31-4988-9aa6-fe76ffeb710f");
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (auth) {
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            }

            var authenticationState = new AuthenticationState(authorities);
            authenticationState.setCredentials(authToken);
            log.info("Authenticated user with auth :{}", authToken);
            authenticationState.setAuthenticated(auth);
            SecurityContextHolder.getContext().setAuthentication(authenticationState);

        }

        filterChain.doFilter(request, response);
    }

    private boolean isBearerToken(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    private String extractToken(String authHeader) {
        return authHeader.replaceAll("Bearer ", "").replace("sso_1.0_", "");
    }


}