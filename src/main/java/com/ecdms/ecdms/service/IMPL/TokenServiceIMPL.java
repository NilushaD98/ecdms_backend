package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.entity.TokenConfiguration;
import com.ecdms.ecdms.entity.TokenHistory;
import com.ecdms.ecdms.entity.User;
import com.ecdms.ecdms.enums.Status;
import com.ecdms.ecdms.enums.TokenType;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.TokenConfigurationRepository;
import com.ecdms.ecdms.repository.TokenHistoryRepository;
import com.ecdms.ecdms.service.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TokenServiceIMPL implements TokenService {

    private final TokenConfigurationRepository tokenConfigurationRepository;
    private final TokenHistoryRepository tokenHistoryRepository;
    private final HttpServletRequest httpServletRequest;
    private Date expiryDate = new Date();

    @Override
    public String createToken(String username){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("username",user.getUsername());
            claims.put("userID", user.getUserUuid());
            claims.put("privileges",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            expiryDate =new Date(new Date().getTime() + getSecretKey().getExpirationTimeInSeconds());
            String token = Jwts.builder()
                    .setIssuer("ECDMS")
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, getSecretKey().getSecretKey())
                    .compact();
            String uuid = tokenSave(token, user);
            if(uuid != null){
                return uuid;
            }else {
                throw new InternalServerErrorException("Token saving unsuccessful");
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Require service unavailable.");
        }

    }

    private String tokenSave(String token, User user){
        try {
            String clientIp = httpServletRequest.getHeader("X-Forwarded-For");
            String userAgent = httpServletRequest.getHeader("User-Agent");
            String deviceType = httpServletRequest.getHeader("X-Device-Type");
            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = httpServletRequest.getRemoteAddr();
            }
            ZoneId colomboZone = ZoneId.of("Asia/Colombo");
            LocalDateTime expireAt = expiryDate.toInstant()
                    .atZone(colomboZone)
                    .toLocalDateTime();
            TokenHistory tokenHistory = new TokenHistory(
                    token,
                    false,
                    false,
                    deviceType != null ? deviceType:"WEB",
                    UUID.randomUUID().toString(),
                    expireAt,
                    true,
                    TokenType.ACCESS_TOKEN.toString(),
                    user
            );
            tokenHistoryRepository.save(tokenHistory);
            return tokenHistory.getTokenUuid();
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Lookup userId from the token
     * @param token Auth Token
     * @return Username
     */
    @Override
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSecretKey().getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Validate token signature and content
     * @param authToken Auth Token
     * @return TRUE if valid, FALSE otherwise
     */
    @Override
    public boolean validateToken(String authToken) {
        try {

            Jwts.parser().setSigningKey(getSecretKey().getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
    private TokenConfiguration getSecretKey(){
        Optional<TokenConfiguration> byStatusAndType = tokenConfigurationRepository.findByStatusAndType(TokenType.ACCESS_TOKEN.toString(), Status.ACTIVE.toString());
        if (!byStatusAndType.isPresent()){
            throw new InternalServerErrorException("Require service unavailable.");
        }else {
            return byStatusAndType.get();
        }
    }

}
