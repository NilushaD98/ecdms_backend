/*
 * Copyright (c) 2024. Property of EPIC Lanka Technologies Pvt. Ltd.
 */

package com.ecdms.ecdms.config;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.entity.TokenHistory;
import com.ecdms.ecdms.repository.TokenHistoryRepository;
import com.ecdms.ecdms.service.TokenService;
import com.ecdms.ecdms.util.AuthorizedUserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Filter incoming requests and fetch and prepare user token and user details
 *
 * @author Ayesh Jayasekara
 * @version 1.0.0
 * @since V2.0.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenProvider;
    private final TokenHistoryRepository tokenHistoryRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if(
                    request.getServletPath().equals("/auth/login")
            ){
                filterChain.doFilter(request,response);
                return;
            }

            Optional<TokenHistory> tokenHistoryByTokenUUIDEquals = tokenHistoryRepository.findTokenHistoryByTokenUUIDEquals(getJwtFromRequest(request));
            if (tokenHistoryByTokenUUIDEquals.isEmpty()){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                StandardResponse failureResponse = new StandardResponse(false,"Unauthorized");
                try {
                    writeJsonResponse(response, failureResponse);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            String jwt = tokenHistoryByTokenUUIDEquals.get().getToken();
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                UserDetails userDetails = tokenHistoryByTokenUUIDEquals.get().getUser();
                AuthorizedUserContext.setUser(userDetails.getUsername());
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }


    /**
     * Fetch Token from the incoming request
     * @param request Incoming Request
     * @return Token extracted from the request
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("token = "+ bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        if(StringUtils.hasText(request.getHeader("Authorization")) && request.getHeader("Authorization").startsWith("Bearer ")){
            return request.getHeader("Authorization").substring(7);
        }
        return null;
    }
    private void writeJsonResponse(HttpServletResponse response, Object dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
