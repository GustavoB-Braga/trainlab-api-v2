package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.stats.StatsDto;
import br.com.trainlab.trainlab.exception.ResourceNotFoundException;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.repository.ExerciseRepository;
import br.com.trainlab.trainlab.repository.UserRepository;
import br.com.trainlab.trainlab.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    public StatsDto getStats(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User não encontrado"));
        Long userId = user.getId();

        return new StatsDto(
                workoutRepository.countByUser_Id(userId),
                exerciseRepository.countByWorkoutUserId(userId),
                exerciseRepository.sumSetsByUserId(userId)
        );
    }
}
