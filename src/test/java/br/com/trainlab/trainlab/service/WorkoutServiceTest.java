package br.com.trainlab.trainlab.service;

import br.com.trainlab.trainlab.dto.workout.WorkoutRequestDto;
import br.com.trainlab.trainlab.model.User;
import br.com.trainlab.trainlab.model.Workout;
import br.com.trainlab.trainlab.model.enums.Level;
import br.com.trainlab.trainlab.model.enums.WorkoutType;
import br.com.trainlab.trainlab.repository.UserRepository;
import br.com.trainlab.trainlab.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {
    @InjectMocks
    private WorkoutService service;
    @Mock
    private WorkoutRepository repository;
    @Mock
    private UserRepository userRepository;

    private WorkoutRequestDto requestDto;

    //CREATE TESTS

    @Test
    void shouldCreateWorkoutSuccessfully() {
        //ARRANGE
        User user = new User();
        this.requestDto = new WorkoutRequestDto("Treino Teste", "testando o teste", WorkoutType.CARDIO, Level.BEGINNER);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        var savedWorkout = new Workout();
        savedWorkout.setId(1L);
        savedWorkout.setName(requestDto.name());
        savedWorkout.setDescription(requestDto.description());
        savedWorkout.setType(requestDto.type());
        savedWorkout.setLevel(requestDto.level());
        savedWorkout.setCreatedAt(LocalDateTime.now());
        given(repository.save(any(Workout.class))).willReturn(savedWorkout);

        //ACT
        var response = service.createWorkout("email@teste.com", requestDto);

        //ASSERT
        assertNotNull(response);
        assertEquals(savedWorkout.getName(), response.name());
        assertEquals(savedWorkout.getType(), response.type());
        assertEquals(savedWorkout.getLevel(), response.level());
        assertEquals(savedWorkout.getCreatedAt(), response.createdAt());
        System.out.println("Criado em: " + savedWorkout.getCreatedAt());
        verify(repository).save((any(Workout.class)));
    }
}