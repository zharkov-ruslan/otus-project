package ru.otus.demo.api.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.demo.model.dto.ErrorMessageDto;
import ru.otus.demo.model.dto.UserDto;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface UserController {

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    UserDto getUser(@PathVariable @NonNull UUID userId);

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Создание нового пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запрос успешно обработан"),
                    @ApiResponse(responseCode = "400", description = "Неверно сформирован запрос",
                            content = {
                                    @Content(mediaType = APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorMessageDto.class)),
                            }),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервиса",
                            content = {
                                    @Content(mediaType = APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorMessageDto.class)),
                            })
            }
    )
    ResponseEntity<UUID> createUser(@RequestBody @NonNull @Valid UserDto userDto);

    @PutMapping(value = "/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> updateUser(@PathVariable @NonNull UUID userId,
                                       @RequestBody @NonNull UserDto userDto);

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable @NonNull UUID userId);
}
