package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.auth.LoginRequestDto;
import br.com.trainlab.trainlab.exception.BusinessException;
import br.com.trainlab.trainlab.exception.ErrorMessage;
import br.com.trainlab.trainlab.exception.ResourceNotFoundException;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.repository.UserRepository;
import br.com.trainlab.trainlab.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String login(LoginRequestDto dto) {
        User user = repository.findByEmail(dto.email()).orElseThrow(() -> new BusinessException(ErrorMessage.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BusinessException(ErrorMessage.INVALID_CREDENTIALS);
        }

        var token = jwtService.generateToken(user.getEmail());
        System.out.println("---------------------------------------------------");
        System.out.println("Usuário Logado -→ " + user.getEmail());
        System.out.println("---------------------------------------------------");

        return token;
    }
}


