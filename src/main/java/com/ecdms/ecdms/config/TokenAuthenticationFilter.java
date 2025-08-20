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
            String path = request.getServletPath();
            if ("/auth/login".equals(path) || path.startsWith("/ws/")) {
                filterChain.doFilter(request, response);
                return;
            }

            String tokenUuid = getJwtFromRequest(request);
            Optional<TokenHistory> tokenHistoryOpt = tokenHistoryRepository.findTokenHistoryByTokenUUIDEquals(tokenUuid);

            if (!tokenHistoryOpt.isPresent()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                StandardResponse failureResponse = new StandardResponse(false, "Unauthorized");
                writeJsonResponse(response, failureResponse);
                return; // Stop processing
            }

            String jwt = tokenHistoryOpt.get().getToken();
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                UserDetails userDetails = tokenHistoryOpt.get().getUser();
                AuthorizedUserContext.setUser(userDetails.getUsername());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                writeJsonResponse(response, new StandardResponse(false, "Internal Server Error"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
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
            return bearerToken.substring(7);
        }
        return null;
    }
    private void writeJsonResponse(HttpServletResponse response, Object dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
        response.getWriter().flush();
    }
}
