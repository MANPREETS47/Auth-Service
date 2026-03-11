package org.example.service;

import java.util.Optional;
import java.util.UUID;

import org.example.entities.refreshtoken;
import org.example.entities.userinfo;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;


    
@Service
public class RefreshTokenService {
    
    @Autowired RefreshTokenRepository refreshTokenRepository;

    @Autowired UserRepository userRepository;

    public refreshtoken createRefreshToken(String username){
        userinfo user  = userRepository.findByUsername(username);
        refreshtoken refreshToken = refreshtoken.builder()
            .user(user)
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(600000))
            .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public refreshtoken verifyExpiration(refreshtoken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public Optional<refreshtoken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
