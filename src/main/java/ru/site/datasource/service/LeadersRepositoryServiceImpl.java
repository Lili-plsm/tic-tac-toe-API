package ru.site.datasource.service;

import java.util.List;
import ru.site.datasource.model.Leader;
import ru.site.datasource.repository.LeadersRepository;

public class LeadersRepositoryServiceImpl implements LeadersRepositoryService {

    private final LeadersRepository leadersRepository;

    public LeadersRepositoryServiceImpl(LeadersRepository leadersRepository) {
        this.leadersRepository = leadersRepository;
    }
    public List<Leader> getLeaders(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("n can't be null");
        }
        return leadersRepository.findTopPlayers(n);
    }
}
