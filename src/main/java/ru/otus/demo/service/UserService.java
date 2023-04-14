package ru.otus.demo.service;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.otus.demo.exception.BadRequestException;
import ru.otus.demo.exception.InternalException;
import ru.otus.demo.exception.NotFoundException;
import ru.otus.demo.mapper.UserMapper;
import ru.otus.demo.model.dto.UserDto;
import ru.otus.demo.model.entity.UserEntity;
import ru.otus.demo.repository.UserRepository;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static ru.otus.demo.meta.MessageTemplate.USER_ALREADY_EXIST_ERROR;
import static ru.otus.demo.meta.MessageTemplate.INTERNAL_ERROR;
import static ru.otus.demo.meta.MessageTemplate.USER_NOT_FOUND_ERROR;

/**
 * Сервис по работе с пользователями.
 *
 * @author Руслан Жарков
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Получение пользователя по ID.
     *
     * @param userId ID пользователя
     * @return DTO пользователя
     * @throws NotFoundException исключение в случае, если пользователя с указаным ID не существует
     */
    @Nullable
    public UserDto getUser(@NonNull final UUID userId) {
        return findUserById(userId).map(userMapper::toDto)
                .orElseThrow(getUserNotFoundExceptionSupplier(userId));
    }

    /**
     * Создание нового пользователя.
     *
     * @param userDto DTO нового пользователя
     * @return ID нового пользователя
     */
    @NonNull
    public UUID createUser(@NonNull final UserDto userDto) {
        final UserEntity userEntity = userMapper.toEntity(userDto);
        try {
            final UserEntity savedUserEntity = userRepository.save(userEntity);
            userRepository.flush();
            return savedUserEntity.getId();
        } catch (final DataIntegrityViolationException e) {
            throw new BadRequestException(MessageFormat.format(
                    USER_ALREADY_EXIST_ERROR, userDto.getUserName()), e);
        } catch (final Exception e) {
            throw new InternalException(MessageFormat.format(INTERNAL_ERROR, e.getMessage()), e);
        }
    }

    /**
     * Обновление данных пользователя.
     *
     * @param userId  ID пользователя
     * @param userDto DTO с новыми данными пользователя
     * @return DTO с данными пользователя
     * @throws NotFoundException исключение в случае, если пользователя с указаным ID не существует
     */
    @NonNull
    public UserDto updateUser(@NonNull final UUID userId, @NonNull final UserDto userDto) {
        return findUserById(userId).map(userEntity -> {
            final UserEntity forUpdateUserEntity = userMapper.partialUpdate(userDto, userEntity);
            final UserEntity updatedUserEntity = forUpdateUserEntity.equals(userEntity)
                    ? userEntity
                    : userRepository.save(forUpdateUserEntity);
            return userMapper.toDto(updatedUserEntity);
        }).orElseThrow(getUserNotFoundExceptionSupplier(userId));
    }

    /**
     * Удаление пользователя по ID.
     *
     * @param userId ID пользователя
     */
    public void deleteUser(@NonNull final UUID userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Поиск пользователя по ID в БД.
     *
     * @param userId ID пользователя
     * @return результат поиска
     */
    @NonNull
    private Optional<UserEntity> findUserById(@NonNull final UUID userId) {
        return userRepository.findById(userId);
    }

    @NonNull
    private Supplier<RuntimeException> getUserNotFoundExceptionSupplier(@NonNull final UUID userId) {
        return () -> {
            throw new NotFoundException(
                    MessageFormat.format(USER_NOT_FOUND_ERROR, userId));
        };
    }
}