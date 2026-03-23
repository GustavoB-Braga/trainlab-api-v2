package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.user.UserRequestDto;
import br.com.trainlab.trainlab.dto.user.UserResponseDto;
import br.com.trainlab.trainlab.dto.user.UserUpdateRequestDto;
import br.com.trainlab.trainlab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    //------CREATE USER--------\\

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto) {
        UserResponseDto response = service.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //------UPDATE USER--------\\

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto dto) {
        UserResponseDto response = service.updateUser(id, dto);

        return ResponseEntity.ok(response);
    }

    //------DELETE USER--------\\

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}
