package ru.otus.demo.meta;

import lombok.Getter;
import lombok.NonNull;

/**
 * Роли ползователей в приложении.
 *
 * @author Руслан Жарков
 */
@Getter
public enum Role {
    APP_ADMIN("app-admin"),
    APP_USER("app-user");

    private final String value;

    Role(@NonNull final String value) {
        this.value = value;
    }
}