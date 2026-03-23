package br.com.trainlab.trainlab.dto.exercise;

import br.com.trainlab.trainlab.model.enums.MuscleGroup;

public record ExerciseResponseDto(
        Long id,
        String name,
        MuscleGroup muscleGroup,
        Integer sets,
        Integer repetitions
) {
}
