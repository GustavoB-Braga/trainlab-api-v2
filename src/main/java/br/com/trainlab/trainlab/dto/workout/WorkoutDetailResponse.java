package br.com.trainlab.trainlab.dto.workout;

import br.com.trainlab.trainlab.dto.exercise.ExerciseResponseDto;
import br.com.trainlab.trainlab.model.enums.Level;
import br.com.trainlab.trainlab.model.enums.WorkoutType;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutDetailResponse(
        Long id,
        String name,
        String description,
        WorkoutType type,
        Level level,
        LocalDateTime createdAt,
        LocalDateTime updateAt,
        List<ExerciseResponseDto> exercises
) {
}
