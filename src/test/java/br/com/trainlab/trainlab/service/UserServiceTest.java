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

    private UserResponseDto userResponseDto;

    private UserRequestDto dto;
    private UserUpdateRequestDto updateRequestDto;

    private User user;

    //CREATE TESTS

    @Test
    void shouldCreateUserSuccessfully() {
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

    //UPDATE TESTS

    @Test
    void shoudlUpdateUserSuccessfully() {
        //ARRANGE
        this.user = new User();
        user.setId(1L);
        user.setName("nome errado");
        user.setEmail("email errado");

        this.updateRequestDto = new UserUpdateRequestDto("Gustavo Braga", "teste@gmail.com");

        given(repository.existsByEmailAndIdNot(updateRequestDto.email(), 1L)).willReturn(false);
        given(repository.findById(1L)).willReturn(Optional.of(user));


        given(repository.save(any())).willReturn(user);

        //ACT
        UserResponseDto response = service.updateUser(1L, updateRequestDto);

        //ASSERT
        assertEquals(response.id(), user.getId());
        assertEquals(response.nome(), user.getName());
        assertEquals(response.email(), user.getEmail());

        then(repository).should(times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExistsOnUpdate() {
        //ARRANGE
        this.updateRequestDto = new UserUpdateRequestDto("Gustavo Braga", "teste@gmail.com");
        given(repository.existsByEmailAndIdNot(updateRequestDto.email(), 1L)).willReturn(true);

        //ASSERT + ACT
        assertThrows(BusinessException.class, () -> service.updateUser(1L, updateRequestDto));
        then(repository).should(never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnUpdate() {
        //ARRANGE
        this.updateRequestDto = new UserUpdateRequestDto("Gustavo Braga", "teste@gmail.com");
        given(repository.existsByEmailAndIdNot(updateRequestDto.email(), 1L)).willReturn(false);

        //ASSERT + ACT
        assertThrows(ResourceNotFoundException.class, () -> service.updateUser(1L, updateRequestDto));
        then(repository).should(never()).save(any());

    }

    @Test
    void shouldDeleteUserSuccessfully() {

        //ARRANGE
        User user = new User();
        user.setId(1L);

        given(repository.findById(1L)).willReturn(Optional.of(user));

        //ACT
        service.deleteUser(1L);

        //ASSERT
        then(repository).should(times(1)).delete(any());

    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnDelete() {
        //ARRANGE
        given(repository.findById(1L)).willReturn(Optional.empty());

        //ASSERT + ACT
        assertThrows(ResourceNotFoundException.class, () -> service.deleteUser(1L));
//        then(repository).should(never()).delete(any());
        verify(repository,never()).delete(any());
    }
}
