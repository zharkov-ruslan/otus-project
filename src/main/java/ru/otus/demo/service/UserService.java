package ru.otus.demo.service;

import lombok.NonNull;
import org.springframework.lang.Nullable;
import ru.otus.demo.model.dto.UserDto;

import javax.ws.rs.NotFoundException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static ru.otus.demo.meta.MessageTemplate.USER_NOT_FOUND_ERROR;

public interface UserService {

    @Nullable
    UserDto getUser(@NonNull UUID userId);

    @NonNull
    List<UserDto> getAllUsers();

    @NonNull
    UUID createUser(@NonNull UserDto userDto);

    @NonNull
    UserDto updateUser(@NonNull UUID userId, @NonNull UserDto userDto);

    void deleteUser(@NonNull UUID userId);

    default Supplier<NotFoundException> userNotFoundExceptionSupplier(@NonNull final UUID userId) {
        return () -> {
            throw new NotFoundException(userNotFoundMessageSupplier(userId).get());
        };
    }

    default Supplier<String> userNotFoundMessageSupplier(@NonNull final UUID userId) {
        return () -> MessageFormat.format(USER_NOT_FOUND_ERROR, userId);
    }
}
