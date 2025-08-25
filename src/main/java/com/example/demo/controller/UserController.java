package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest){
        User user=userService.registerUser(registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getEmail());
        return ResponseEntity.ok(user);
    }
}