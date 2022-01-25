package de.gmasil.demo.springnative.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExtension {

    public <T> Optional<T> findProjectionById(Long id, Class<T> type);

    public Optional<User> findByUsername(String username);
}
