package br.com.trainlab.trainlab.dto.Workout;

import br.com.trainlab.trainlab.model.enums.Level;
import br.com.trainlab.trainlab.model.enums.WorkoutType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkoutRequestDto(@NotBlank String name, @NotBlank String description, @NotNull WorkoutType type,
                                @NotNull Level level) {
}
