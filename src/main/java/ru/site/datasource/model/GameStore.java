package ru.site.datasource.model;

import jakarta.persistence.*;
import ru.site.datasource.enums.GameStatus;
import ru.site.datasource.enums.GameType;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class GameStore {

@Id
@GeneratedValue(generator = "UUID")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
@Column(updatable = false, nullable = false)
private UUID uuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "field_store_id")
    private FieldStore field;
    @Enumerated(EnumType.STRING)
    @Column
    private GameStatus status = GameStatus.WAITING;
    @Enumerated(EnumType.STRING)
    @Column
    private GameType gameType = GameType.HVS_C;
    @Column private Long playerXId;
    @Column private Long playerOId;
    @Column private Long currentPlayerId;
    @Column private Long winnerId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime eventDateTime;

    public GameStore() {
        // empty
    }

    public LocalDateTime getEventDateTime() { return eventDateTime; }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public FieldStore getField() { return field; }
    public void setField(FieldStore field) { this.field = field; }

    public UUID getUuid() { return uuid; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public GameType getGameType() { return gameType; }
    public void setGameType(GameType gameType) { this.gameType = gameType; }

    public GameStatus getStatus() { return status; }
    public void setStatus(GameStatus status) { this.status = status; }

    public Long getPlayerXId() { return playerXId; }
    public void setPlayerXId(Long playerXId) { this.playerXId = playerXId; }

    public Long getPlayerOId() { return playerOId; }
    public void setPlayerOId(Long playerOId) { this.playerOId = playerOId; }

    public Long getCurrentPlayerId() { return currentPlayerId; }
    public void setCurrentPlayerId(Long currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public Long getWinnerId() { return winnerId; }
    public void setWinnerId(Long winnerId) { this.winnerId = winnerId; }
}
