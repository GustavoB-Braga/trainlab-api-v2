package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.user.UserRequestDto;
import br.com.trainlab.trainlab.dto.user.UserUpdateRequestDto;
import br.com.trainlab.trainlab.exception.BusinessException;
import br.com.trainlab.trainlab.exception.ResourceNotFoundException;
import br.com.trainlab.trainlab.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService service;

    private ArgumentCaptor<UserRequestDto> captor = ArgumentCaptor.forClass(UserRequestDto.class);
    private ArgumentCaptor<UserUpdateRequestDto> captorUpdate = ArgumentCaptor.forClass(UserUpdateRequestDto.class);

    //-------- CREATE USER TESTES --------------\\

    @Test
    void shouldCreateUserSuccessfully_whenValidRequest() throws Exception {
        //ARRANGE
        String json = """
                { 
                 "name": "Gustavo Braga",
                 "email": "teste@gmail.com",
                 "password": "123456",
                 "confirmPassword": "123456"
                 }
                """;


        //ACT
        mvc.perform(
                post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        //ASSERT
        verify(service).createUser(captor.capture());
        UserRequestDto request = captor.getValue();

        Assertions.assertEquals("Gustavo Braga", request.name());
        Assertions.assertEquals("teste@gmail.com", request.email());
        Assertions.assertEquals("123456", request.password());
        Assertions.assertEquals("123456", request.confirmPassword());

    }

    @Test
    void shouldReturnBadRequest_whenPasswordsDoNotMatch() throws Exception {
        //ARRANGE
        String json = """
                { 
                 "name": "Gustavo Braga",
                 "email": "teste@gmail.com",
                 "password": "123456",
                 "confirmPassword": "142536"
                 }
                """;

        doThrow(new BusinessException("Senhas incompatíveis")).when(service).createUser(any());

        //ACT
        mvc.perform(
                post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        //ASSERT
        verify(service).createUser(any());
    }

    @Test
    void shouldReturnBadRequest_whenEmailIsInvalid() throws Exception {
        //ARRANGE
        String json = """
                { 
                 "name": "Gustavo Braga",
                 "email": "emailErrado.com",
                 "password": "123456",
                 "confirmPassword": "123456"
                 }
                """;

        //ACT
        mvc.perform(
                post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        //ASSERT
        verify(service, never()).createUser(any());
    }

    @Test
    void shouldReturnBadRequest_whenPayloadIsEmpty() throws Exception {
        //ARRANGE
        String json = """
                {}
                """;

        //ACT
        mvc.perform(
                post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        //ASSERT
        verify(service, never()).createUser(any());
    }

    //-------- UPDATE USER TESTES --------------\\

    @Test
    void shouldUpdateUserSuccessfully_whenValidRequest() throws Exception {
        //ARRANGE
        String json = """
                { 
                 "name": "Gustavo Braga",
                 "email": "teste@gmail.com"
                 }
                """;


        //ACT
        mvc.perform(
                put("/users/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        //ASSERT
        verify(service).updateUser(eq(1L), captorUpdate.capture());
        UserUpdateRequestDto request = captorUpdate.getValue();

        Assertions.assertEquals("Gustavo Braga", request.name());
        Assertions.assertEquals("teste@gmail.com", request.email());

    }

    @Test
    void shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        //ARRANGE
        String json = """
                {
                "name": "Gustavo Braga",
                "email": "teste@gmail.com"
                }
                """;

        doThrow(new ResourceNotFoundException("Usuário não encontrado")).when(service).updateUser(eq(1L), any());
        //ACT
        mvc.perform(
                put("/users/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        //ASSERT
        verify(service).updateUser(eq(1L), any());

    }

    @Test
    void shouldReturnBadRequest_whenPayloadIsEmptyOnUpdate() throws Exception {
        //ARRANGE
        String json = """
                {}
                """;

        //ACT
        mvc.perform(
                put("/users/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        //ASSERT
        verify(service, never()).updateUser(any(), any());
    }

    @Test
    void shouldReturnBadRequest_whenBusinessRuleFails() throws Exception {
        //ARRANGE
        String json = """
                {
                "name": "Gustavo Braga",
                "email": "teste@gmail.com"
                }
                """;


        doThrow(new BusinessException("Email já cadastrado")).when(service).updateUser(eq(1L), any());
        //ACT
        mvc.perform(
                put("/users/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        //ASSERT
        verify(service).updateUser(eq(1L), any());
    }

    //-------- DELETE USER TESTES --------------\\

    @Test
    void shouldDeleteUserSuccessfully_whenUserExists() throws Exception {

        //ARRANGE + ACT
        mvc.perform(
                delete("/users/1")
        ).andExpect(status().isNoContent());

        //ASSERT
        verify(service).deleteUser(eq(1L));
    }

    @Test
    void shouldReturnNotFound_whenUserDoesNotExistOnDelete() throws Exception {

        //ARRANGE
        doThrow(new ResourceNotFoundException("Usuário não encontrado")).when(service).deleteUser(eq(1L));
        // ACT
        mvc.perform(
                delete("/users/1")
        ).andExpect(status().isNotFound());

        //ASSERT
        verify(service).deleteUser(eq(1L));
    }
}



