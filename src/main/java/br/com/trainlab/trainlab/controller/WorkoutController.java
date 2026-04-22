package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.workout.WorkoutDetailResponseDto;
import br.com.trainlab.trainlab.dto.workout.WorkoutRequestDto;
import br.com.trainlab.trainlab.dto.workout.WorkoutResponseDto;
import br.com.trainlab.trainlab.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService service;


    @PostMapping
    public ResponseEntity<WorkoutResponseDto> createWorkout(@PathVariable Long userId, @RequestBody @Valid WorkoutRequestDto dto) {
        WorkoutResponseDto response = service.createWorkout(userId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutResponseDto>> listWorkouts(@PathVariable Long userId) {
        var response = service.listWorkouts(userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutDetailResponseDto> getWorkoutDetail(@PathVariable Long userId, @PathVariable Long workoutId) {
        var response = service.getWorkoutDetail(userId, workoutId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> updateWorkout(@PathVariable Long userId, @PathVariable Long workoutId, @RequestBody @Valid WorkoutRequestDto dto) {
        WorkoutResponseDto response = service.updateWorkout(userId, workoutId, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long userId, @PathVariable Long workoutId) {
        service.deleteWorkout(userId, workoutId);

        return ResponseEntity.noContent().build();
    }
}
