package ru.site.web.model;

public class LeadersResponse {
    private Long id;
    private String login;
    private Double winRatio;

    public LeadersResponse() {
        //
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public Double getWinRatio() { return winRatio; }
    public void setWinRatio(Double winRatio) { this.winRatio = winRatio; }
}
