package ru.site.datasource.model;

import jakarta.persistence.*;


@Entity
@Table(name = "leaders")
public class Leader {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "win_ratio") private Double winRatio;

    public Leader() {}

    public Leader(User user, Double winRatio) {
        this.user = user;
        this.winRatio = winRatio;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Double getWinRatio() { return winRatio; }

    public void setWinRatio(Double winRatio) { this.winRatio = winRatio; }
}
