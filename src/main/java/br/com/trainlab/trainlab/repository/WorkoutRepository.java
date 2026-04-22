package br.com.trainlab.trainlab.repository;

import br.com.trainlab.trainlab.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

//    Optional<Workout> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long workoutId, long userId);

    List<Workout> findAllByUserId(Long userId);

    Optional<Workout> findByIdAndUserId(Long workoutId, Long userId);

    long countByUser_Id(Long userId);
}
