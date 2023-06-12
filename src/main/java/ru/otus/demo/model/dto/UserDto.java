package ru.otus.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.demo.model.constraint.UserCreateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO пользователя.
 *
 * @author Руслан Жарков
 */
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @Email
    private String email;
    /**
     * Телефон.
     */
    @NotBlank(message = "Не заполнено поле phone", groups = UserCreateConstraint.class)
    private String phone;
    /**
     * Пароль.
     */
    @NotBlank(message = "Не заполнено поле password", groups = UserCreateConstraint.class)
    private String password;
}