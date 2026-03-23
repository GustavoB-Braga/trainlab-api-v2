package br.com.trainlab.trainlab.repository;

import br.com.trainlab.trainlab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
