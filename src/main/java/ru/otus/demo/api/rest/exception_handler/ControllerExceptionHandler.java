package ru.otus.demo.api.rest.exception_handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.otus.demo.model.dto.ErrorMessageDto;

//import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Обработчик исключений при вызове REST-контроллеров.
 *
 * @author Руслан Жарков
 */
@ControllerAdvice
@Order(-1)
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @NonNull
    public ResponseEntity<ErrorMessageDto> handleNotFoundException(
            @NonNull final NotFoundException notFoundException) {
        final ErrorMessageDto errorMessage = new ErrorMessageDto(notFoundException.getMessage());
        log.error(errorMessage.toString());
        return ResponseEntity.status(NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(value = {BadRequestException.class, MethodArgumentNotValidException.class,
            WebApplicationException.class, ValidationException.class, NoSuchFieldException.class})
    @NonNull
    public ResponseEntity<ErrorMessageDto> handleBadRequestException(@NonNull final Exception badRequestException,
                                                                     @NonNull final WebRequest httpServletRequest) {
        final ErrorMessageDto errorMessage = new ErrorMessageDto(badRequestException.getMessage());
        log.error(errorMessage.toString());
        return ResponseEntity.status(BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @NonNull
    public ResponseEntity<ErrorMessageDto> handleInternalException(
            @NonNull final InternalServerErrorException internalException) {
        final ErrorMessageDto errorMessage = new ErrorMessageDto(internalException.getMessage());
        log.error(errorMessage.toString());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}