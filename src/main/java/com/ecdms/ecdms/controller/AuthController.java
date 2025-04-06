package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.LoginDTO;
import com.ecdms.ecdms.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        return authService.login(loginDTO);
    }
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('LOGIN')")
    public String test(){
        return "teset";
    }
}
