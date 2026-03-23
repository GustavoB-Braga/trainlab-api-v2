package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.Workout.WorkoutDetailResponse;
import br.com.trainlab.trainlab.dto.Workout.WorkoutRequestDto;
import br.com.trainlab.trainlab.dto.Workout.WorkoutResponseDto;
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

    //------CREATE WORKOUT--------\\

    @PostMapping
    public ResponseEntity<WorkoutResponseDto> createWorkout(@PathVariable Long userId, @RequestBody @Valid WorkoutRequestDto dto) {
        WorkoutResponseDto response = service.createWorkout(userId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //------READ WORKOUT--------\\

    @GetMapping
    public ResponseEntity<List<WorkoutResponseDto>> readWorkout(@PathVariable Long userId) {
        var response = service.listWorkouts(userId);

        return ResponseEntity.ok(response);
    }

    //------READ WORKOUT WITH DETAILS--------\\

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutDetailResponse> getDetail(@PathVariable Long userId, @PathVariable Long workoutId) {
        var response = service.getWorkoutDetail(userId, workoutId);

        return ResponseEntity.ok(response);
    }
    //------UPDATE WORKOUT--------\\

    @PostMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> updateWorkout(@PathVariable Long userId, @PathVariable Long workoutId, @RequestBody @Valid WorkoutRequestDto dto) {
        WorkoutResponseDto response = service.updateWorkout(userId, workoutId, dto);

        return ResponseEntity.ok(response);
    }

    //------DELETE WORKOUT--------\\

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> deleteWorkout(@PathVariable Long userId, @PathVariable Long workoutId) {
        service.deleteWorkout(userId, workoutId);

        return ResponseEntity.noContent().build();
    }
}
