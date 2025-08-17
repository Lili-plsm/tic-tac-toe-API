package ru.site.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.site.JwtSettings;
import ru.site.datasource.mapper.SourceMapper;
import ru.site.datasource.repository.GameRepository;
import ru.site.datasource.repository.LeadersRepository;
import ru.site.datasource.repository.RoleRepository;
import ru.site.datasource.repository.UserRepository;
import ru.site.datasource.service.GameRepositoryService;
import ru.site.datasource.service.GameRepositoryServiceImpl;
import ru.site.datasource.service.LeadersRepositoryService;
import ru.site.datasource.service.LeadersRepositoryServiceImpl;
import ru.site.datasource.service.UserRepositoryService;
import ru.site.datasource.service.UserRepositoryServiceImpl;
import ru.site.domain.service.GameService;
import ru.site.domain.service.GameServiceImpl;
import ru.site.domain.service.UserService;
import ru.site.domain.service.UserServiceImpl;
import ru.site.security.service.AuthService;
import ru.site.security.service.AuthServiceImpl;
import ru.site.security.service.JwtProvider;
import ru.site.security.util.JwtUtil;
import ru.site.web.mapper.GameResponseMapper;
import ru.site.web.mapper.LeaderResponseMapper;
import ru.site.web.mapper.WebMapper;

@Configuration
public class AppConfig {

    @Bean
    public WebMapper webMapper() {
        return new WebMapper();
    }

    @Bean
    public SourceMapper sourceMapper() {
        return new SourceMapper();
    }

    @Bean
    public GameResponseMapper responseMapper() {
        return new GameResponseMapper();
    }
    @Bean
    public LeaderResponseMapper leaderResponseMapper() {
        return new LeaderResponseMapper();
    }

    @Bean
    public UserRepositoryService
    userRepositoryService(UserRepository userRepository,
                          RoleRepository roleRepository) {
        return new UserRepositoryServiceImpl(userRepository, roleRepository);
    }

    @Bean
    public GameRepositoryService
    gameRepositoryService(GameRepository gameRepository) {
        return new GameRepositoryServiceImpl(gameRepository);
    }

    @Bean
    public LeadersRepositoryService
    leadersRepositoryService(LeadersRepository leadersRepository) {
        return new LeadersRepositoryServiceImpl(leadersRepository);
    }

    @Bean
    public GameService
    gameService(UserRepositoryService userRepositoryService,
                GameRepositoryService gameRepositoryService,
                LeadersRepositoryService leadersRepositoryService,
                SourceMapper sourceMapper) {
        return new GameServiceImpl(userRepositoryService, gameRepositoryService,
                                   leadersRepositoryService, sourceMapper);
    }

    @Bean
    public UserService
    userService(UserRepositoryService userRepositoryService) {
        return new UserServiceImpl(userRepositoryService);
    }

    @Bean
    public AuthService authService(JwtProvider jwtProvider,
                                   UserService userService, JwtUtil jwtUtil) {
        return new AuthServiceImpl(jwtProvider, userService, jwtUtil);
    }

    @Bean
    public JwtProvider jwtProvider(JwtSettings jwtSettings) {
        return new JwtProvider(jwtSettings);
    }
}
