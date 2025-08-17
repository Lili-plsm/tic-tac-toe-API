package ru.site.datasource.service;

import java.util.List;
import java.util.UUID;
import ru.site.datasource.model.GameStore;

public interface GameRepositoryService {
    public GameStore saveGame(GameStore game);
    public GameStore getGame(UUID uuid);
    public List<GameStore> findByPlayerXId(Long playerXId);
    public List<GameStore> findByPlayerOId(Long playerOId);
}
