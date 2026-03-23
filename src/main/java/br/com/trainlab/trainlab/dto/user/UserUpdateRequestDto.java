package br.com.trainlab.trainlab.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequestDto(@NotBlank String name, @NotBlank @Email String email) {
}
