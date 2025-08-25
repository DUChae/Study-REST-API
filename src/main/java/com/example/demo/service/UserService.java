package com.example.demo.service;


import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User registerUser(String username, String email, String password){
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 등록된 사용자 이름입니다.");
        }
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        User user= User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return userRepository.save(user);
    }
}
