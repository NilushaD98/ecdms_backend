/*
 * Copyright (c) 2024. Property of EPIC Lanka Technologies Pvt. Ltd.
 */
package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.entity.User;
import com.ecdms.ecdms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nilusha Dissanayake
 * @version 1.0.0
 * @since 2.0.0
 */
@Service
@Transactional
@Slf4j
public class CustomUserDetailsServiceIMPL implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameEquals(username).orElseThrow(
                () ->(
                        new UsernameNotFoundException("User not found with email : " + username)

                )
        );
        return user;
    }
}
