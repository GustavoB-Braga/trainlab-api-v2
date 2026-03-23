package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.stats.StatsDto;
import br.com.trainlab.trainlab.repository.ExerciseRepository;
import br.com.trainlab.trainlab.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    public StatsDto getStats(Long userId) {
        return new StatsDto(
                workoutRepository.countByUser_Id(userId),
                exerciseRepository.countByWorkoutUserId(userId),
                exerciseRepository.sumSetsByUserId(userId)
        );
    }
}
