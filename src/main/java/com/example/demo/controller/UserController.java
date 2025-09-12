package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest){
        User user=userService.registerUser(registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword());
        return ResponseEntity.ok(user);
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
    }


    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.noContent().build();

    }
}