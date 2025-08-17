package ru.site.datasource.service;

import java.util.List;
import ru.site.datasource.model.Leader;

public interface LeadersRepositoryService {
    List<Leader> getLeaders(Integer n);
}
