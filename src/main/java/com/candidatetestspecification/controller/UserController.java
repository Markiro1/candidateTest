package com.candidatetestspecification.controller;

import com.candidatetestspecification.dtos.jwt.JwtRequest;
import com.candidatetestspecification.dtos.user.UserResponseDto;
import com.candidatetestspecification.dtos.user.UserRequestDto;
import com.candidatetestspecification.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthController authController;

    @PostMapping("/add")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest) {
        return authController.createAuthToken(jwtRequest);
    }
}
