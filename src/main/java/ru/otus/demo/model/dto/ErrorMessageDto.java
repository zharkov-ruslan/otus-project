package ru.otus.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

import static java.time.ZonedDateTime.now;
import static ru.otus.demo.meta.Constant.ISO_8601_EXTENDED_DATETIME_Z_PATTERN;

/**
 * DTO сообщения об ошибке.
 *
 * @author Руслан Жарков
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageDto {
    @NotNull
    private final UUID id;
    @Nullable
    private final String[] messages;
    @JsonFormat(pattern = ISO_8601_EXTENDED_DATETIME_Z_PATTERN)
    @NotNull
    private final ZonedDateTime timestamp;

    public ErrorMessageDto(String... messages) {
        this.id = UUID.randomUUID();
        this.messages = messages;
        this.timestamp = now();
    }
}