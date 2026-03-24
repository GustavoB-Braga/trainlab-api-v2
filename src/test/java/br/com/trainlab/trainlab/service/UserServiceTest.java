package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.user.UserRequestDto;
import br.com.trainlab.trainlab.exception.BusinessException;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserRequestDto dto;


    @Test
    void shoudlCreateUserSuccessfully() {
        //ARRANGE
        this.dto = new UserRequestDto("Gustavo", "teste@gmail.com", "123456", "123456");
        given(repository.existsByEmail(dto.email())).willReturn(false);
        given(passwordEncoder.encode(dto.password())).willReturn("senha_protegida");

        var savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(dto.name());
        savedUser.setEmail(dto.email());
        savedUser.setPassword("senha_protegida");

        given(repository.save(any(User.class))).willReturn(savedUser);

        //ACT
        var response = service.createUser(dto);

        //ASSERT
        assertNotNull(response);
        assertEquals("Gustavo", response.nome());
        assertEquals("teste@gmail.com", response.email());
        then(repository).should(times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenPasswordsDoNotMatch() {
        //ARRANGE
        this.dto = new UserRequestDto("Gustavo", "teste@gmail.com", "123456", "12345");

        //ASSERT + ACT
        assertThrows(BusinessException.class, () -> service.createUser(dto));
        then(repository).should(never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        //ARRANGE
        this.dto = new UserRequestDto("Gustavo", "teste@gmail.com", "123456", "123456");
        given(repository.existsByEmail(dto.email())).willReturn(true);

        //ASSERT + ACT
        assertThrows(BusinessException.class, () -> service.createUser(dto));
        then(repository).should(never()).save(any());
    }
}
