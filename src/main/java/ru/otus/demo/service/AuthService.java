package ru.otus.demo.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import ru.otus.demo.model.dto.LoginResponseDto;

/**
 * Сервис аутентификации и авторизации в Keycloak.
 *
 * @author Руслан Жарков
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthzClient authzClient;

    public LoginResponseDto login(@NonNull final String userName, @NonNull final String pass) {
        try {
            final AuthorizationResponse response = authzClient.authorization(userName, pass).authorize();
            return LoginResponseDto.builder()
                    .tokenType(response.getTokenType())
                    .token(response.getToken())
                    .refreshToken(response.getRefreshToken())
                    .build();
        } catch (final Exception ex) {
            throw new AuthorizationServiceException(ex.getMessage());
        }
    }
}