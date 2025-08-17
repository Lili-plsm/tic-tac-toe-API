package ru.site.datasource.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import ru.site.datasource.model.GameStore;
import ru.site.datasource.repository.GameRepository;

public class GameRepositoryServiceImpl implements GameRepositoryService {

    private final GameRepository gameRepository;

    public GameRepositoryServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameStore getGame(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID не может быть null");
        }
        return gameRepository.findByUuid(uuid).orElseThrow(
            ()
                -> new EntityNotFoundException("GameStore not found с UUID: " +
                                               uuid));
    }

    public List<GameStore> findByPlayerXId(Long playerXId) {
        return gameRepository.findByPlayerXId(playerXId);
    }

    public List<GameStore> findByPlayerOId(Long playerOId) {
        return gameRepository.findByPlayerOId(playerOId);
    }

    @Override
    @Transactional
    public GameStore saveGame(GameStore game) {
        Optional<GameStore> existingGameOpt =
            gameRepository.findByUuid(game.getUuid());
        GameStore savedGame = null;
        if (existingGameOpt.isPresent()) {
            GameStore existingGame = existingGameOpt.get();
            existingGame.setField(game.getField());
            existingGame.setGameType(game.getGameType());
            existingGame.setStatus(game.getStatus());
            existingGame.setPlayerXId(game.getPlayerXId());
            existingGame.setPlayerOId(game.getPlayerOId());
            existingGame.setCurrentPlayerId(game.getCurrentPlayerId());
            existingGame.setWinnerId(game.getWinnerId());
            savedGame = gameRepository.save(existingGame);
        } else {
            savedGame = gameRepository.save(game);
        }
        return savedGame;
    }
}
