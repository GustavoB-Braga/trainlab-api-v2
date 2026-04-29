package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.exercise.ExerciseRequestDto;
import br.com.trainlab.trainlab.dto.exercise.ExerciseResponseDto;
import br.com.trainlab.trainlab.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts/{workoutId}/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService service;

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping
    public ResponseEntity<ExerciseResponseDto> createExercise(
            @PathVariable Long workoutId,
            @RequestBody @Valid ExerciseRequestDto dto) {

        String email = getAuthenticatedUserEmail();

        ExerciseResponseDto response = service.createExercise(email, workoutId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDto>> listExercises(
            @PathVariable Long workoutId) {

        String email = getAuthenticatedUserEmail();

        var response = service.listExercises(email, workoutId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponseDto> getExercise(@PathVariable Long workoutId, @PathVariable Long exerciseId) {

        String email = getAuthenticatedUserEmail();

        var response = service.getExercise(email,workoutId,exerciseId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponseDto> updateExercise(
            @PathVariable Long workoutId,
            @PathVariable Long exerciseId,
            @RequestBody @Valid ExerciseRequestDto dto) {

        String email = getAuthenticatedUserEmail();

        ExerciseResponseDto response = service.updateExercise(email, workoutId, exerciseId, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable Long workoutId,
            @PathVariable Long exerciseId) {

        String email = getAuthenticatedUserEmail();

        service.deleteExercise(email, workoutId, exerciseId);

        return ResponseEntity.noContent().build();
    }
}
