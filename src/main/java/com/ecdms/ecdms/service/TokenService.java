package com.ecdms.ecdms.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TokenService {

    String createToken(String username) throws JsonProcessingException;
    boolean validateToken(String authToken);
    String getUserIdFromToken(String token);
}
