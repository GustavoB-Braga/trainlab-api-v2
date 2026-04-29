package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.auth.LoginRequestDto;
import br.com.trainlab.trainlab.dto.auth.LoginResponseDto;
import br.com.trainlab.trainlab.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        String token = service.login(dto);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
