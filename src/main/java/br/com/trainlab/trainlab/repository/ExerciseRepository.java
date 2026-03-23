package br.com.trainlab.trainlab.repository;

import br.com.trainlab.trainlab.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByWorkoutId(Long workoutId);

//    Exercise findByIdAndWorkoutId(Long exerciseId, Long workoutId);

    Optional<Exercise> findByIdAndWorkoutId(Long exerciseId, Long workoutId);

    @Query("""
            SELECT COALESCE(SUM(e.sets), 0)
            FROM Exercise e
            WHERE e.workout.user.id = :userId
            """)
    long sumSetsByUserId(@Param("userId") Long userId);

    long countByWorkoutUserId(Long userId);
}
