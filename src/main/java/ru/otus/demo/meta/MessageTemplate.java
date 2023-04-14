package ru.otus.demo.meta;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Шаблоны сообщений.
 *
 * @author Руслан Жарков
 */
@NoArgsConstructor(access = PRIVATE)
public class MessageTemplate {
    public static final String GET_USER_REQUEST_MESSAGE = "Получен запрос данных пользователя: id={}";
    public static final String CREATE_USER_REQUEST_MESSAGE = "Получен запрос на создание пользователя: {}";
    public static final String UPDATE_USER_REQUEST_MESSAGE = "Получен запрос на обновление данных пользователя: id={}, userData={}";
    public static final String DELETE_USER_REQUEST_MESSAGE = "Получен запрос на удаление пользователя: id={}";

    public static final String USER_NOT_FOUND_ERROR = "Пользователь не найден: id={0}";
    public static final String USER_ALREADY_EXIST_ERROR = "Пользователь [{0}] уже существует";
    public static final String INTERNAL_ERROR = "Внутренняя ошибка сервиса: {0}";
}