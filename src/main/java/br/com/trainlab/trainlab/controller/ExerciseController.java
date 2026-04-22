package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.exercise.ExerciseRequestDto;
import br.com.trainlab.trainlab.dto.exercise.ExerciseResponseDto;
import br.com.trainlab.trainlab.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/workouts/{workoutId}/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService service;

    //------CREATE EXERCISE--------\\

    @PostMapping
    public ResponseEntity<ExerciseResponseDto> createExercise(
            @PathVariable Long userId,
            @PathVariable Long workoutId,
            @RequestBody @Valid ExerciseRequestDto dto) {
        ExerciseResponseDto response = service.createExercise(userId, workoutId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //------READ EXERCISE--------\\

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDto>> listExercises(
            @PathVariable Long userId,
            @PathVariable Long workoutId) {
        var response = service.listExercises(userId, workoutId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //------UPDATE EXERCISE--------\\

    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponseDto> updateExercise(
            @PathVariable Long userId,
            @PathVariable Long workoutId,
            @PathVariable Long exerciseId,
            @RequestBody @Valid ExerciseRequestDto dto) {
        ExerciseResponseDto response = service.updateExercise(userId, workoutId, exerciseId, dto);

        return ResponseEntity.ok(response);
    }


    //------DELETE EXERCISE--------\\

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable Long userId,
            @PathVariable Long workoutId,
            @PathVariable Long exerciseId) {
        service.deleteExercise(userId, workoutId, exerciseId);

        return ResponseEntity.noContent().build();
    }
}
