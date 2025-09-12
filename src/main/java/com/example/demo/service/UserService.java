package com.example.demo.service;


import com.example.demo.domain.User;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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

    public UserResponseDto getUser(String username){
        User user=userRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }


    public void deleteUser(String username){
        User user=userRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        userRepository.delete(user);
    }
    @Transactional
    public User authenticateUser(String username,String password){
        User user=userRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }
}
