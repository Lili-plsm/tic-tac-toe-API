package ru.site.web.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.site.datasource.model.Leader;
import ru.site.domain.service.GameService;
import ru.site.web.mapper.LeaderResponseMapper;
import ru.site.web.model.LeadersResponse;

@RestController
public class LeadersController {

    private final GameService gameService;
    private final LeaderResponseMapper leadersResponseMapper;

    public LeadersController(GameService gameService,
                             LeaderResponseMapper leadersResponseMapper) {
        this.gameService = gameService;
        this.leadersResponseMapper = leadersResponseMapper;
    }

    @GetMapping("/leaders/{n}")
    public ResponseEntity<?> leaders(@Valid @PathVariable Integer n) {
        List<Leader> users = gameService.getLeaders(n);
        List<LeadersResponse> usersResp = new ArrayList<>();
        for (Leader user : users) {
            usersResp.add(leadersResponseMapper.toResponse(
                user));
        }

        return ResponseEntity.ok(usersResp);
    }
}
