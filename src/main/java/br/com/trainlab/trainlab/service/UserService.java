package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.User.UserRequestDto;
import br.com.trainlab.trainlab.dto.User.UserResponseDto;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    //------CREATE USER--------\\

    public UserResponseDto createUser(UserRequestDto dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        User savedUser = repository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    //------UPDATE USER--------\\

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        repository.save(user);
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    //------DELETE USER--------\\

    public void deleteUser(Long id) {

        User user = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.delete(user);
    }
}
