package ru.otus.demo.config;

import lombok.Getter;
import lombok.NonNull;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.AuthzClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KeycloakConfig {

    @Value("${keycloak-admin.url}")
    private String serverUrl;
    @Value("${keycloak-admin.realm}")
    private String realm;
    @Value("${keycloak-admin.client-id}")
    private String clientId;
    @Value("${keycloak-admin.client-secret}")
    private String clientSecret;

    @Bean("keycloak-admin")
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build()
                )
                .build();
    }

    @Bean
    public AuthzClient keycloakAuthzClient(@NonNull final KeycloakSpringBootProperties props) {
        var config = new org.keycloak.authorization.client.Configuration(
                props.getAuthServerUrl(), props.getRealm(),
                props.getResource(), props.getCredentials(), null);

        return AuthzClient.create(config);
    }
}