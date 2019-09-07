package com.moskit.contatos.controller;

import com.moskit.contatos.error.ApiError;
import com.moskit.contatos.exception.ContatoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldError().getDefaultMessage(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ContatoException.class)
    @ResponseBody
    public ApiError handleValidationException(ContatoException ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }
}
