package ru.site.web.mapper;

import ru.site.datasource.model.Leader;
import ru.site.web.model.LeadersResponse;

public class LeaderResponseMapper {

    public LeadersResponse toResponse(Leader leader) {
        if (leader == null) {
            throw new IllegalArgumentException("leader must not be null");
        }

        LeadersResponse leaderResponse = new LeadersResponse();
        leaderResponse.setId(leader.getUser().getId());
        leaderResponse.setLogin(leader.getUser().getLogin());
        leaderResponse.setWinRatio(leader.getWinRatio());

        return leaderResponse;
    }
}
