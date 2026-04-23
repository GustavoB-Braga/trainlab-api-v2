package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.user.UserRequestDto;
import br.com.trainlab.trainlab.dto.user.UserResponseDto;
import br.com.trainlab.trainlab.dto.user.UserUpdateRequestDto;
import br.com.trainlab.trainlab.exception.BusinessException;
import br.com.trainlab.trainlab.exception.ResourceNotFoundException;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //------CREATE USER--------\\

    public UserResponseDto createUser(UserRequestDto dto) {

        if (!dto.password().equals(dto.confirmPassword())) {
            throw new BusinessException("As senhas não coincidem");
        }

        if (repository.existsByEmail(dto.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        User savedUser = repository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    //------UPDATE USER--------\\

    public UserResponseDto updateUser(String email, UserUpdateRequestDto dto) {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (repository.existsByEmailAndIdNot(dto.email(), user.getId())) {
            throw new BusinessException("Email já cadastrado");
        }

        user.setName(dto.name());
        user.setEmail(dto.email());

        repository.save(user);

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    //------DELETE USER--------\\

    public void deleteUser(String email) {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        repository.delete(user);
    }

    public UserResponseDto getMe(String email) {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
