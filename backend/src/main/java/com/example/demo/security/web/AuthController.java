package com.example.demo.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TestService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginUserVO login) {
    // 로그인
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

    // 로그인 정보를 security context에 저장
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // jwt토큰 발급
    String token = jwtUtil.generateToken(login.getUsername());

    // 토큰을 응답 메시지로 전송
    return ResponseEntity.ok(Collections.singletonMap("token", token));
  }
}