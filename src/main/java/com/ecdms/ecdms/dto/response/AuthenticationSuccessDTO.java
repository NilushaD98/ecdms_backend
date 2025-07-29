package com.ecdms.ecdms.dto.response;

import com.ecdms.ecdms.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationSuccessDTO {

    private TokenType tokenType;
    private String token;
    private String userType;
    private int userID;
}
