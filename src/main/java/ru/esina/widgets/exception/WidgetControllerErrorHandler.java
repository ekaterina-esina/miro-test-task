package ru.esina.widgets.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class WidgetControllerErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        WidgetErrorEnum.INVALID_REQUEST,
                        e.getBindingResult().getAllErrors().stream().map(error -> {
                            if (error instanceof FieldError) {
                                return String.format("Field=%s, Message=%s", ((FieldError) error).getField(),
                                        error.getDefaultMessage());
                            }
                            return error.getDefaultMessage();
                        }).collect(Collectors.joining("; "))),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptions(Exception e) {
        if (e instanceof WidgetException) {
            return new ResponseEntity<>(
                    new ErrorResponse(((WidgetException) e).getError()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(
                new ErrorResponse(
                        WidgetErrorEnum.UNEXPECTED_ERROR,
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
