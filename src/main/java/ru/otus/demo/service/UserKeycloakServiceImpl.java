package ru.otus.demo.service;

import lombok.NonNull;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.demo.config.KeycloakConfig;
import ru.otus.demo.mapper.UserMapper;
import ru.otus.demo.model.dto.UserDto;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static ru.otus.demo.meta.Role.APP_USER;

/**
 * Сервис по работе с пользователями в Keycloak.
 *
 * @author Руслан Жарков
 */
@Service
public class UserKeycloakServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;
    private final UserMapper userMapper;

    public UserKeycloakServiceImpl(@Qualifier("keycloak-admin") @NonNull final Keycloak keycloak,
                                   @NonNull final KeycloakConfig keycloakConfig,
                                   @NonNull final UserMapper userMapper) {
        this.keycloak = keycloak;
        this.keycloakConfig = keycloakConfig;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUser(@NonNull final UUID userId) throws NotFoundException {
        final UserResource userResource = findUserById(userId);
        final UserRepresentation userRepresentation = userResource.toRepresentation();
        return userMapper.toUserDto(userRepresentation);
    }

    @Override
    public @NonNull List<UserDto> getAllUsers() {
        final UsersResource usersResource = getRealm().users();
        return usersResource.list().stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public @NonNull UUID createUser(@NonNull final UserDto userDto) {
        final UserRepresentation userRepresentation = userMapper.toUserRepresentation(userDto);
        final RealmResource realmResource = getRealm();
        final UsersResource usersResource = realmResource.users();
        final List<RoleRepresentation> roles = realmResource.roles().list(APP_USER.getValue(), true);
        final RoleRepresentation roleUser = roles.iterator().next();
        // Create user (requires manage-users role)
        final Response response = usersResource.create(userRepresentation);
        final UUID userId = UUID.fromString(CreatedResponseUtil.getCreatedId(response));
        final UserResource userResource = findUserById(userId);
        userResource.roles().realmLevel().add(List.of(roleUser));
        return userId;
    }

    @Override
    public @NonNull UserDto updateUser(@NonNull final UUID userId, @NonNull final UserDto userDto) {
        final UserResource userResource = findUserById(userId);
        final UserRepresentation userRepresentationForUpdate = userResource.toRepresentation();
        final UserRepresentation userRepresentationSourceUpdate = userMapper.toUserRepresentation(userDto);
        userMapper.partialUpdate(userRepresentationSourceUpdate, userRepresentationForUpdate);
        userResource.update(userRepresentationForUpdate);
        return userMapper.toUserDto(userRepresentationForUpdate);
    }

    @Override
    public void deleteUser(@NonNull final UUID userId) {
        final UserResource userResource = findUserById(userId);
        userResource.remove();
    }

    @NonNull
    private UserResource findUserById(@NonNull final UUID userId) {
        return getRealm().users().get(userId.toString());
    }

    private RealmResource getRealm() {
        return keycloak.realm(keycloakConfig.getRealm());
    }
}