package ru.site.datasource.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.site.datasource.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByLogin(String login);
    public Optional<User> findById(Long uuid);
    boolean existsByLogin(String login);
}
