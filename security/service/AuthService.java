package ru.site.security.service;

import ru.site.security.model.JwtAuthentication;
import ru.site.security.model.JwtRequest;
import ru.site.security.model.JwtResponse;

public interface AuthService {

    public JwtResponse auth(JwtRequest jwtRequest);

    public JwtResponse updateRefreshToken(String refreshToken);

    public JwtResponse updateAccessToken(String refreshToken);

    public JwtAuthentication getJwtAuthentication(String token);
}