package ru.otus.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.demo.model.constraint.UserCreateConstraint;

/**
 * DTO пользователя.
 *
 * @author Руслан Жарков
 */
@NoArgsConstructor
@Data
public class UserDto {
    /**
     * Имя учетной записи.
     */
    @NotBlank(message = "Не заполнено поле username", groups = UserCreateConstraint.class)
    private String userName;
    /**
     * Фамилия.
     */
    @NotBlank(message = "Не заполнено поле lastname", groups = UserCreateConstraint.class)
    private String lastName;
    /**
     * Имя.
     */
    @NotBlank(message = "Не заполнено поле firstname", groups = UserCreateConstraint.class)
    private String firstName;
    /**
     * Email.
     */
    @NotBlank(message = "Не заполнено поле email", groups = UserCreateConstraint.class)
    private String email;
    /**
     * Телефон.
     */
    @NotBlank(message = "Не заполнено поле phone", groups = UserCreateConstraint.class)
    private String phone;
}