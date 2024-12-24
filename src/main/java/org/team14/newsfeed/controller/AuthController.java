package org.team14.newsfeed.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team14.newsfeed.service.UserService;
import org.team14.newsfeed.token.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SiunupRequest dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("회원가입에 성공하셨습니다.")
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto) {
        userService.authenticateUser(dto);
        return ResponseEntity.ok(token);
    }

}
