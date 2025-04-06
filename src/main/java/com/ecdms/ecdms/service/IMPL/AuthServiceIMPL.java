package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.request.LoginDTO;
import com.ecdms.ecdms.dto.response.AuthenticationSuccessDTO;
import com.ecdms.ecdms.enums.TokenType;
import com.ecdms.ecdms.exceptions.UnauthorizedException;
import com.ecdms.ecdms.repository.TokenHistoryRepository;
import com.ecdms.ecdms.repository.UserRepository;
import com.ecdms.ecdms.service.AuthService;
import com.ecdms.ecdms.service.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceIMPL implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public ResponseEntity login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenService.createToken(  loginDTO.getUsername());
            return new ResponseEntity<>(new AuthenticationSuccessDTO(TokenType.ACCESS_TOKEN,token), HttpStatus.CREATED);
        }catch (Exception e){
            e.getMessage();
            throw new UnauthorizedException("Please enter a valid username and password");
        }
    }
}
