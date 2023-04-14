package ru.otus.demo.api.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;
import static ru.otus.demo.meta.MessageTemplate.CREATE_USER_REQUEST_MESSAGE;
import static ru.otus.demo.meta.MessageTemplate.DELETE_USER_REQUEST_MESSAGE;
import static ru.otus.demo.meta.MessageTemplate.GET_USER_REQUEST_MESSAGE;
import static ru.otus.demo.meta.MessageTemplate.UPDATE_USER_REQUEST_MESSAGE;

/**
 * Контроллер для работы с пользователем.
 *
 * @author Руслан Жарков
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @GetMapping(value = "/{userId}")
    public UserDto getUser(@PathVariable @NonNull final UUID userId) {
        log.info(GET_USER_REQUEST_MESSAGE, userId);
        return userService.getUser(userId);
    }

    @Override
    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody @NonNull @Validated(UserCreateConstraint.class)
                                           final UserDto userDto) {
        log.info(CREATE_USER_REQUEST_MESSAGE, userDto);
        return ok().body(userService.createUser(userDto));
    }

    @Override
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable @NonNull final UUID userId,
                                              @RequestBody @NonNull final UserDto userDto) {
        log.info(UPDATE_USER_REQUEST_MESSAGE, userId, userDto);
        return ok().body(userService.updateUser(userId, userDto));
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @NonNull final UUID userId) {
        log.info(DELETE_USER_REQUEST_MESSAGE, userId);
        userService.deleteUser(userId);
        return ok().build();
    }
}