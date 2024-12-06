package com.example.employeemanagement.service.http;

import com.example.employeemanagement.security.AuthenticationState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.employeemanagement.exception.HttpSendException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AccountProviderHttpClient {

    @Value("${service.account-provider.base-url}")
    private String accountProviderBaseUrl;

    @Value("${service.account-provider.timeout-ms}")
    private long timeoutMs;

    public void deleteRequest(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        String url = accountProviderBaseUrl + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .headers(prepareAuthenticationHeader().toString())
                .timeout(Duration.ofMillis(timeoutMs))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new HttpSendException("HTTP status code: " + response.statusCode());
            } else {
                System.out.println("Delete request successful: " + response.body());
            }
        } catch (Exception e) {
            throw new HttpSendException("Can't send request to URL: " + url + ": " + e.getMessage());
        }
    }

    public HttpHeaders prepareAuthenticationHeader() {
        HttpHeaders headers = new HttpHeaders();
        AuthenticationState authenticationState = (AuthenticationState) SecurityContextHolder.getContext()
                .getAuthentication();
        String token = "Bearer sso_1.0_" + authenticationState.getCredentials();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        return headers;
    }
}
