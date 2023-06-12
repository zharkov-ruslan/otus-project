package ru.otus.demo.api.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.demo.model.dto.LoginResponseDto;
import ru.otus.demo.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public static final String AUTH_ENDPOINT = "/auth";
    public static final String AUTH_LOGIN_ENDPOINT = AUTH_ENDPOINT + "/login";
    public static final String AUTH_LOGOUT_ENDPOINT = AUTH_ENDPOINT + "/logout";

    private final AuthService authService;

    @PostMapping(value = AUTH_LOGIN_ENDPOINT, consumes = APPLICATION_FORM_URLENCODED_VALUE)
//    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponseDto> login(@NonNull final String userName, @NonNull final String password) {
        final LoginResponseDto responseMessage = authService.login(userName, password);
        return ok().body(responseMessage);
    }

    /* TODO: разобраться, почему при logout пустой refresh_token и где его взять или как передать.
        Вариант для logout по умолчанию: /sso/logout - тоже не работает из-за пустого idToken */
    @GetMapping(path = AUTH_LOGOUT_ENDPOINT)
    public ResponseEntity<Void> logout(@NonNull final HttpServletRequest request) throws ServletException {
        request.logout();
        return ResponseEntity.status(NO_CONTENT).build();
    }
}