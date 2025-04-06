package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity login(LoginDTO loginDTO);
}
