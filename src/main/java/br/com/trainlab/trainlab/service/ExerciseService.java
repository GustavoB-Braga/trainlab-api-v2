package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.exercise.ExerciseRequestDto;
import br.com.trainlab.trainlab.dto.exercise.ExerciseResponseDto;
import br.com.trainlab.trainlab.exception.ResourceNotFoundException;
import br.com.trainlab.trainlab.model.Exercise;
import br.com.trainlab.trainlab.model.Workout;
import br.com.trainlab.trainlab.repository.ExerciseRepository;
import br.com.trainlab.trainlab.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository repository;

    @Autowired
    private WorkoutRepository workoutRepository;

    public ExerciseResponseDto createExercise(String email, Long workoutId, ExerciseRequestDto dto) {

        Workout workout = workoutRepository.findByIdAndUserEmail(workoutId, email)
                .orElseThrow(() -> new ResourceNotFoundException("User ou Workout não encontrado"));

        Exercise exercise = new Exercise();
        exercise.setName(dto.name());
        exercise.setMuscleGroup(dto.muscleGroup());
        exercise.setSets(dto.sets());
        exercise.setRepetitions(dto.repetitions());
        exercise.setWorkout(workout);

        var savedExercise = repository.save(exercise);
        return new ExerciseResponseDto(
                savedExercise.getId(),
                savedExercise.getName(),
                savedExercise.getMuscleGroup(),
                savedExercise.getSets(),
                savedExercise.getRepetitions()
        );
    }

    public List<ExerciseResponseDto> listExercises(String email, Long workoutId) {

        Workout workout = workoutRepository.findByIdAndUserEmail(workoutId, email)
                .orElseThrow(() -> new ResourceNotFoundException("Workout não encontrado"));

        List<Exercise> exercises = repository.findAllByWorkoutId(workoutId);

        return exercises.stream()
                .map(ex -> new ExerciseResponseDto(
                        ex.getId(),
                        ex.getName(),
                        ex.getMuscleGroup(),
                        ex.getSets(),
                        ex.getRepetitions())
                ).toList();
    }

    public ExerciseResponseDto updateExercise(String email, Long workoutId, Long exerciseId, ExerciseRequestDto dto) {

        Workout workout = workoutRepository.findByIdAndUserEmail(workoutId, email)
                .orElseThrow(() -> new ResourceNotFoundException("User ou Workout não encontrado"));

        Exercise exercise = repository.findByIdAndWorkoutId(exerciseId, workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise não encontrado"));

        exercise.setName(dto.name());
        exercise.setMuscleGroup(dto.muscleGroup());
        exercise.setSets(dto.sets());
        exercise.setRepetitions(dto.repetitions());

        Exercise updated = repository.save(exercise);

        return new ExerciseResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getMuscleGroup(),
                updated.getSets(),
                updated.getRepetitions()
        );
    }

    public void deleteExercise(String email, Long workoutId, Long exerciseId) {
        Workout workout = workoutRepository.findByIdAndUserEmail(workoutId, email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário ou treino não encontrado"));

        Exercise exercise = repository.findByIdAndWorkoutId(exerciseId, workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercício não encontrado"));

        repository.delete(exercise);
    }
}
