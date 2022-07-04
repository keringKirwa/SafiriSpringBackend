package com.kirwa.safiriApp.ExceptionHandlers;

import com.kirwa.safiriApp.Entities.ErrorMessage;
import com.kirwa.safiriApp.Exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class UserAlreadyExistsExceptionHandler {
    @ControllerAdvice
    @ResponseStatus
    public static class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(UserAlreadyExistsException.class)
        public ResponseEntity<ErrorMessage> departmentNotFoundException(
                UserAlreadyExistsException exception, WebRequest request) {
            ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                    exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }
    }
}

