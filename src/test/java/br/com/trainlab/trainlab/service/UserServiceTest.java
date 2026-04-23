package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.user.UserRequestDto;
import br.com.trainlab.trainlab.dto.user.UserResponseDto;
import br.com.trainlab.trainlab.dto.user.UserUpdateRequestDto;
import br.com.trainlab.trainlab.exception.BusinessException;
import br.com.trainlab.trainlab.exception.ResourceNotFoundException;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    // ================= CREATE =================

    @Test
    void shouldCreateUserSuccessfully() {
        var dto = new UserRequestDto("Gustavo", "teste@gmail.com", "123456", "123456");

        given(repository.existsByEmail(dto.email())).willReturn(false);
        given(passwordEncoder.encode(dto.password())).willReturn("senha_protegida");

        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(dto.name());
        savedUser.setEmail(dto.email());
        savedUser.setPassword("senha_protegida");

        given(repository.save(any(User.class))).willReturn(savedUser);

        var response = service.createUser(dto);

        assertNotNull(response);
        assertEquals("Gustavo", response.nome());
        assertEquals("teste@gmail.com", response.email());

        then(repository).should(times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenPasswordsDoNotMatch() {
        var dto = new UserRequestDto("Gustavo", "teste@gmail.com", "123456", "12345");

        assertThrows(BusinessException.class, () -> service.createUser(dto));
        then(repository).should(never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        var dto = new UserRequestDto("Gustavo", "teste@gmail.com", "123456", "123456");

        given(repository.existsByEmail(dto.email())).willReturn(true);

        assertThrows(BusinessException.class, () -> service.createUser(dto));
        then(repository).should(never()).save(any());
    }

    // ================= UPDATE =================

    @Test
    void shouldUpdateUserSuccessfully() {
        var user = new User();
        user.setId(1L);
        user.setName("nome errado");
        user.setEmail("email errado");

        var dto = new UserUpdateRequestDto("Gustavo Braga", "teste@gmail.com");

        given(repository.findByEmail("teste@gmail.com")).willReturn(Optional.of(user));
        given(repository.existsByEmailAndIdNot(dto.email(), user.getId())).willReturn(false);
        given(repository.save(any())).willReturn(user);

        var response = service.updateUser("teste@gmail.com", dto);

        assertEquals(user.getId(), response.id());
        assertEquals(user.getName(), response.nome());
        assertEquals(user.getEmail(), response.email());

        then(repository).should(times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExistsOnUpdate() {
        var user = new User();
        user.setId(1L);

        var dto = new UserUpdateRequestDto("Gustavo Braga", "teste@gmail.com");

        given(repository.findByEmail("teste@gmail.com")).willReturn(Optional.of(user));
        given(repository.existsByEmailAndIdNot(dto.email(), user.getId())).willReturn(true);

        assertThrows(BusinessException.class, () -> service.updateUser("teste@gmail.com", dto));
        then(repository).should(never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnUpdate() {
        var dto = new UserUpdateRequestDto("Gustavo Braga", "teste@gmail.com");

        given(repository.findByEmail("teste@gmail.com")).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateUser("teste@gmail.com", dto));

        then(repository).should(never()).save(any());
    }

    // ================= DELETE =================

    @Test
    void shouldDeleteUserSuccessfully() {
        var user = new User();
        user.setId(1L);

        given(repository.findByEmail("teste@gmail.com")).willReturn(Optional.of(user));

        service.deleteUser("teste@gmail.com");

        then(repository).should(times(1)).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnDelete() {
        given(repository.findByEmail("teste@gmail.com")).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteUser("teste@gmail.com"));

        verify(repository, never()).delete(any());
    }
}