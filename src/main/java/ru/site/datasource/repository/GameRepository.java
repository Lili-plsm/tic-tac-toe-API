package ru.site.datasource.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import ru.site.datasource.model.GameStore;

public interface GameRepository extends CrudRepository<GameStore, UUID> {
    Optional<GameStore> findByUuid(UUID uuid);
    List<GameStore> findByPlayerXId(Long playerXId);
    List<GameStore> findByPlayerOId(Long playerOId);
    boolean existsByUuid(UUID uuid);
    boolean deleteByUuid(UUID uuid);
}
