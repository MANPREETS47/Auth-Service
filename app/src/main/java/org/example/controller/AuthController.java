package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.example.entities.refreshtoken;
import org.example.model.UserinfoDto;
import org.example.response.JwtResponse;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.example.service.CustomUserDetailsService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity<?> signUp(@RequestBody UserinfoDto userInfoDto) {
        try {
            Boolean isSignUped = userDetailsService.signupUser(userInfoDto);

            if (Boolean.FALSE.equals(isSignUped)) {
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }

            refreshtoken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());

            return new ResponseEntity<>(JwtResponse.builder().accessToken(jwtToken).token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}