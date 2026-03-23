package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.Workout.WorkoutDetailResponse;
import br.com.trainlab.trainlab.dto.Workout.WorkoutRequestDto;
import br.com.trainlab.trainlab.dto.Workout.WorkoutResponseDto;
import br.com.trainlab.trainlab.dto.exercise.ExerciseResponseDto;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.model.Workout;
import br.com.trainlab.trainlab.repository.UserRepository;
import br.com.trainlab.trainlab.repository.WorkoutRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository repository;

    @Autowired
    private UserRepository userRepository;

    public WorkoutResponseDto createWorkout(Long userId, @Valid WorkoutRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Workout workout = new Workout();
        workout.setName(dto.name());
        workout.setDescription(dto.description());
        workout.setType(dto.type());
        workout.setLevel(dto.level());
        workout.setUser(user);

        Workout savedWorkout = repository.save(workout);

        return new WorkoutResponseDto(
                savedWorkout.getId(),
                savedWorkout.getName(),
                savedWorkout.getType(),
                savedWorkout.getLevel(),
                savedWorkout.getCreatedAt(),
                savedWorkout.getUpdatedAt()
        );
    }

    public List<WorkoutResponseDto> listWorkouts(Long userId) {

        List<Workout> workouts = repository.findAllByUserId(userId);

        return workouts.stream()
                .map(workout -> new WorkoutResponseDto(
                        workout.getId(),
                        workout.getName(),
                        workout.getType(),
                        workout.getLevel(),
                        workout.getCreatedAt(),
                        workout.getUpdatedAt()

                )).toList();
    }

    public WorkoutDetailResponse getWorkoutDetail(Long userId, Long workoutId) {
        Workout workout = repository.findByIdAndUserId(workoutId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado"));

        List<ExerciseResponseDto> exercises = workout.getExercises()
                .stream()
                .map(ex -> new ExerciseResponseDto(
                        ex.getId(),
                        ex.getName(),
                        ex.getMuscleGroup(),
                        ex.getSets(),
                        ex.getRepetitions()
                )).toList();

        return new WorkoutDetailResponse(
                workout.getId(),
                workout.getName(),
                workout.getDescription(),
                workout.getType(),
                workout.getLevel(),
                workout.getCreatedAt(),
                workout.getUpdatedAt(),
                exercises
        );
    }

    public WorkoutResponseDto updateWorkout(Long userId, Long workoutId, @Valid WorkoutRequestDto dto) {

        Workout workout = repository.findByIdAndUser(workoutId, userId)
                .orElseThrow(() -> new RuntimeException("Workout não encontrado"));

        workout.setName(dto.name());
        workout.setDescription(dto.description());
        workout.setType(dto.type());
        workout.setLevel(dto.level());

        Workout updatedWorkout = repository.save(workout);

        return new WorkoutResponseDto(
                updatedWorkout.getId(),
                updatedWorkout.getName(),
                updatedWorkout.getType(),
                updatedWorkout.getLevel(),
                updatedWorkout.getCreatedAt(),
                updatedWorkout.getUpdatedAt()
        );
    }

    public void deleteWorkout(Long userId, Long workoutId) {

        repository.deleteByIdAndUserId(workoutId, userId);
    }

}


