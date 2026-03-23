package br.com.trainlab.trainlab.dto.exercise;

import br.com.trainlab.trainlab.model.enums.MuscleGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExerciseRequestDto(
        @NotBlank
        String name,
        @NotNull
        MuscleGroup muscleGroup,
        @NotNull @Min(1)
        Integer sets,
        @NotNull @Min(1)
        Integer repetitions
) {
}
