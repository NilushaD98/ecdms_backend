/*
 * Copyright (c) 2024. Property of EPIC Lanka Technologies Pvt. Ltd.
 */

package com.ecdms.ecdms.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * Authentication Service Security Configuration
 *
 * @author Uditha Dayan
 * @since V2.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
@Slf4j
@Primary
public class AuthSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final LogOutHandler logOutHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Value("${security.permitted-urls}")
    private String[] permittedURL;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable())
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/**","/ws/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
                .httpBasic(
                        httpSecurityHttpBasicConfigurer ->
                        {
                            try {
                                httpSecurityHttpBasicConfigurer.disable()
                                        .exceptionHandling(
                                                httpSecurityExceptionHandlingConfigurer ->
                                                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                                                                new RestAuthenticationEntryPoint()
                                                        )
                                        );
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logout ->
                        logout.logoutUrl("/logout").addLogoutHandler(logOutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                )
                .build();
    }


    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request,
                                              WebSocketHandler wsHandler,
                                              Map<String, Object> attributes) {
                // Extract user from JWT and set as Principal
                Object userObj = attributes.get("user");
                if (userObj instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) userObj;
                    return new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), null, userDetails.getAuthorities());
                }
                return null;
            }
        };
    }
}
