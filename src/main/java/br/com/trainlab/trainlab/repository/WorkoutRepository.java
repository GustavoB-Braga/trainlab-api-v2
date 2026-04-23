package br.com.trainlab.trainlab.repository;

import br.com.trainlab.trainlab.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

//    Optional<Workout> findByIdAndUserId(Long id, Long userId);

    List<Workout> findAllByUserId(Long userId);

    List<Workout> findAllByUserEmail(String email);


    Optional<Workout> findByIdAndUserEmail(Long workoutId, String email);

    long countByUser_Id(Long userId);
}
