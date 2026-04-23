package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.workout.WorkoutDetailResponseDto;
import br.com.trainlab.trainlab.dto.workout.WorkoutRequestDto;
import br.com.trainlab.trainlab.dto.workout.WorkoutResponseDto;
import br.com.trainlab.trainlab.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService service;

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping
    public ResponseEntity<WorkoutResponseDto> createWorkout(@RequestBody @Valid WorkoutRequestDto dto) {

        String email = getAuthenticatedUserEmail();

        WorkoutResponseDto response = service.createWorkout(email, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutResponseDto>> listWorkouts() {

        String email = getAuthenticatedUserEmail();

        var response = service.listWorkouts(email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutDetailResponseDto> getWorkoutDetail(@PathVariable Long workoutId) {

        String email = getAuthenticatedUserEmail();

        var response = service.getWorkoutDetail(email, workoutId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> updateWorkout(@PathVariable Long workoutId, @RequestBody @Valid WorkoutRequestDto dto) {

        String email = getAuthenticatedUserEmail();

        WorkoutResponseDto response = service.updateWorkout(email, workoutId, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long workoutId) {

        String email = getAuthenticatedUserEmail();

        service.deleteWorkout(email, workoutId);

        return ResponseEntity.noContent().build();
    }
}
