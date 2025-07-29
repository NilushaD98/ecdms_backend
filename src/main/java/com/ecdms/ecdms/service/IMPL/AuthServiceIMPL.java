package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.request.LoginDTO;
import com.ecdms.ecdms.dto.response.AuthenticationSuccessDTO;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.entity.Teacher;
import com.ecdms.ecdms.enums.TokenType;
import com.ecdms.ecdms.exceptions.UnauthorizedException;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.repository.TeacherRepository;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceIMPL implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

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
            String token = tokenService.createToken(loginDTO.getUsername());
            String userType = "";
            int userID = 0;
            Optional<Student> byEmailEquals = studentRepository.findByEmailEquals(loginDTO.getUsername());
            Optional<Teacher> optionalTeacher = teacherRepository.findByEmail(loginDTO.getUsername());

            if(byEmailEquals.isPresent()){
                userType = "STUDENT";
                userID = byEmailEquals.get().getStuID();
            }else if (optionalTeacher.isPresent()){
                userType = "TEACHER";
                userID = optionalTeacher.get().getTeacherID();
            }else {
                userType = "ADMIN";
            }
            return new ResponseEntity<>(new AuthenticationSuccessDTO(
                    TokenType.ACCESS_TOKEN,token,
                    userType,
                    userID
                    ), HttpStatus.CREATED);
        }catch (Exception e){
            e.getMessage();
            throw new UnauthorizedException("Please enter a valid username and password");
        }
    }
}
