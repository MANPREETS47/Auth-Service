package org.example.service;

import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import org.example.entities.userinfo;
import org.example.model.UserinfoDto;
import org.example.EventProducer.UserInfoProducer;
import org.example.EventProducer.UserinfoEvent;

import lombok.AllArgsConstructor;
import lombok.Data;


@Component
@AllArgsConstructor
@Data
public class CustomUserDetailsService implements UserDetailsService {
    

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userinfo user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user);
    }

    public userinfo checkifUserAlreadyExsist(UserinfoDto userinfodto){
        return userRepository.findByUsername(userinfodto.getUsername());
    }

    public Boolean signupUser(UserinfoDto userinfoDto){
        userinfoDto.setPassword(passwordEncoder.encode(userinfoDto.getPassword()));
        if(Objects.nonNull(checkifUserAlreadyExsist(userinfoDto))){
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepository.save(new userinfo(userId, userinfoDto.getUsername(), userinfoDto.getPassword(), new HashSet<>()));
        userInfoProducer.sendEventToKafka(userinfoEvent(userinfoDto, userId));
        return true;
    }

    private UserinfoEvent userinfoEvent(UserinfoDto userinfoDto, String userId){
        return UserinfoEvent.builder()
                .id(userId)
                .firstname(userinfoDto.getUserName())
                .lastname(userinfoDto.getLastName())
                .email(userinfoDto.getEmail())
                .phoneNumber(userinfoDto.getPhoneNumber())
                .build();
    }

}
