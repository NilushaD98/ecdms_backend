package com.ecdms.ecdms.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // Check if the request is from mobile (based on a custom header)
        String deviceType = request.getHeader("X-Device-Type");

        if ("MOBILE".equalsIgnoreCase(deviceType)) {
            // Send custom JSON response for mobile
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", "401");
            errorResponse.put("message", "Invalid username or password");

            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } else {
            // Default 403 error for web
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid username or password");
        }
    }
}