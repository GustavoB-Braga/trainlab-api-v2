package br.com.trainlab.trainlab.dto.Workout;

import br.com.trainlab.trainlab.model.enums.Level;
import br.com.trainlab.trainlab.model.enums.WorkoutType;

import java.time.LocalDateTime;

public record WorkoutResponseDto(
        Long id,
        String name,
        WorkoutType type,
        Level level,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
