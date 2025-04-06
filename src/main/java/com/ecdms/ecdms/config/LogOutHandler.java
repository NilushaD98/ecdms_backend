/*
 * Copyright (c) 2024. Property of EPIC Lanka Technologies Pvt. Ltd.
 */
package com.ecdms.ecdms.config;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.entity.TokenHistory;
import com.ecdms.ecdms.repository.TokenHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Designated to Handle Logout
 *
 * @author Nilusha Dissanayake
 * @version 1.0.0
 * @since 2.0.0
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LogOutHandler implements LogoutHandler {

    private final TokenHistoryRepository tokenHistoryRepository;

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        if(StringUtils.hasText(request.getHeader("Authorization")) && request.getHeader("Authorization").startsWith("Bearer ")){
            return request.getHeader("Authorization").substring(7);
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
        final String authHeader =request.getHeader("Authorization");
        final String jwt;
        if(authHeader != null && !authHeader.startsWith("Bearer ")){
            return;
        }
        jwt = getJwtFromRequest(request);
        TokenHistory token = tokenHistoryRepository.findTokenHistoryByTokenUUIDEquals(jwt).get();
        token.setStatus(false);
        token.setRevokeStatus(true);
        token.setExpiredAt(LocalDateTime.now(ZoneId.of("Asia/Colombo")));
        tokenHistoryRepository.save(token);
        SecurityContextHolder.clearContext();


        StandardResponse apiResponseDTO = new StandardResponse(200,true,"Logout successful.");
        response.setStatus(HttpServletResponse.SC_OK);
        writeJsonResponse(response, apiResponseDTO);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            StandardResponse failureResponse = new StandardResponse(false,"An error occurred during logout.");
            try {
                writeJsonResponse(response, failureResponse);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }
    private void writeJsonResponse(HttpServletResponse response, Object dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }

}
