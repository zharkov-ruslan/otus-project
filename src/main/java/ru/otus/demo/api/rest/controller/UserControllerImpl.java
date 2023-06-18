package ru.otus.demo.api.rest.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.demo.model.constraint.UserCreateConstraint;
import ru.otus.demo.model.dto.UserDto;
import ru.otus.demo.service.UserService;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static ru.otus.demo.api.rest.controller.UserController.USER_ENDPOINT_PATH;
import static ru.otus.demo.meta.MessageTemplate.CREATE_USER_REQUEST_MESSAGE;
import static ru.otus.demo.meta.MessageTemplate.DELETE_USER_REQUEST_MESSAGE;
import static ru.otus.demo.meta.MessageTemplate.GET_USERS_REQUEST_MESSAGE;
import static ru.otus.demo.meta.MessageTemplate.GET_USER_REQUEST_MESSAGE;
import static ru.otus.demo.meta.MessageTemplate.UPDATE_USER_REQUEST_MESSAGE;

/**
 * Контроллер для работы с пользователем.
 *
 * @author Руслан Жарков
 */
@RestController
@RequestMapping(USER_ENDPOINT_PATH)
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(@Qualifier("userKeycloakServiceImpl") @NonNull final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(ru.otus.demo.meta.Role).APP_ADMIN.value)")
    @Override
    public List<UserDto> getAllUsers() {
        log.info(GET_USERS_REQUEST_MESSAGE);
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(ru.otus.demo.meta.Role).APP_ADMIN.value) || (" +
            "hasRole(T(ru.otus.demo.meta.Role).APP_USER.value) && #userId.toString().equals(principal.name))")
    @Override
    public UserDto getUser(@PathVariable @NonNull final UUID userId) {
        log.info(GET_USER_REQUEST_MESSAGE, userId);
        if (Math.random() > 0.7) {
            throw new InternalServerErrorException("Random error");
        }
        return userService.getUser(userId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UUID> createUser(@RequestBody @NonNull @Validated(UserCreateConstraint.class) final UserDto userDto) {
        log.info(CREATE_USER_REQUEST_MESSAGE, userDto);
        return ResponseEntity.status(CREATED).body(userService.createUser(userDto));
    }

    @PutMapping(value = "/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(ru.otus.demo.meta.Role).APP_ADMIN.value) || (" +
            "hasRole(T(ru.otus.demo.meta.Role).APP_USER.value) && #userId.toString().equals(principal.name))")
    @Override
    public ResponseEntity<UserDto> updateUser(@PathVariable @NonNull final UUID userId,
                                              @RequestBody @NonNull final UserDto userDto) {
        log.info(UPDATE_USER_REQUEST_MESSAGE, userId, userDto);
        if (Math.random() > 0.7) {
            throw new InternalServerErrorException("Random error");
        }
        return ok().body(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole(T(ru.otus.demo.meta.Role).APP_ADMIN.value)")
    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable @NonNull final UUID userId) {
        log.info(DELETE_USER_REQUEST_MESSAGE, userId);
        userService.deleteUser(userId);
        return ok().build();
    }
}